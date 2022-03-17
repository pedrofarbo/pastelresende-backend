package br.com.pasteldoresende.api.funcionarios.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

  private static final long serialVersionUID = -2952735933715107252L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String nome;

  @Column
  private String descricao;

  @Column
  private Boolean freela;
}
