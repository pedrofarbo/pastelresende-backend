package br.com.pasteldoresende.api.vendas.model;

import br.com.pasteldoresende.api.produtos.model.Produto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "venda_produtos")
@Data
@ToString(of = {"id"})
public class VendaProduto implements Serializable {

  @EmbeddedId
  private VendaProdutoId id;

  @MapsId("vendaId")
  @ManyToOne
  @JoinColumn(name = "venda_id", nullable = false)
  private Venda venda;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "produto_id", nullable = false)
  private Produto produto;

  @Column(name = "quantidade", nullable = false)
  private Integer quantidade;
}
