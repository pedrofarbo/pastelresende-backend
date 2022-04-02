package br.com.pasteldoresende.api.vendas.controller.response;

import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.vendas.model.TipoVendaEnum;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class VendaResponse implements Serializable {
  private Long id;
  private FeiraResponse feira;
  private List<VendaProdutoResponse> vendaProdutos;
  private TipoVendaEnum tipoVenda;
  private BigDecimal total;
}
