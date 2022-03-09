package br.com.pasteldoresende.api.categorias.service.impl;

import br.com.pasteldoresende.api.categorias.controller.request.CategoriaRequest;
import br.com.pasteldoresende.api.categorias.controller.response.CategoriaResponse;
import br.com.pasteldoresende.api.categorias.converter.CategoriaConverter;
import br.com.pasteldoresende.api.categorias.model.Categoria;
import br.com.pasteldoresende.api.categorias.repository.CategoriaRepository;
import br.com.pasteldoresende.api.categorias.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

  private final CategoriaRepository categoriaRepository;
  private final CategoriaConverter converter;

  @Override
  public List<CategoriaResponse> getAll() {
    List<Categoria> categorias = new ArrayList<>();

    try {
      Iterable<Categoria> categoriasIterable = categoriaRepository.findAll();
      categoriasIterable.iterator().forEachRemaining(categorias::add);

      if (!categorias.isEmpty()) {
        return converter.converterCategoriaListToCategoriaResponseList(categorias);
      } else {
        return new ArrayList<>();
      }
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }

  }

  @Override
  public CategoriaResponse getById(Long id) {
    try {
      Optional<Categoria> categoria = categoriaRepository.findById(id);
      return categoria.map(converter::converterCategoriaToCategoriaResponse).orElse(null);
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public CategoriaResponse save(CategoriaRequest categoriaRequest) {
    Categoria categoria = converter.converterCategoriaRequestToCategoria(categoriaRequest);

    try {
      Categoria c1 = categoriaRepository.findByNome(categoriaRequest.getNome());

      if (c1 != null) {
        throw new BadRequestException();
      }

    } catch (BadRequestException bad) {
      throw new BadRequestException();
    }

    try {
      Categoria c = categoriaRepository.save(categoria);

      if (c.getId() != null) {
        return converter.converterCategoriaToCategoriaResponse(c);
      } else {
        throw new InternalServerErrorException();
      }

    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public CategoriaResponse update(Long id, CategoriaRequest categoriaRequest) {
    try {
      Categoria c1 = categoriaRepository.findByNome(categoriaRequest.getNome());

      if (c1 != null && !Objects.equals(c1.getId(), id)) {
        throw new BadRequestException();
      }

    } catch (BadRequestException bad) {
      throw new BadRequestException();
    }

    try {
      Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

      if (categoriaOptional.isPresent()) {
        Categoria categoria = converter.converterCategoriaRequestToCategoria(categoriaRequest);
        categoria.setId(categoriaOptional.get().getId());

        if (categoriaOptional.get().getDescricao() != null && categoriaRequest.getDescricao() != null) {
          categoria.setDescricao(categoriaRequest.getDescricao());
        } else if (categoriaOptional.get().getDescricao() == null && categoriaRequest.getDescricao() != null) {
          categoria.setDescricao(categoriaRequest.getDescricao());
        } else {
          categoria.setDescricao(categoriaOptional.get().getDescricao());
        }

        try {
          Categoria c = categoriaRepository.save(categoria);
          return converter.converterCategoriaToCategoriaResponse(c);
        } catch (Exception e) {
          throw new InternalServerErrorException();
        }

      } else {
        throw new NotFoundException();
      }

    } catch (NotFoundException no) {
      throw new NotFoundException();
    }
  }

  @Override
  public Boolean delete(Long id) {
    try {
      Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

      if (categoriaOptional.isPresent()) {
        try {
          categoriaRepository.delete(categoriaOptional.get());
          return true;
        } catch (Exception e) {
          throw new InternalServerErrorException();
        }

      } else {
        return false;
      }

    } catch (NotFoundException no) {
      throw new NotFoundException();
    }
  }

}
