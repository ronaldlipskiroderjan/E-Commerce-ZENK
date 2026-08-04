package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {
}
