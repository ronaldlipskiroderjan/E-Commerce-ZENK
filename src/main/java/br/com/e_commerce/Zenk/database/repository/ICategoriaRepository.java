package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
    boolean existsByNomeIgnoreCase(String nome);
}

