package br.com.pasteldoresende.api.vendas.model;

import br.com.pasteldoresende.api.produtos.model.Produto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "venda_produtos")
@Data
@ToString(of = {"id"})
@EqualsAndHashCode(exclude = "venda")
@AllArgsConstructor
@NoArgsConstructor
public class VendaProduto implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Integer sequencia;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "venda_id", nullable = false)
  private Venda venda;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "produto_id", nullable = false)
  private Produto produto;

  @Column(name = "quantidade", nullable = false)
  private Integer quantidade;
}
