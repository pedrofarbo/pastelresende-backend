package br.com.pasteldoresende.api.categorias.service;

import br.com.pasteldoresende.api.categorias.controller.request.CategoriaRequest;
import br.com.pasteldoresende.api.categorias.controller.response.CategoriaResponse;

import java.util.List;

public interface CategoriaService {
  List<CategoriaResponse> getAll();
  CategoriaResponse getById(Long id);
  CategoriaResponse save(CategoriaRequest categoriaRequest);
  CategoriaResponse update(Long id, CategoriaRequest categoriaRequest);
  Boolean delete(Long id);
}
