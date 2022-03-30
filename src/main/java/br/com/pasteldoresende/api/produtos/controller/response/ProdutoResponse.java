package br.com.pasteldoresende.api.produtos.controller.response;

import br.com.pasteldoresende.api.categorias.controller.response.CategoriaResponse;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProdutoResponse implements Serializable {
  private Long id;
  private FeiraResponse feira;
  private CategoriaResponse categoria;
  private String nome;
  private String descricao;
  private String imagemBase64;
  private BigDecimal preco;
}
