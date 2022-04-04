package br.com.pasteldoresende.api.feiras.model;

import br.com.pasteldoresende.api.funcionarios.model.Funcionario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "feira")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of="id")
@EqualsAndHashCode(exclude = "endereco")
public class Feira implements Serializable {

  private static final long serialVersionUID = -2952735933715107252L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id;

  @Column
  private String nome;

  @Column
  private String descricao;

  @JsonIgnoreProperties(value = {"feira"}, allowSetters = true)
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "feira")
  private FeiraEndereco endereco;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "funcionario_id")
  private Funcionario responsavel;

  @Column
  private String obs;
}
