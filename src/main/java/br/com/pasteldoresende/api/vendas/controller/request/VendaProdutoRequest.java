package br.com.pasteldoresende.api.vendas.controller.request;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class VendaProdutoRequest implements Serializable {
  private Long produtoId;
  private Integer quantidade;
}
