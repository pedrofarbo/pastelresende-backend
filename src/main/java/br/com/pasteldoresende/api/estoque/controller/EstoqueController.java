package br.com.pasteldoresende.api.estoque.controller;

import br.com.pasteldoresende.api.estoque.controller.request.EstoqueRequest;
import br.com.pasteldoresende.api.estoque.controller.response.EstoqueResponse;
import br.com.pasteldoresende.api.estoque.service.EstoqueService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class EstoqueController {

  private final EstoqueService estoqueService;

  @GetMapping(path = "/api/v1/estoque/produto/{id}", produces = {"application/json"})
  public ResponseEntity<EstoqueResponse> getByProductId(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    EstoqueResponse estoqueResponse = estoqueService.getByProductId(id);
    return ResponseEntity.ok(estoqueResponse);
  }

  @GetMapping(path = "/api/v1/estoque", produces = {"application/json"})
  public ResponseEntity<List<EstoqueResponse>> getAll() {
    return ResponseEntity.ok(estoqueService.getAll());
  }

  @GetMapping(path = "/api/v1/estoque/feira/{id}", produces = {"application/json"})
  public ResponseEntity<List<EstoqueResponse>> getAllByFeiraId(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(estoqueService.getAllByFeiraId(id));
  }

  @PutMapping(path = "/api/v1/estoque/produto/{id}", produces = {"application/json"}, consumes = {"application/json"})
  public ResponseEntity<Object> update(@RequestBody EstoqueRequest estoqueRequest, @PathVariable("id") Long id) {
    if (id == null || estoqueRequest.getValor() == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      EstoqueResponse estoqueResponse = estoqueService.update(id, estoqueRequest);
      return ResponseEntity.ok(estoqueResponse);
    } catch (Exception bad) {

      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "Sistema indisponível no momento, tente novamente mais tarde.");

      return ResponseEntity.internalServerError().body(obj);
    }
  }
}
