package br.com.pasteldoresende.api.funcionarios.controller.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FuncionarioResponse {
  private static final long serialVersionUID = -2952735933715107252L;

  private Long id;
  private String nome;
  private String descricao;
  private Boolean freela;
}
