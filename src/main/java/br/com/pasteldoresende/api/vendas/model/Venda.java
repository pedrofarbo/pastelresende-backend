package br.com.pasteldoresende.api.vendas.model;

import br.com.pasteldoresende.api.feiras.model.Feira;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendas")
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"produtos"})
public class Venda implements Serializable  {

  private static final long serialVersionUID = -2952735933715107252L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feira_id")
  private Feira feira;

  @JsonIgnoreProperties(value = {"venda"}, allowSetters = true)
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "venda", orphanRemoval = true)
  private Set<VendaProduto> produtos = new HashSet<>();

  @Column
  private TipoVendaEnum tipoVenda;

  @Column
  private BigDecimal total;
}
