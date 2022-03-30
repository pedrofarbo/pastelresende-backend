package br.com.pasteldoresende.api.estoque.service.impl;

import br.com.pasteldoresende.api.estoque.controller.request.EstoqueRequest;
import br.com.pasteldoresende.api.estoque.controller.response.EstoqueResponse;
import br.com.pasteldoresende.api.estoque.converter.EstoqueConverter;
import br.com.pasteldoresende.api.estoque.model.Estoque;
import br.com.pasteldoresende.api.estoque.repository.EstoqueRepository;
import br.com.pasteldoresende.api.estoque.service.EstoqueService;
import br.com.pasteldoresende.api.produtos.model.Produto;
import br.com.pasteldoresende.api.produtos.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EstoqueServiceImpl implements EstoqueService {

  private final EstoqueRepository estoqueRepository;
  private final ProdutoRepository produtoRepository;
  private final EstoqueConverter converter;

  @Override
  public List<EstoqueResponse> getAll() {
    List<Estoque> estoqueList = new ArrayList<>();

    try {
      Iterable<Estoque> estoqueIterable = estoqueRepository.findAll();
      estoqueIterable.iterator().forEachRemaining(estoqueList::add);

      if (!estoqueList.isEmpty()) {
        return converter.converterEstoqueListToEstoqueResponseList(estoqueList);
      } else {
        return new ArrayList<>();
      }
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public List<EstoqueResponse> getAllByFeiraId(Long id) {
    List<Estoque> estoqueList = new ArrayList<>();

    try {
      Iterable<Estoque> estoqueIterable = estoqueRepository.findAllByFeiraId(id);
      estoqueIterable.iterator().forEachRemaining(estoqueList::add);

      if (!estoqueList.isEmpty()) {
        return converter.converterEstoqueListToEstoqueResponseList(estoqueList);
      } else {
        return new ArrayList<>();
      }

    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public EstoqueResponse getByProductId(Long id) {
    try {
      Optional<Estoque> produto = estoqueRepository.findByProdutoId(id);
      return produto.map(converter::converterEstoqueToEstoqueResponse).orElse(null);
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public EstoqueResponse update(Long id, EstoqueRequest estoqueRequest) {

    try {
      Optional<Estoque> e = estoqueRepository.findByProdutoId(id);

      if (e.isPresent()) {
        if (estoqueRequest.getValor() > 0) {
          e.get().setTotal(e.get().getTotal() + estoqueRequest.getValor());
        } else {
          Integer total = (e.get().getTotal() - (estoqueRequest.getValor() * -1));

          if(total < 0) {
            e.get().setTotal(0);
          } else {
            e.get().setTotal(total);
          }
        }

        try {
          Estoque estoque = estoqueRepository.save(e.get());

          return converter.converterEstoqueToEstoqueResponse(estoque);
        } catch (Exception exception) {
          throw new InternalServerErrorException();
        }
      } else {
        Optional<Produto> p = produtoRepository.findById(id);

        if (p.isPresent()) {
          Estoque estoque = new Estoque();
          estoque.setProduto(p.get());

          if (estoqueRequest.getValor() > 0) {
            estoque.setTotal(estoqueRequest.getValor());
          } else {
            estoque.setTotal(0);
          }

          Estoque estoqueSaved = estoqueRepository.save(estoque);

          return converter.converterEstoqueToEstoqueResponse(estoqueSaved);
        } else {
          throw new NotFoundException();
        }
      }

    } catch (NotFoundException no) {
      throw new NotFoundException();
    }
  }
}
