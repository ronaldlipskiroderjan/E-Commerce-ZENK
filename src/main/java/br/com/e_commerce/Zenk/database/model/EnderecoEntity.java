package br.com.e_commerce.Zenk.database.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enderecos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private int numero;

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cep;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transportadora_id")
    private TransportadoraEntity transportadora;
}
