package br.com.pasteldoresende.api.produtos.model;

import br.com.pasteldoresende.api.categorias.model.Categoria;
import br.com.pasteldoresende.api.feiras.model.Feira;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Produto extends AbstractPersistable<Long> implements Serializable  {

  private static final long serialVersionUID = -2952735933715107252L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feira_id")
  private Feira feira;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @Column
  private String nome;

  @Column
  private String descricao;

  @Column
  private String imagemBase64;

  @Column
  private BigDecimal preco;
}
