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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

      if (!v.getProdutos().isEmpty()) {
        v.getProdutos().forEach(p -> {
          VendaProdutoResponse vendaProdutoResponse = new VendaProdutoResponse();
          ProdutoResponse produtoResponse = new ProdutoResponse();

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
            produtoResponse.setFeira(feiraResponse);
            produtoResponse.setImagemBase64(p.getProduto().getImagemBase64());
            produtoResponse.setPreco(p.getProduto().getPreco());

            vendaProdutoResponse.setProduto(produtoResponse);
            vendaProdutoResponse.setId(p.getId());
            vendaProdutoResponse.setSequencia(p.getSequencia());
            vendaProdutoResponse.setQuantidade(p.getQuantidade());
            vendaProdutoResponseList.add(vendaProdutoResponse);
          }
        });
      }

      vendaResponse.setVendaProdutos(vendaProdutoResponseList);
      vendaResponse.setTipoVenda(v.getTipoVenda());
      vendaResponse.setTotal(v.getTotal());
      vendaResponse.setFeira(feiraResponse);
      vendaResponse.setDataCriacao(v.getDataCriacao());
      vendaResponse.setDataAtualizacao(v.getDataAtualizacao());

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
        ProdutoResponse produtoResponse = new ProdutoResponse();
        VendaProdutoResponse vendaProdutoResponse = new VendaProdutoResponse();

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
        produtoResponse.setFeira(feiraResponse);
        produtoResponse.setImagemBase64(p.getProduto().getImagemBase64());
        produtoResponse.setPreco(p.getProduto().getPreco());

        vendaProdutoResponse.setProduto(produtoResponse);
        vendaProdutoResponse.setId(p.getId());
        vendaProdutoResponse.setSequencia(p.getSequencia());
        vendaProdutoResponse.setQuantidade(p.getQuantidade());
        vendaProdutoResponseList.add(vendaProdutoResponse);
      }
    });

    vendaResponse.setVendaProdutos(vendaProdutoResponseList);
    vendaResponse.setTipoVenda(v.getTipoVenda());
    vendaResponse.setTotal(v.getTotal());
    vendaResponse.setFeira(feiraResponse);
    vendaResponse.setDataCriacao(v.getDataCriacao());
    vendaResponse.setDataAtualizacao(v.getDataAtualizacao());

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

    int sequencia = 0;
    BigDecimal valorTotalProduto;

    for (ProdutoResponse produtoResponse : produtosResponseList) {
      valorTotalProduto = BigDecimal.ZERO;

      sequencia++;

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

      valorTotalProduto = valorTotalProduto.add(produtoResponse.getPreco().multiply(BigDecimal.valueOf(vendaProduto.getQuantidade())));
      total = total.add(valorTotalProduto);

      vendaProduto.setVenda(venda);

      vendaProduto.setSequencia(sequencia);

      vendaProdutoList.add(vendaProduto);
    }

    Set<VendaProduto> vendaProdutoSet = new HashSet<>(vendaProdutoList);

    venda.setProdutos(vendaProdutoSet);
    venda.setTotal(total);
    venda.setDataCriacao(LocalDateTime.now());

    return venda;
  }

  public Venda vendaUpdateRequestToVenda(Venda venda, VendaUpdateRequest vendaRequest, List<ProdutoResponse> produtosResponseList, FeiraResponse feiraResponse) {
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
    BigDecimal valorTotalProduto;

    for (ProdutoResponse produtoResponse : produtosResponseList) {
      valorTotalProduto = BigDecimal.ZERO;

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

      vendaProduto.setId(venda.getProdutos().stream().filter(p -> {
        assert p.getProduto().getId() != null;
        return p.getProduto().getId().equals(produtoResponse.getId());
      }).findFirst().orElseGet(VendaProduto::new).getId());

      vendaProduto.setSequencia(venda.getProdutos().stream().filter(p -> {
        assert p.getProduto().getId() != null;
        return p.getProduto().getId().equals(produtoResponse.getId());
      }).findFirst().orElseGet(VendaProduto::new).getSequencia());

      vendaProduto.setProduto(produto);
      vendaProduto.setQuantidade(vendaRequest.getProdutos().stream().filter(p -> p.getProdutoId().equals(produtoResponse.getId())).findFirst().orElseGet(VendaUpdateProdutoRequest::new).getQuantidadeNova());

      valorTotalProduto = valorTotalProduto.add(produtoResponse.getPreco().multiply(BigDecimal.valueOf(vendaProduto.getQuantidade())));
      total = total.add(valorTotalProduto);

      vendaProduto.setVenda(venda);
      vendaProdutoList.add(vendaProduto);
    }

    Set<VendaProduto> vendaProdutoSet = new HashSet<>(vendaProdutoList);

    venda.setProdutos(vendaProdutoSet);
    venda.setTotal(total);
    venda.setDataAtualizacao(LocalDateTime.now());

    return venda;
  }
}
