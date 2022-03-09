package br.com.pasteldoresende.api.feiras.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "feira_endereco")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Endereco implements Serializable {

  private static final long serialVersionUID = -2952735933715107252L;

  @Id
  @Column(unique = true, nullable = false)
  private Long id;

  @MapsId("id")
  @OneToOne
  @JoinColumn(name = "id")
  private Feira feira;

  @Column
  private String logradouro;

  @Column
  private String numero;

  @Column
  private String cidade;

  @Column
  private String bairro;

  @Column
  private String uf;

  @Column
  private String cep;

  @Column
  private String complemento;
}
