package br.com.pasteldoresende.api.feiras.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "feira_endereco")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of="id")
@EqualsAndHashCode
public class FeiraEndereco implements Serializable {

  private static final long serialVersionUID = -2952735933715107252L;

  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @MapsId("id")
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
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
