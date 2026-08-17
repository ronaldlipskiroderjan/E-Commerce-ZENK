package br.com.e_commerce.Zenk.database.repository;

import br.com.e_commerce.Zenk.database.model.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {
    boolean existsByLogradouroAndNumero(String logradouro, int numero);
    List<EnderecoEntity> findAllByUsuarioEmail(String Username);
    Optional<EnderecoEntity> findByIdAndUsuarioEmail(Integer id, String Username);
}
