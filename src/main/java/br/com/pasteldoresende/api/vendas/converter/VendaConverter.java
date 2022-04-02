package br.com.pasteldoresende.api.vendas.converter;

import br.com.pasteldoresende.api.categorias.controller.response.CategoriaResponse;
import br.com.pasteldoresende.api.categorias.model.Categoria;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.model.Feira;
import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;
import br.com.pasteldoresende.api.produtos.model.Produto;
import br.com.pasteldoresende.api.vendas.controller.request.VendaProdutoRequest;
import br.com.pasteldoresende.api.vendas.controller.request.VendaRequest;
import br.com.pasteldoresende.api.vendas.controller.request.VendaUpdateProdutoRequest;
import br.com.pasteldoresende.api.vendas.controller.request.VendaUpdateRequest;
import br.com.pasteldoresende.api.vendas.controller.response.VendaProdutoResponse;
import br.com.pasteldoresende.api.vendas.controller.response.VendaResponse;
import br.com.pasteldoresende.api.vendas.model.Venda;
import br.com.pasteldoresende.api.vendas.model.VendaProduto;
import br.com.pasteldoresende.api.vendas.model.VendaProdutoId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendaConverter {

  public List<VendaResponse> converterVendaListToVendaResponseList(List<Venda> vendaList) {
    List<VendaResponse> vendaResponseList = new ArrayList<>();

    vendaList.forEach(v -> {
      VendaResponse vendaResponse = new VendaResponse();

      if (v.getId() != null) {
        vendaResponse.setId(v.getId());
      }

      List<VendaProdutoResponse> vendaProdutoResponseList = new ArrayList<>();
      VendaProdutoResponse vendaProdutoResponse = new VendaProdutoResponse();

      ProdutoResponse produtoResponse = new ProdutoResponse();
      CategoriaResponse categoriaResponse = new CategoriaResponse();
      FeiraResponse feiraResponse = new FeiraResponse();

      if (v.getFeira() != null) {
        if (v.getFeira().getId() != null) {
          feiraResponse.setId(v.getFeira().getId());
        }

        feiraResponse.setDescricao(v.getFeira().getDescricao());
        feiraResponse.setNome(v.getFeira().getNome());
        feiraResponse.setResponsavel(null);
        feiraResponse.setEndereco(null);
      }

      v.getProdutos().forEach(p -> {
        if (p.getProduto() != null) {
          if (p.getProduto().getId() != null) {
            produtoResponse.setId(p.getProduto().getId());
          }

          if (p.getProduto().getCategoria() != null) {
            if (p.getProduto().getCategoria().getId() != null) {
              categoriaResponse.setId(p.getProduto().getCategoria().getId());
            }

            categoriaResponse.setDescricao(p.getProduto().getCategoria().getDescricao());
            categoriaResponse.setNome(p.getProduto().getCategoria().getNome());
          }

          produtoResponse.setDescricao(p.getProduto().getDescricao());
          produtoResponse.setNome(p.getProduto().getNome());
          produtoResponse.setCategoria(categoriaResponse);
          produtoResponse.setFeira(null);
          produtoResponse.setImagemBase64(p.getProduto().getImagemBase64());
          produtoResponse.setPreco(p.getProduto().getPreco());

          vendaProdutoResponse.setProduto(produtoResponse);
          vendaProdutoResponse.setQuantidade(p.getQuantidade());
          vendaProdutoResponseList.add(vendaProdutoResponse);
        }
      });

      vendaResponse.setVendaProdutos(vendaProdutoResponseList);
      vendaResponse.setTipoVenda(v.getTipoVenda());
      vendaResponse.setTotal(v.getTotal());

      vendaResponseList.add(vendaResponse);
    });

    return vendaResponseList;
  }

  public VendaResponse converterVendaToVendaResponse(Venda v) {
    VendaResponse vendaResponse = new VendaResponse();

    if (v.getId() != null) {
      vendaResponse.setId(v.getId());
    }

    List<VendaProdutoResponse> vendaProdutoResponseList = new ArrayList<>();
    VendaProdutoResponse vendaProdutoResponse = new VendaProdutoResponse();

    ProdutoResponse produtoResponse = new ProdutoResponse();
    CategoriaResponse categoriaResponse = new CategoriaResponse();
    FeiraResponse feiraResponse = new FeiraResponse();

    if (v.getFeira() != null) {
      if (v.getFeira().getId() != null) {
        feiraResponse.setId(v.getFeira().getId());
      }

      feiraResponse.setDescricao(v.getFeira().getDescricao());
      feiraResponse.setNome(v.getFeira().getNome());
      feiraResponse.setResponsavel(null);
      feiraResponse.setEndereco(null);
    }

    v.getProdutos().forEach(p -> {
      if (p.getProduto() != null) {
        if (p.getProduto().getId() != null) {
          produtoResponse.setId(p.getProduto().getId());
        }

        if (p.getProduto().getCategoria() != null) {
          if (p.getProduto().getCategoria().getId() != null) {
            categoriaResponse.setId(p.getProduto().getCategoria().getId());
          }

          categoriaResponse.setDescricao(p.getProduto().getCategoria().getDescricao());
          categoriaResponse.setNome(p.getProduto().getCategoria().getNome());
        }

        produtoResponse.setDescricao(p.getProduto().getDescricao());
        produtoResponse.setNome(p.getProduto().getNome());
        produtoResponse.setCategoria(categoriaResponse);
        produtoResponse.setFeira(null);
        produtoResponse.setImagemBase64(p.getProduto().getImagemBase64());
        produtoResponse.setPreco(p.getProduto().getPreco());

        vendaProdutoResponse.setProduto(produtoResponse);
        vendaProdutoResponse.setQuantidade(p.getQuantidade());
        vendaProdutoResponseList.add(vendaProdutoResponse);
      }
    });

    vendaResponse.setVendaProdutos(vendaProdutoResponseList);
    vendaResponse.setTipoVenda(v.getTipoVenda());
    vendaResponse.setTotal(v.getTotal());

    return vendaResponse;
  }

  public Venda vendaRequestToVenda(VendaRequest vendaRequest, List<ProdutoResponse> produtosResponseList, FeiraResponse feiraResponse) {
    Venda venda = new Venda();
    venda.setTipoVenda(vendaRequest.getTipoVenda());

    Feira feira = new Feira();
    feira.setId(feiraResponse.getId());
    feira.setNome(feiraResponse.getNome());
    feira.setDescricao(feiraResponse.getDescricao());
    feira.setEndereco(null);
    feira.setResponsavel(null);

    venda.setFeira(feira);

    List<VendaProduto> vendaProdutoList = new ArrayList<>();

    BigDecimal total = BigDecimal.ZERO;

    for (ProdutoResponse produtoResponse : produtosResponseList) {
      VendaProduto vendaProduto = new VendaProduto();

      Produto produto = new Produto();
      Categoria categoria = new Categoria();

      categoria.setId(produtoResponse.getCategoria().getId());
      categoria.setNome(produtoResponse.getCategoria().getNome());
      categoria.setDescricao(produtoResponse.getCategoria().getDescricao());

      produto.setId(produtoResponse.getId());
      produto.setImagemBase64(produtoResponse.getImagemBase64());
      produto.setFeira(feira);
      produto.setNome(produtoResponse.getNome());
      produto.setDescricao(produtoResponse.getDescricao());
      produto.setCategoria(categoria);
      produto.setPreco(produtoResponse.getPreco());

      vendaProduto.setProduto(produto);
      vendaProduto.setQuantidade(vendaRequest.getProdutos().stream().filter(p -> p.getProdutoId().equals(produtoResponse.getId())).findFirst().orElseGet(VendaProdutoRequest::new).getQuantidade());

      vendaProdutoList.add(vendaProduto);

      total = total.add(produtoResponse.getPreco());

      vendaProduto.setVenda(venda);

      if (vendaProduto.getId() == null) {
        vendaProduto.setId(new VendaProdutoId());
      }

      if (vendaProduto.getId().getVendaId() == null) {
        vendaProduto.getId().setVendaId(venda.getId());
      }

      if (vendaProduto.getId().getSequencia() == null) {
        final int sequencia = venda.getProdutos().stream()
          .filter(pi -> pi.getId() != null && pi.getId().getSequencia() != null)
          .mapToInt(pi -> Math.toIntExact(pi.getId().getSequencia()))
          .max()
          .orElse(0) + 1;

        vendaProduto.getId().setSequencia((long) sequencia);
      }
    }

    Set<VendaProduto> vendaProdutoSet = new HashSet<>(vendaProdutoList);

    venda.setProdutos(vendaProdutoSet);
    venda.setTotal(total);

    return venda;
  }

  public Venda vendaUpdateRequestToVenda(VendaUpdateRequest vendaRequest, List<ProdutoResponse> produtosResponseList, FeiraResponse feiraResponse) {
    Venda venda = new Venda();
    venda.setTipoVenda(vendaRequest.getTipoVenda());

    Feira feira = new Feira();
    feira.setId(feiraResponse.getId());
    feira.setNome(feiraResponse.getNome());
    feira.setDescricao(feiraResponse.getDescricao());
    feira.setEndereco(null);
    feira.setResponsavel(null);

    venda.setFeira(feira);

    List<VendaProduto> vendaProdutoList = new ArrayList<>();

    BigDecimal total = BigDecimal.ZERO;

    for (ProdutoResponse produtoResponse : produtosResponseList) {
      VendaProduto vendaProduto = new VendaProduto();

      Produto produto = new Produto();
      Categoria categoria = new Categoria();

      categoria.setId(produtoResponse.getCategoria().getId());
      categoria.setNome(produtoResponse.getCategoria().getNome());
      categoria.setDescricao(produtoResponse.getCategoria().getDescricao());

      produto.setId(produtoResponse.getId());
      produto.setImagemBase64(produtoResponse.getImagemBase64());
      produto.setFeira(null);
      produto.setNome(produtoResponse.getNome());
      produto.setDescricao(produtoResponse.getDescricao());
      produto.setCategoria(categoria);

      vendaProduto.setVenda(null);
      vendaProduto.setProduto(produto);
      vendaProduto.setQuantidade(vendaRequest.getProdutos().stream().filter(p -> p.getProdutoId().equals(produtoResponse.getId())).findFirst().orElseGet(VendaUpdateProdutoRequest::new).getQuantidadeNova());

      vendaProdutoList.add(vendaProduto);

      total = total.add(produtoResponse.getPreco());
    }

    Set<VendaProduto> vendaProdutoSet = new HashSet<>(vendaProdutoList);

    venda.setProdutos(vendaProdutoSet);
    venda.setTotal(total);

    return venda;
  }
}
