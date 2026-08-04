package br.com.e_commerce.Zenk.database.model;

import br.com.e_commerce.Zenk.enums.MetodoPagamento;
import br.com.e_commerce.Zenk.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private MetodoPagamento metodo;

    @Column(nullable = false)
    private StatusPagamento status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;
}
