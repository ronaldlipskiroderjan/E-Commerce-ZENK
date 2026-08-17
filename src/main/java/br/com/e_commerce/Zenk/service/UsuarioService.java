package br.com.e_commerce.Zenk.service;

import br.com.e_commerce.Zenk.config.TokenProvider;
import br.com.e_commerce.Zenk.database.model.UsuarioEntity;
import br.com.e_commerce.Zenk.database.repository.IRoleRepository;
import br.com.e_commerce.Zenk.database.repository.IUsuarioRepository;
import br.com.e_commerce.Zenk.dtos.request.PasswordUpdateRequestDTO;
import br.com.e_commerce.Zenk.dtos.request.UpdateUsuarioRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.UsuarioResponseDTO;
import br.com.e_commerce.Zenk.enums.RoleTypeEnum;
import br.com.e_commerce.Zenk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void update(UpdateUsuarioRequestDTO dto, Authentication authentication) throws Exception {
        UsuarioEntity usuarioEntity= usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        usuarioEntity.setNome(dto.nome());
        usuarioEntity.setCpf(dto.cpf());
        usuarioEntity.setTelefone(dto.telefone());
        usuarioRepository.save(usuarioEntity);
    }

    public void updatePassword(PasswordUpdateRequestDTO dto, Authentication authentication) {
        UsuarioEntity usuarioEntity= usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        usuarioEntity.setSenha(passwordEncoder.encode(dto.senha()));
        usuarioRepository.save(usuarioEntity);
    }

    public void deleteMe(Authentication authentication) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        usuarioRepository.delete(usuarioEntity);
    }

    public Page<UsuarioResponseDTO> findAllUsers(Pageable pageable) {
        return usuarioRepository.findAllByRole(RoleTypeEnum.ROLE_CLIENTE.name(), pageable)
                .map(this::toDTO);
    }

    public UsuarioResponseDTO findById(Integer id) throws Exception {
        return usuarioRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado para o ID " + id));
    }

    public void deleteUser(Integer id) throws Exception {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
        usuarioRepository.delete(usuarioEntity);
    }

    private UsuarioResponseDTO toDTO(UsuarioEntity usuarioEntity) {
        return new UsuarioResponseDTO(
                usuarioEntity.getId(),
                usuarioEntity.getNome(),
                usuarioEntity.getEmail(),
                usuarioEntity.getCpf(),
                usuarioEntity.getTelefone()
        );
    }
}
