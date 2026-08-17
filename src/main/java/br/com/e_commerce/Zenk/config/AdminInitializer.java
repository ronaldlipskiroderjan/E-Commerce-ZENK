package br.com.e_commerce.Zenk.config;

import br.com.e_commerce.Zenk.database.model.RoleEntity;
import br.com.e_commerce.Zenk.database.model.UsuarioEntity;
import br.com.e_commerce.Zenk.database.repository.IRoleRepository;
import br.com.e_commerce.Zenk.database.repository.IUsuarioRepository;
import br.com.e_commerce.Zenk.enums.RoleTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final IUsuarioRepository usuarioRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.nome}")
    private String adminNome;
    @Value("${app.admin.email}")
    private String adminEmail;
    @Value("${app.admin.password}")
    private String adminPassword;
    @Value("${app.admin.cpf}")
    private String adminCpf;
    @Value("${app.admin.telefone}")
    private String adminTelefone;

    @Bean
    CommandLineRunner criarAdminInicial() {
        return args -> {
            if (usuarioRepository.existsByEmail(adminEmail)) {
                return;
            }
            RoleEntity roleAdmin = roleRepository.findByNome(RoleTypeEnum.ROLE_ADMIN.name())
                    .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                            .nome(RoleTypeEnum.ROLE_ADMIN.name())
                            .build()));
            RoleEntity roleCliente = roleRepository.findByNome(RoleTypeEnum.ROLE_CLIENTE.name())
                    .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                            .nome(RoleTypeEnum.ROLE_CLIENTE.name())
                            .build()));
            UsuarioEntity admin = UsuarioEntity.builder()
                    .nome(adminNome)
                    .email(adminEmail)
                    .roles(Set.of(roleAdmin, roleCliente))
                    .senha(passwordEncoder.encode(adminPassword))
                    .cpf(adminCpf)
                    .telefone(adminTelefone)
                    .dataCadastro(LocalDateTime.now())
                    .build();
            usuarioRepository.save(admin);
        };
    }

}
