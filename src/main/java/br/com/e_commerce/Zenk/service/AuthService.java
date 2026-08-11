package br.com.e_commerce.Zenk.service;

import br.com.e_commerce.Zenk.config.TokenProvider;
import br.com.e_commerce.Zenk.database.model.RoleEntity;
import br.com.e_commerce.Zenk.database.model.UsuarioEntity;
import br.com.e_commerce.Zenk.database.repository.IRoleRepository;
import br.com.e_commerce.Zenk.database.repository.IUsuarioRepository;
import br.com.e_commerce.Zenk.dtos.request.AuthLoginRequestDTO;
import br.com.e_commerce.Zenk.dtos.request.AuthRequestDTO;
import br.com.e_commerce.Zenk.dtos.request.TokenRefreshRequestDTO;
import br.com.e_commerce.Zenk.dtos.response.TokenResponseDTO;
import br.com.e_commerce.Zenk.enums.RoleTypeEnum;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUsuarioRepository usuarioRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public void register(AuthRequestDTO dto) throws BadRequestException {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new BadRequestException("E-mail já cadastrado");
        }
        RoleEntity role = roleRepository.findByNome(RoleTypeEnum.ROLE_CLIENTE.name())
                .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                        .nome(RoleTypeEnum.ROLE_CLIENTE.name())
                        .build()));
        usuarioRepository.save(UsuarioEntity.builder()
                .nome(dto.nome())
                .email(dto.email())
                .roles(Set.of(role))
                .senha(passwordEncoder.encode(dto.senha()))
                .cpf(dto.cpf())
                .telefone(dto.telefone())
                .dataCadastro(LocalDateTime.now())
                .activate(true)
                .build()
        );
    }

    public TokenResponseDTO login(AuthLoginRequestDTO dto) throws Exception {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
            String token = tokenProvider.gerarToken(auth);
            return new TokenResponseDTO(token, expirationTime);
        } catch (BadCredentialsException ex) {
            throw new BadRequestException("Credenciais Inválidas");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public TokenResponseDTO refreshToken(Authentication authentication) {
            return new TokenResponseDTO(tokenProvider.gerarToken(authentication), expirationTime);
    }
}