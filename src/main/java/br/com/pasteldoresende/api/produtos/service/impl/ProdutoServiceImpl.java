package br.com.pasteldoresende.api.produtos.service.impl;

import br.com.pasteldoresende.api.categorias.model.Categoria;
import br.com.pasteldoresende.api.categorias.repository.CategoriaRepository;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.model.Feira;
import br.com.pasteldoresende.api.feiras.repository.FeiraRepository;
import br.com.pasteldoresende.api.produtos.controller.request.ProdutoRequest;
import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;
import br.com.pasteldoresende.api.produtos.converter.ProdutoConverter;
import br.com.pasteldoresende.api.produtos.model.Produto;
import br.com.pasteldoresende.api.produtos.repository.ProdutoRepository;
import br.com.pasteldoresende.api.produtos.service.ProdutoService;
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
public class ProdutoServiceImpl implements ProdutoService {

  private final ProdutoRepository produtoRepository;
  private final CategoriaRepository categoriaRepository;
  private final FeiraRepository feiraRepository;
  private final ProdutoConverter converter;

  @Override
  public List<ProdutoResponse> getAll() {
    List<Produto> produtos = new ArrayList<>();

    try {
      Iterable<Produto> produtosIterable = produtoRepository.findAll();
      produtosIterable.iterator().forEachRemaining(produtos::add);

      if (!produtos.isEmpty()) {
        return converter.converterProdutoListToProdutoResponseList(produtos);
      } else {
        return new ArrayList<>();
      }
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }

  }

  @Override
  public ProdutoResponse getById(Long id) {
    try {
      Optional<Produto> produto = produtoRepository.findById(id);
      return produto.map(converter::converterProdutoToProdutoResponse).orElse(null);
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public ProdutoResponse save(ProdutoRequest produtoRequest) {
    Feira feira = new Feira();
    Categoria categoria = new Categoria();

    try {
      Optional<Feira> f = feiraRepository.findById(produtoRequest.getFeiraId());

      if (f.isPresent()) {
        feira = f.get();
      }
    } catch (NotFoundException no) {
      throw new NotFoundException();
    }

    try {
      Optional<Categoria> c = categoriaRepository.findById(produtoRequest.getCategoriaId());

      if (c.isPresent()) {
        categoria = c.get();
      }
    } catch (NotFoundException no) {
      throw new NotFoundException();
    }

    Produto produto = converter.converterProdutoRequestToProduto(produtoRequest, categoria, feira);

    try {
      Produto c1 = produtoRepository.findByFeiraAndNome(feira, produtoRequest.getNome());

      if (c1 != null) {
        throw new BadRequestException();
      }

    } catch (BadRequestException bad) {
      throw new BadRequestException();
    }

    try {
      Produto c = produtoRepository.save(produto);

      if (c.getId() != null) {
        return converter.converterProdutoToProdutoResponse(c);
      } else {
        throw new InternalServerErrorException();
      }

    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public ProdutoResponse update(Long id, ProdutoRequest produtoRequest) {
    Feira feira = new Feira();
    Categoria categoria = new Categoria();

    try {
      Optional<Feira> f = feiraRepository.findById(produtoRequest.getFeiraId());

      if (f.isPresent()) {
        feira = f.get();
      }
    } catch (NotFoundException no) {
      throw new NotFoundException();
    }

    try {
      Optional<Categoria> c = categoriaRepository.findById(produtoRequest.getCategoriaId());

      if (c.isPresent()) {
        categoria = c.get();
      }
    } catch (NotFoundException no) {
      throw new NotFoundException();
    }

    try {
      Produto c1 = produtoRepository.findByFeiraAndNome(feira, produtoRequest.getNome());

      if (c1 != null && !Objects.equals(c1.getId(), id)) {
        throw new BadRequestException();
      }

    } catch (BadRequestException bad) {
      throw new BadRequestException();
    }

    try {
      Optional<Produto> produtoOptional = produtoRepository.findById(id);

      if (produtoOptional.isPresent()) {
        Produto produto = converter.converterProdutoRequestToProduto(produtoRequest, categoria, feira);
        produto.setId(produtoOptional.get().getId());

        if (produtoOptional.get().getDescricao() != null && produtoRequest.getDescricao() != null) {
          produto.setDescricao(produtoRequest.getDescricao());
        } else if (produtoOptional.get().getDescricao() == null && produtoRequest.getDescricao() != null) {
          produto.setDescricao(produtoRequest.getDescricao());
        } else {
          produto.setDescricao(produtoOptional.get().getDescricao());
        }

        try {
          Produto c = produtoRepository.save(produto);
          return converter.converterProdutoToProdutoResponse(c);
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
      Optional<Produto> produtoOptional = produtoRepository.findById(id);

      if (produtoOptional.isPresent()) {
        try {
          produtoRepository.delete(produtoOptional.get());
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
