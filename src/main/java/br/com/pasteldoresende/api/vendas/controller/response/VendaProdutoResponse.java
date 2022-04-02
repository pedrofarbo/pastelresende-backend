package br.com.pasteldoresende.api.vendas.controller.response;

import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class VendaProdutoResponse implements Serializable {
  private Long id;
  private ProdutoResponse produto;
  private Integer quantidade;
}
