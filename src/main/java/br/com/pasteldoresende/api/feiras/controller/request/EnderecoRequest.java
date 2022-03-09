package br.com.pasteldoresende.api.feiras.controller.request;

import br.com.pasteldoresende.api.feiras.model.Feira;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EnderecoRequest implements Serializable {

  private static final long serialVersionUID = -2952735933715107252L;

  private Feira feira;
  private String logradouro;
  private String numero;
  private String cidade;
  private String bairro;
  private String uf;
  private String cep;
  private String complemento;
}
