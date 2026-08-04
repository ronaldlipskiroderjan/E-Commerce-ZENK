package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {
}
