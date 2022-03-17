package br.com.pasteldoresende.api.produtos.controller.request;

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
public class ProdutoRequest implements Serializable {
  private Long feiraId;
  private Long categoriaId;
  private String nome;
  private String descricao;
  private String imagemBase64;
  private BigDecimal preco;
}
