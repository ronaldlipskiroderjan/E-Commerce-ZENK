package br.com.e_commerce.Zenk.service;

import br.com.e_commerce.Zenk.config.TokenProvider;
import br.com.e_commerce.Zenk.database.model.RoleEntity;
import br.com.e_commerce.Zenk.database.model.UsuarioEntity;
import br.com.e_commerce.Zenk.database.repository.IRoleRepository;
import br.com.e_commerce.Zenk.database.repository.IUsuarioRepository;
import br.com.e_commerce.Zenk.dtos.request.PasswordUpdateRequestDTO;
import br.com.e_commerce.Zenk.dtos.request.UpdateUsuarioRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.TokenResponseDTO;
import br.com.e_commerce.Zenk.dtos.response.UsuarioResponseDTO;
import br.com.e_commerce.Zenk.enums.RoleTypeEnum;
import br.com.e_commerce.Zenk.exception.NotFoundException;
import jakarta.persistence.EnumType;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;
    @Value("${jwt.expiration}")
    private long expirationTime;


    public UsuarioResponseDTO findMe(Authentication authentication) {
        return usuarioRepository.findByEmail(authentication.getName())
                .map(this::toDTO)
                .orElseThrow(() -> new UsernameNotFoundException("Usuaŕio não encontrado!"));
    }

    public TokenResponseDTO update(UpdateUsuarioRequestDTO dto, Authentication authentication) throws Exception {
        UsuarioEntity usuarioEntity= usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        usuarioEntity.setNome(dto.nome());
        usuarioEntity.setEmail(dto.email());
        usuarioEntity.setCpf(dto.cpf());
        usuarioEntity.setTelefone(dto.telefone());
        usuarioRepository.save(usuarioEntity);
        return authenticate(usuarioEntity);
    }

    public TokenResponseDTO updatePassword(PasswordUpdateRequestDTO dto, Authentication authentication) throws Exception {
        UsuarioEntity usuarioEntity= usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        usuarioEntity.setSenha(passwordEncoder.encode(dto.senha()));
        usuarioRepository.save(usuarioEntity);
        return authenticate(usuarioEntity);
    }

    public void deactivateMe(Authentication authentication) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        usuarioEntity.setActivate(false);
        usuarioRepository.save(usuarioEntity);
    }

    public Page<UsuarioResponseDTO> findAllUsers(Pageable pageable) throws Exception {
        RoleEntity role = roleRepository.findByNome(RoleTypeEnum.ROLE_ADMIN.name())
                .orElseThrow(() -> new NotFoundException("Role não encontrada"));
        return (Page<UsuarioResponseDTO>) usuarioRepository.findAll(pageable).
                filter(u -> u.getAuthorities().equals(role))
                .map(this::toDTO);
    }

    public UsuarioResponseDTO findById(Integer id) throws Exception {
        return usuarioRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado para o ID " + id));
    }

    public void deactivateUser(Integer id) throws Exception{
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
        if (usuarioEntity.isActivate()) {
            usuarioEntity.setActivate(false);
        } else {
            usuarioEntity.setActivate(true);
        }
        usuarioRepository.save(usuarioEntity);
    }

    private TokenResponseDTO authenticate(UsuarioEntity usuarioEntity) throws Exception {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioEntity.getEmail(), usuarioEntity.getSenha()));
            String token = tokenProvider.gerarToken(auth);
            return new TokenResponseDTO(token, expirationTime);
        } catch (BadCredentialsException ex) {
            throw new BadRequestException("Credenciais Inválidas");
        } catch (Exception ex) {
            throw ex;
        }
    }

    private UsuarioResponseDTO toDTO(UsuarioEntity usuarioEntity) {
        return new UsuarioResponseDTO(
                usuarioEntity.getNome(),
                usuarioEntity.getEmail(),
                usuarioEntity.getCpf(),
                usuarioEntity.getTelefone(),
                usuarioEntity.isActivate()
        );
    }
}
