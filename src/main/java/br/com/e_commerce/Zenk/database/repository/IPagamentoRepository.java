package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagamentoRepository extends JpaRepository<PagamentoEntity, Integer> {
}
