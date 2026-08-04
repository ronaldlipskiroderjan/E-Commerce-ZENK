package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoRepository extends JpaRepository<PedidoEntity, Integer> {
}
