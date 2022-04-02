package br.com.pasteldoresende.api.vendas.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class VendaProdutoId implements Serializable {
  @Column(name = "venda_id", nullable = false)
  private Long vendaId;

  @Column(name = "sequencia", nullable = false)
  private Long sequencia;
}
