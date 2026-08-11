package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.UsuarioEntity;
import br.com.e_commerce.Zenk.dtos.response.UsuarioResponseDTO;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    boolean existsByEmail(String email);
    Optional<UsuarioEntity> findByEmail(String email);
    Page<UsuarioEntity> findAll(Pageable pageable);
}
