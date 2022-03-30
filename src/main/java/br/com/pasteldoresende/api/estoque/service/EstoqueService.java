package br.com.pasteldoresende.api.estoque.service;

import br.com.pasteldoresende.api.estoque.controller.request.EstoqueRequest;
import br.com.pasteldoresende.api.estoque.controller.response.EstoqueResponse;

import java.util.List;

public interface EstoqueService {
  List<EstoqueResponse> getAll();
  EstoqueResponse getByProductId(Long id);
  EstoqueResponse update(Long id, EstoqueRequest estoqueRequest);
  List<EstoqueResponse> getAllByFeiraId(Long id);
}
