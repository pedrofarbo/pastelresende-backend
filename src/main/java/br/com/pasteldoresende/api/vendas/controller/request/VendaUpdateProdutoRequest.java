package br.com.pasteldoresende.api.vendas.controller.request;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class VendaUpdateProdutoRequest implements Serializable {
  private Long produtoId;
  private Integer quantidadeAntiga;
  private Integer quantidadeNova;
}
