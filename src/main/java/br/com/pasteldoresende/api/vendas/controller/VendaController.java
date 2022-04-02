package br.com.pasteldoresende.api.vendas.controller;

import br.com.pasteldoresende.api.vendas.controller.request.VendaRequest;
import br.com.pasteldoresende.api.vendas.controller.request.VendaUpdateRequest;
import br.com.pasteldoresende.api.vendas.controller.response.VendaResponse;
import br.com.pasteldoresende.api.vendas.service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class VendaController {

  private final VendaService vendaService;

  @GetMapping(path = "/api/v1/venda/{id}", produces = {"application/json"})
  public ResponseEntity<VendaResponse> getById(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    VendaResponse vendaResponse = vendaService.getById(id);
    return ResponseEntity.ok(vendaResponse);
  }

  @GetMapping(path = "/api/v1/venda", produces = {"application/json"})
  public ResponseEntity<List<VendaResponse>> getAll() {
    return ResponseEntity.ok(vendaService.getAll());
  }

  @GetMapping(path = "/api/v1/venda/feira/{id}", produces = {"application/json"})
  public ResponseEntity<List<VendaResponse>> getAllByFeiraId(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(vendaService.getAllByFeiraId(id));
  }

  @PostMapping(path = "/api/v1/venda", produces = {"application/json"}, consumes = {"application/json"})
  public ResponseEntity<Object> create(@RequestBody VendaRequest vendaRequest) {
    if (vendaRequest.getProdutos().isEmpty() || vendaRequest.getFeiraId() == null || vendaRequest.getTipoVenda() == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      VendaResponse vendaResponse = vendaService.create(vendaRequest);
      return ResponseEntity.ok(vendaResponse);
    } catch (Exception bad) {

      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "Sistema indisponível no momento, tente novamente mais tarde.");

      return ResponseEntity.internalServerError().body(obj);
    }
  }

  @PutMapping(path = "/api/v1/venda/{id}", produces = {"application/json"}, consumes = {"application/json"})
  public ResponseEntity<Object> update(@RequestBody VendaUpdateRequest vendaRequest, @PathVariable("id") Long id) {
    if (id == null || vendaRequest.getProdutos().isEmpty() || vendaRequest.getFeiraId() == null || vendaRequest.getTipoVenda() == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      VendaResponse vendaResponse = vendaService.update(id, vendaRequest);
      return ResponseEntity.ok(vendaResponse);
    } catch (Exception bad) {

      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "Sistema indisponível no momento, tente novamente mais tarde.");

      return ResponseEntity.internalServerError().body(obj);
    }
  }
}
