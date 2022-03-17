package br.com.pasteldoresende.api.funcionarios.controller.request;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FuncionarioRequest implements Serializable {

  private static final long serialVersionUID = -2952735933715107252L;

  private String nome;
  private String descricao;
  private Boolean freela;
}
