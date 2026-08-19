package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.ProdutoEntity;
import br.com.e_commerce.Zenk.dtos.response.ProdutoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
    boolean existsByNomeIgnoreCase(String nome);
    Page<ProdutoEntity> findAllByCategoriaId(Integer id, Pageable pageable);
}
