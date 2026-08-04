package br.com.e_commerce.Zenk.database.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transportadoras")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportadoraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "razao_social", nullable = false, unique = true)
    private String razaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(name = "inscricao_estadual", nullable = false)
    private String inscricaoEstadual;

    @Column(nullable = false, unique = true)
    private String rntcAntt;

    @Column(nullable = false, unique = true)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frete_id")
    private FreteEntity frete;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private EnderecoEntity endereco;
}
