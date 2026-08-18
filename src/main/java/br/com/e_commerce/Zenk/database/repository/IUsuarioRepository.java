package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<UsuarioEntity> findByEmail(String email);
    @Query("""
        SELECT u
        FROM UsuarioEntity u
        JOIN u.roles r 
        WHERE r.nome = :role
    """)
    Page<UsuarioEntity> findAllByRole(@Param("role") String role, Pageable pageable);
}
