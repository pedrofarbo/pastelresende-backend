package br.com.pasteldoresende.api.feiras.controller.request;

import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FeiraRequest implements Serializable {
  private static final long serialVersionUID = -2952735933715107252L;

  private String nome;
  private String descricao;
  private FeiraEnderecoRequest endereco;
  private String responsavel;
  private String obs;
}
