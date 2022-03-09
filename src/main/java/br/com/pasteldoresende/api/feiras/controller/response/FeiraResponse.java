package br.com.pasteldoresende.api.feiras.controller.response;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class FeiraResponse implements Serializable {
  private static final long serialVersionUID = -2952735933715107252L;

  private Long id;
  private String nome;
  private String descricao;
  private EnderecoResponse endereco;
  private String responsavel;
  private String obs;
}