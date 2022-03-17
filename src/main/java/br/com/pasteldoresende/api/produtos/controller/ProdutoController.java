package br.com.pasteldoresende.api.produtos.controller;

import br.com.pasteldoresende.api.produtos.controller.request.ProdutoRequest;
import br.com.pasteldoresende.api.produtos.controller.response.ProdutoResponse;
import br.com.pasteldoresende.api.produtos.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProdutoController {

  private final ProdutoService produtoService;

  @GetMapping(path = "/api/v1/produtos/{id}", produces = {"application/json"})
  public ResponseEntity<ProdutoResponse> getById(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    ProdutoResponse produtoResponse = produtoService.getById(id);
    return ResponseEntity.ok(produtoResponse);
  }

  @GetMapping(path = "/api/v1/produtos", produces = {"application/json"})
  public ResponseEntity<List<ProdutoResponse>> getAll() {
    return ResponseEntity.ok(produtoService.getAll());
  }

  @PutMapping(path = "/api/v1/produtos/{id}", produces = {"application/json"}, consumes = {"application/json"})
  public ResponseEntity<Object> update(@RequestBody ProdutoRequest produtoRequest, @PathVariable("id") Long id) {
    if (produtoRequest.getNome() == null || id == null || produtoRequest.getFeiraId() == null || produtoRequest.getCategoriaId() == null || produtoRequest.getPreco() == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      ProdutoResponse produtoResponse = produtoService.update(id, produtoRequest);
      return ResponseEntity.ok(produtoResponse);
    } catch (BadRequestException bad) {

      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "O produto já existe para a feira selecionada com o nome escolhido.");

      return ResponseEntity.badRequest().body(obj);
    }
  }

  @PostMapping(path = "/api/v1/produtos", produces = "application/json", consumes = {"application/json"})
  public ResponseEntity<Object> save(@RequestBody ProdutoRequest produtoRequest) {
    if (produtoRequest.getNome() == null || produtoRequest.getFeiraId() == null || produtoRequest.getCategoriaId() == null || produtoRequest.getPreco() == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      ProdutoResponse produtoResponse = produtoService.save(produtoRequest);
      return ResponseEntity.status(201).body(produtoResponse);

    } catch (BadRequestException ex) {
      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "O produto já existe para a feira selecionada com o nome escolhido.");

      return ResponseEntity.badRequest().body(obj);
    }
  }

  @DeleteMapping(path = "/api/v1/produtos/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    if (produtoService.delete(id)) {
      return ResponseEntity.status(200).body(true);
    }

    return ResponseEntity.status(200).body(false);
  }
}
