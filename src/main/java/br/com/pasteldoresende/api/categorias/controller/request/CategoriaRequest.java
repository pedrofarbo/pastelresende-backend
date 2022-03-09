package br.com.pasteldoresende.api.categorias.controller.request;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoriaRequest implements Serializable {
  private String nome;
  private String descricao;
}
