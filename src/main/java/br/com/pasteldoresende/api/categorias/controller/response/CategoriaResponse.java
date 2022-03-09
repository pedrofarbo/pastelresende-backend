package br.com.pasteldoresende.api.categorias.controller.response;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CategoriaResponse implements Serializable {
  private long id;
  private String nome;
  private String descricao;
}
