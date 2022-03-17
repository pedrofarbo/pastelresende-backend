package br.com.pasteldoresende.api.produtos.service;

import br.com.pasteldoresende.api.produtos.controller.request.ProdutoRequest;
import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;

import java.util.List;

public interface ProdutoService {
  List<ProdutoResponse> getAll();
  ProdutoResponse getById(Long id);
  ProdutoResponse save(ProdutoRequest produtoRequest);
  ProdutoResponse update(Long id, ProdutoRequest produtoRequest);
  Boolean delete(Long id);
}
