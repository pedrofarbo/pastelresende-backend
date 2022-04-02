package br.com.pasteldoresende.api.vendas.controller.request;

import br.com.pasteldoresende.api.vendas.model.TipoVendaEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class VendaUpdateRequest implements Serializable {
  private Long feiraId;
  private List<VendaUpdateProdutoRequest> produtos;
  private TipoVendaEnum tipoVenda;

}
