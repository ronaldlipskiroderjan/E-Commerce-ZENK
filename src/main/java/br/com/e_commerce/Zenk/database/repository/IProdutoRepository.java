package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
}
