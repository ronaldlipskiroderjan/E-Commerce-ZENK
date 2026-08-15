package br.com.e_commerce.Zenk.database.model;

import br.com.e_commerce.Zenk.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @Column(nullable = false)
    private StatusPedido status;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frete_id")
    private FreteEntity frete;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pagamento_id")
    private PagamentoEntity pagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<ItemPedidoEntity> itens = new HashSet<>();

    // ============================================================ //
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PedidoEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
