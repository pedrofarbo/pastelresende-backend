package br.com.pasteldoresende.api.categorias.converter;

import br.com.pasteldoresende.api.categorias.controller.request.CategoriaRequest;
import br.com.pasteldoresende.api.categorias.controller.response.CategoriaResponse;
import br.com.pasteldoresende.api.categorias.model.Categoria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaConverter {

  public List<CategoriaResponse> converterCategoriaListToCategoriaResponseList(List<Categoria> categorias) {
    List<CategoriaResponse> categoriaResponseList = new ArrayList<>();

    categorias.forEach(c -> {
      CategoriaResponse categoriaResponse = new CategoriaResponse();

      if (c.getId() != null) {
        categoriaResponse.setId(c.getId());
      }

      categoriaResponse.setNome(c.getNome());
      categoriaResponse.setDescricao(c.getDescricao() != null ? c.getDescricao() : null);
      categoriaResponseList.add(categoriaResponse);
    });

    return categoriaResponseList;
  }

  public CategoriaResponse converterCategoriaToCategoriaResponse(Categoria categoria) {
    CategoriaResponse categoriaResponse = new CategoriaResponse();

    if (categoria.getId() != null) {
      categoriaResponse.setId(categoria.getId());
    }

    categoriaResponse.setNome(categoria.getNome());
    categoriaResponse.setDescricao(categoria.getDescricao() != null ? categoria.getDescricao() : null);

    return categoriaResponse;
  }

  public Categoria converterCategoriaRequestToCategoria(CategoriaRequest categoriaRequest) {
    Categoria categoria = new Categoria();
    categoria.setNome(categoriaRequest.getNome());

    if(categoriaRequest.getDescricao() != null) {
      categoria.setDescricao(categoriaRequest.getDescricao());
    }

    return categoria;
  }
}
