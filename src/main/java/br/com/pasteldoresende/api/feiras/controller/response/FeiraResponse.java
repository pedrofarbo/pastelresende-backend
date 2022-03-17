package br.com.pasteldoresende.api.feiras.controller.response;

import br.com.pasteldoresende.api.funcionarios.controller.response.FuncionarioResponse;
import br.com.pasteldoresende.api.funcionarios.model.Funcionario;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FeiraResponse implements Serializable {
  private static final long serialVersionUID = -2952735933715107252L;

  private Long id;
  private String nome;
  private String descricao;
  private FeiraEnderecoResponse endereco;
  private FuncionarioResponse responsavel;
  private String obs;
}
