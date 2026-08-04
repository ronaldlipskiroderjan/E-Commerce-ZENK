package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
}
