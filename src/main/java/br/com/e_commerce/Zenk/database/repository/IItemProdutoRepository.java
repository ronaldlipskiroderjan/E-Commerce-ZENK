package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.ItemPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemProdutoRepository extends JpaRepository<ItemPedidoEntity, Integer> {
}
