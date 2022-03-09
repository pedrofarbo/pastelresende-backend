package br.com.pasteldoresende.api.feiras.controller;

import br.com.pasteldoresende.api.feiras.controller.request.FeiraRequest;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.service.FeiraService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class FeiraController {

  private final FeiraService feiraService;

  @GetMapping(path = "/api/v1/feiras/{id}", produces = {"application/json"})
  public ResponseEntity<FeiraResponse> getById(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok().body(feiraService.getById(id));
  }

  @GetMapping(path = "/api/v1/feiras", produces = {"application/json"})
  public ResponseEntity<List<FeiraResponse>> getAll() {
    return ResponseEntity.ok().body(feiraService.getAll());
  }

  @PutMapping(path = "/api/v1/feiras/{id}", produces = {"application/json"}, consumes = {"application/json"})
  public ResponseEntity<Object> update(@RequestBody FeiraRequest feiraRequest, @PathVariable("id") Long id) {
    if (feiraRequest.getNome() == null || id == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      return ResponseEntity.ok().body(feiraService.update(id, feiraRequest));
    } catch (BadRequestException bad) {

      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "A feira já existe para o nome " + feiraRequest.getNome());

      return ResponseEntity.badRequest().body(obj);
    }
  }

  @PostMapping(path = "/api/v1/feiras", produces = "application/json", consumes = {"application/json"})
  public ResponseEntity<Object> save(@RequestBody FeiraRequest feiraRequest) {
    if (feiraRequest.getNome() == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      return ResponseEntity.status(201).body(feiraService.save(feiraRequest));

    } catch (BadRequestException ex) {
      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "A feira já existe para o nome " + feiraRequest.getNome());

      return ResponseEntity.badRequest().body(obj);
    }
  }

  @DeleteMapping(path = "/api/v1/feiras/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    if (feiraService.delete(id)) {
      return ResponseEntity.status(200).body(true);
    }

    return ResponseEntity.status(200).body(false);
  }
}
