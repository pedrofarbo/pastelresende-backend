package br.com.pasteldoresende.api.categorias.model;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Categoria extends AbstractPersistable<Long> implements Serializable  {

  private static final long serialVersionUID = -2952735933715107252L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String nome;

  @Column
  private String descricao;
}
