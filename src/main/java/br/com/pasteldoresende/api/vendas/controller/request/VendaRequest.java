package br.com.pasteldoresende.api.vendas.controller.request;

import br.com.pasteldoresende.api.vendas.model.TipoVendaEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class VendaRequest implements Serializable {
  private Long feiraId;
  private List<VendaProdutoRequest> produtos;
  private TipoVendaEnum tipoVenda;
}
