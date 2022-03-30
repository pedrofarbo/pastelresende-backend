package br.com.pasteldoresende.api.estoque.controller.response;

import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EstoqueResponse implements Serializable {
  private Long id;
  private ProdutoResponse produto;
  private Integer total;
}
