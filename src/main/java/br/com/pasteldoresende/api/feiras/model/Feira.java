package br.com.pasteldoresende.api.feiras.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "feira")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "feira")
  private Endereco endereco;

  @Column
  private String responsavel;

  @Column
  private String obs;
}
