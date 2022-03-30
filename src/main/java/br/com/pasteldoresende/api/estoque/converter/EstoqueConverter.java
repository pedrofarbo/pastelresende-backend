package br.com.pasteldoresende.api.estoque.converter;

import br.com.pasteldoresende.api.categorias.controller.response.CategoriaResponse;
import br.com.pasteldoresende.api.categorias.model.Categoria;
import br.com.pasteldoresende.api.estoque.controller.request.EstoqueRequest;
import br.com.pasteldoresende.api.estoque.controller.response.EstoqueResponse;
import br.com.pasteldoresende.api.estoque.model.Estoque;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.model.Feira;
import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueConverter {

  public List<EstoqueResponse> converterEstoqueListToEstoqueResponseList(List<Estoque> estoques) {
    List<EstoqueResponse> estoqueResponseList = new ArrayList<>();

    estoques.forEach(e -> {
      EstoqueResponse estoqueResponse = new EstoqueResponse();

      if (e.getId() != null) {
        estoqueResponse.setId(e.getId());
      }

      ProdutoResponse produtoResponse = new ProdutoResponse();
      CategoriaResponse categoriaResponse = new CategoriaResponse();

      FeiraResponse feiraResponse = new FeiraResponse();

      if (e.getProduto() != null) {
        if(e.getProduto().getId() != null) {
          produtoResponse.setId(e.getProduto().getId());
        }

        if (e.getProduto().getFeira() != null) {
          if(e.getProduto().getFeira().getId() != null) {
            feiraResponse.setId(e.getProduto().getFeira().getId());
          }

          feiraResponse.setDescricao(e.getProduto().getFeira().getDescricao());
          feiraResponse.setNome(e.getProduto().getFeira().getNome());
          feiraResponse.setResponsavel(null);
          feiraResponse.setEndereco(null);
        }

        if (e.getProduto().getCategoria() != null) {
          if(e.getProduto().getCategoria().getId() != null) {
            categoriaResponse.setId(e.getProduto().getCategoria().getId());
          }

          categoriaResponse.setDescricao(e.getProduto().getCategoria().getDescricao());
          categoriaResponse.setNome(e.getProduto().getCategoria().getNome());
        }

        produtoResponse.setDescricao(e.getProduto().getDescricao());
        produtoResponse.setNome(e.getProduto().getNome());
        produtoResponse.setCategoria(categoriaResponse);
        produtoResponse.setFeira(feiraResponse);
        produtoResponse.setImagemBase64(e.getProduto().getImagemBase64());
        produtoResponse.setPreco(e.getProduto().getPreco());
      }

      estoqueResponse.setProduto(produtoResponse);
      estoqueResponse.setTotal(e.getTotal());

      estoqueResponseList.add(estoqueResponse);
    });

    return estoqueResponseList;
  }

  public EstoqueResponse converterEstoqueToEstoqueResponse(Estoque e) {
    EstoqueResponse estoqueResponse = new EstoqueResponse();

    if (e.getId() != null) {
      estoqueResponse.setId(e.getId());
    }

    ProdutoResponse produtoResponse = new ProdutoResponse();
    CategoriaResponse categoriaResponse = new CategoriaResponse();

    FeiraResponse feiraResponse = new FeiraResponse();

    if (e.getProduto() != null) {
      if(e.getProduto().getId() != null) {
        produtoResponse.setId(e.getProduto().getId());
      }

      if (e.getProduto().getFeira() != null) {
        if(e.getProduto().getFeira().getId() != null) {
          feiraResponse.setId(e.getProduto().getFeira().getId());
        }

        feiraResponse.setDescricao(e.getProduto().getFeira().getDescricao());
        feiraResponse.setNome(e.getProduto().getFeira().getNome());
        feiraResponse.setResponsavel(null);
        feiraResponse.setEndereco(null);
      }

      if (e.getProduto().getCategoria() != null) {
        if(e.getProduto().getCategoria().getId() != null) {
          categoriaResponse.setId(e.getProduto().getCategoria().getId());
        }

        categoriaResponse.setDescricao(e.getProduto().getCategoria().getDescricao());
        categoriaResponse.setNome(e.getProduto().getCategoria().getNome());
      }

      produtoResponse.setDescricao(e.getProduto().getDescricao());
      produtoResponse.setNome(e.getProduto().getNome());
      produtoResponse.setCategoria(categoriaResponse);
      produtoResponse.setFeira(feiraResponse);
      produtoResponse.setImagemBase64(e.getProduto().getImagemBase64());
      produtoResponse.setPreco(e.getProduto().getPreco());
    }

    estoqueResponse.setProduto(produtoResponse);
    estoqueResponse.setTotal(e.getTotal());

    return estoqueResponse;
  }
}
