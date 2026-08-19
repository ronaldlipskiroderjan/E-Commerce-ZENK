package br.com.e_commerce.Zenk.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fretes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal custo;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(nullable = false)
    private boolean ativo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;
}
