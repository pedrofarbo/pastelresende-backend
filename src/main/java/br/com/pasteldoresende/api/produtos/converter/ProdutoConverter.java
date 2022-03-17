package br.com.pasteldoresende.api.produtos.converter;

import br.com.pasteldoresende.api.categorias.controller.response.CategoriaResponse;
import br.com.pasteldoresende.api.categorias.model.Categoria;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraEnderecoResponse;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.model.Feira;
import br.com.pasteldoresende.api.funcionarios.controller.response.FuncionarioResponse;
import br.com.pasteldoresende.api.funcionarios.model.Funcionario;
import br.com.pasteldoresende.api.produtos.controller.request.ProdutoRequest;
import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;
import br.com.pasteldoresende.api.produtos.model.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoConverter {

  public List<ProdutoResponse> converterProdutoListToProdutoResponseList(List<Produto> produtos) {
    List<ProdutoResponse> produtoResponseList = new ArrayList<>();

    produtos.forEach(p -> {
      ProdutoResponse produtoResponse = new ProdutoResponse();

      if (p.getId() != null) {
        produtoResponse.setId(p.getId());
      }

      CategoriaResponse categoriaResponse = new CategoriaResponse();

      if (p.getCategoria() != null) {
        if(p.getCategoria().getId() != null) {
          categoriaResponse.setId(p.getCategoria().getId());
        }

        categoriaResponse.setDescricao(p.getCategoria().getDescricao());
        categoriaResponse.setNome(p.getCategoria().getNome());
      }

      FeiraResponse feiraResponse = new FeiraResponse();

      if (p.getFeira() != null) {
        if(p.getFeira().getId() != null) {
          feiraResponse.setId(p.getFeira().getId());
        }

        feiraResponse.setDescricao(p.getFeira().getDescricao());
        feiraResponse.setNome(p.getFeira().getNome());
        feiraResponse.setResponsavel(null);
        feiraResponse.setEndereco(null);
      }

      produtoResponse.setNome(p.getNome());
      produtoResponse.setDescricao(p.getDescricao() != null ? p.getDescricao() : null);
      produtoResponse.setCategoria(categoriaResponse);
      produtoResponse.setPreco(p.getPreco());
      produtoResponse.setImagemBase64(p.getImagemBase64() != null ? p.getImagemBase64() : null);

      produtoResponse.setFeira(feiraResponse);

      produtoResponseList.add(produtoResponse);
    });

    return produtoResponseList;
  }

  public ProdutoResponse converterProdutoToProdutoResponse(Produto produto) {
    ProdutoResponse produtoResponse = new ProdutoResponse();

    if (produto.getId() != null) {
      produtoResponse.setId(produto.getId());
    }

    CategoriaResponse categoriaResponse = new CategoriaResponse();

    if (produto.getCategoria() != null) {
      if(produto.getCategoria().getId() != null) {
        categoriaResponse.setId(produto.getCategoria().getId());
      }

      categoriaResponse.setDescricao(produto.getCategoria().getDescricao());
      categoriaResponse.setNome(produto.getCategoria().getNome());
    }

    FeiraResponse feiraResponse = new FeiraResponse();

    if (produto.getFeira() != null) {
      if(produto.getFeira().getId() != null) {
        feiraResponse.setId(produto.getFeira().getId());
      }

      feiraResponse.setDescricao(produto.getFeira().getDescricao());
      feiraResponse.setNome(produto.getFeira().getNome());

      feiraResponse.setResponsavel(null);
      feiraResponse.setEndereco(null);
    }

    produtoResponse.setNome(produto.getNome());
    produtoResponse.setDescricao(produto.getDescricao() != null ? produto.getDescricao() : null);
    produtoResponse.setCategoria(categoriaResponse);
    produtoResponse.setPreco(produto.getPreco());
    produtoResponse.setImagemBase64(produto.getImagemBase64() != null ? produto.getImagemBase64() : null);

    produto.getFeira().setEndereco(null);

    produtoResponse.setFeira(feiraResponse);

    return produtoResponse;
  }

  public Produto converterProdutoRequestToProduto(ProdutoRequest produtoRequest, Categoria categoria, Feira feira) {
    Produto produto = new Produto();

    if(produtoRequest.getDescricao() != null) {
      produto.setDescricao(produtoRequest.getDescricao());
    }

    produto.setNome(produtoRequest.getNome());
    produto.setDescricao(produtoRequest.getDescricao() != null ? produtoRequest.getDescricao() : null);
    produto.setCategoria(categoria);
    produto.setPreco(produtoRequest.getPreco());
    produto.setImagemBase64(produtoRequest.getImagemBase64() != null ? produtoRequest.getImagemBase64() : null);

    produto.setFeira(feira);

    return produto;
  }
}
