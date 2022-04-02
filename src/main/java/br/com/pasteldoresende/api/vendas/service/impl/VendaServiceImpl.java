package br.com.pasteldoresende.api.vendas.service.impl;

import br.com.pasteldoresende.api.estoque.controller.request.EstoqueRequest;
import br.com.pasteldoresende.api.estoque.controller.response.EstoqueResponse;
import br.com.pasteldoresende.api.estoque.service.EstoqueService;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.service.FeiraService;
import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;
import br.com.pasteldoresende.api.produtos.service.ProdutoService;
import br.com.pasteldoresende.api.vendas.controller.request.VendaProdutoRequest;
import br.com.pasteldoresende.api.vendas.controller.request.VendaRequest;
import br.com.pasteldoresende.api.vendas.controller.request.VendaUpdateProdutoRequest;
import br.com.pasteldoresende.api.vendas.controller.request.VendaUpdateRequest;
import br.com.pasteldoresende.api.vendas.controller.response.VendaResponse;
import br.com.pasteldoresende.api.vendas.converter.VendaConverter;
import br.com.pasteldoresende.api.vendas.model.Venda;
import br.com.pasteldoresende.api.vendas.model.VendaProduto;
import br.com.pasteldoresende.api.vendas.repository.VendaRepository;
import br.com.pasteldoresende.api.vendas.service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VendaServiceImpl implements VendaService {

  private final VendaRepository vendaRepository;
  private final FeiraService feiraService;
  private final ProdutoService produtoService;
  private final EstoqueService estoqueService;
  private final VendaConverter converter;

  @Override
  public List<VendaResponse> getAll() {
    List<Venda> vendaList = new ArrayList<>();

    try {
      Iterable<Venda> vendaIterable = vendaRepository.findAll();
      vendaIterable.iterator().forEachRemaining(vendaList::add);

      if (!vendaList.isEmpty()) {
        return converter.converterVendaListToVendaResponseList(vendaList);
      } else {
        return new ArrayList<>();
      }
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public List<VendaResponse> getAllByFeiraId(Long id) {
    List<Venda> vendaList = new ArrayList<>();

    try {
      Iterable<Venda> vendaIterable = vendaRepository.findAllByFeiraId(id);
      vendaIterable.iterator().forEachRemaining(vendaList::add);

      if (!vendaList.isEmpty()) {
        return converter.converterVendaListToVendaResponseList(vendaList);
      } else {
        return new ArrayList<>();
      }

    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public VendaResponse getById(Long id) {
    try {
      Optional<Venda> venda = vendaRepository.findById(id);
      return venda.map(converter::converterVendaToVendaResponse).orElse(null);
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public VendaResponse create(VendaRequest vendaRequest) {
    List<ProdutoResponse> produtos = new ArrayList<>();

    try {
      for (VendaProdutoRequest vendaProduto : vendaRequest.getProdutos()) {
        ProdutoResponse produtoResponse = produtoService.getById(vendaProduto.getProdutoId());
        if (produtoResponse.getId() != null) {
          produtos.add(produtoResponse);
        }
      }
    } catch (Exception ex) {
      throw new NotFoundException();
    }

    try {
      FeiraResponse feira = feiraService.getById(vendaRequest.getFeiraId());

      if (feira.getId() != null) {
        Venda venda = converter.vendaRequestToVenda(vendaRequest, produtos, feira);
        Venda vendaResponse = vendaRepository.save(venda);

        if (vendaResponse.getId() != null) {
          EstoqueRequest estoqueRequest = new EstoqueRequest();

          for (VendaProduto produto : vendaResponse.getProdutos()) {
            estoqueRequest.setValor(produto.getQuantidade() * -1);
            estoqueService.update(produto.getProduto().getId(), estoqueRequest);
          }
        }

        return converter.converterVendaToVendaResponse(vendaResponse);
      } else {
        throw new NotFoundException();
      }

    } catch (Exception ex) {
      throw new NotFoundException();
    }
  }

  @Override
  public VendaResponse update(Long id, VendaUpdateRequest vendaRequest) {
    List<ProdutoResponse> produtos = new ArrayList<>();

    try {
      Optional<Venda> vendaOptional = vendaRepository.findById(id);

      if(vendaOptional.isPresent()) {
        try {
          for (VendaUpdateProdutoRequest vendaProduto : vendaRequest.getProdutos()) {
            ProdutoResponse produtoResponse = produtoService.getById(vendaProduto.getProdutoId());
            if (produtoResponse.getId() != null) {
              produtos.add(produtoResponse);
            }
          }
        } catch (Exception ex) {
          throw new NotFoundException();
        }

        try {
          FeiraResponse feira = feiraService.getById(vendaRequest.getFeiraId());

          if (feira.getId() != null) {
            Venda venda = converter.vendaUpdateRequestToVenda(vendaRequest, produtos, feira);
            venda.setId(vendaOptional.get().getId());
            Venda vendaResponse = vendaRepository.save(venda);

            if (vendaResponse.getId() != null) {
              EstoqueRequest estoqueRequest = new EstoqueRequest();

              for (VendaUpdateProdutoRequest vendaProduto : vendaRequest.getProdutos()) {
                EstoqueResponse estoqueResponse = estoqueService.getByProductId(vendaProduto.getProdutoId());
                int diferenca = Math.abs(vendaProduto.getQuantidadeNova() - vendaProduto.getQuantidadeAntiga());
                estoqueRequest.setValor(diferenca);
                estoqueService.update(estoqueResponse.getId(), estoqueRequest);
              }
            }

            return converter.converterVendaToVendaResponse(vendaResponse);
          } else {
            throw new NotFoundException();
          }

        } catch (Exception ex) {
          throw new NotFoundException();
        }
      } else {
        throw new NotFoundException();
      }
    } catch (Exception ex) {
      throw new NotFoundException();
    }
  }
}
