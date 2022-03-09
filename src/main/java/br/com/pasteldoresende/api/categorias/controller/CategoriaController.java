package br.com.pasteldoresende.api.categorias.controller;

import br.com.pasteldoresende.api.categorias.controller.request.CategoriaRequest;
import br.com.pasteldoresende.api.categorias.controller.response.CategoriaResponse;
import br.com.pasteldoresende.api.categorias.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class CategoriaController {

  private final CategoriaService categoriaService;

  @GetMapping(path = "/api/v1/categorias/{id}", produces = {"application/json"})
  public ResponseEntity<CategoriaResponse> getById(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    CategoriaResponse categoriaResponse = categoriaService.getById(id);
    return ResponseEntity.ok(categoriaResponse);
  }

  @GetMapping(path = "/api/v1/categorias", produces = {"application/json"})
  public ResponseEntity<List<CategoriaResponse>> getAll() {
    return ResponseEntity.ok(categoriaService.getAll());
  }

  @PutMapping(path = "/api/v1/categorias/{id}", produces = {"application/json"}, consumes = {"application/json"})
  public ResponseEntity<Object> update(@RequestBody CategoriaRequest categoriaRequest, @PathVariable("id") Long id) {
    if (categoriaRequest.getNome() == null || id == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      CategoriaResponse categoriaResponse = categoriaService.update(id, categoriaRequest);
      return ResponseEntity.ok(categoriaResponse);
    } catch (BadRequestException bad) {

      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "A categoria já existe para o nome " + categoriaRequest.getNome());

      return ResponseEntity.badRequest().body(obj);
    }
  }

  @PostMapping(path = "/api/v1/categorias", produces = "application/json", consumes = {"application/json"})
  public ResponseEntity<Object> save(@RequestBody CategoriaRequest categoriaRequest) {
    if (categoriaRequest.getNome() == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      CategoriaResponse categoriaResponse = categoriaService.save(categoriaRequest);
      return ResponseEntity.status(201).body(categoriaResponse);

    } catch (BadRequestException ex) {
      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "A categoria já existe para o nome " + categoriaRequest.getNome());

      return ResponseEntity.badRequest().body(obj);
    }
  }

  @DeleteMapping(path = "/api/v1/categorias/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    if (categoriaService.delete(id)) {
      return ResponseEntity.status(200).body(true);
    }

    return ResponseEntity.status(200).body(false);
  }
}
