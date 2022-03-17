package br.com.pasteldoresende.api.funcionarios.controller;

import br.com.pasteldoresende.api.funcionarios.controller.request.FuncionarioRequest;
import br.com.pasteldoresende.api.funcionarios.controller.response.FuncionarioResponse;
import br.com.pasteldoresende.api.funcionarios.service.impl.FuncionarioServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class FuncionarioController {

  private final FuncionarioServiceImpl funcionarioServiceImpl;

  @GetMapping(path = "/api/v1/funcionarios/{id}", produces = {"application/json"})
  public ResponseEntity<FuncionarioResponse> getById(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok().body(funcionarioServiceImpl.getById(id));
  }

  @GetMapping(path = "/api/v1/funcionarios", produces = {"application/json"})
  public ResponseEntity<List<FuncionarioResponse>> getAll(@QueryParam("freela") Boolean freela) {
    return ResponseEntity.ok().body(funcionarioServiceImpl.getAll(freela));
  }

  @PutMapping(path = "/api/v1/funcionarios/{id}", produces = {"application/json"}, consumes = {"application/json"})
  public ResponseEntity<Object> update(@RequestBody FuncionarioRequest funcionarioRequest, @PathVariable("id") Long id) {
    if (funcionarioRequest.getNome() == null || id == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      return ResponseEntity.ok().body(funcionarioServiceImpl.update(id, funcionarioRequest));
    } catch (BadRequestException bad) {

      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "O funcionario já existe para o nome " + funcionarioRequest.getNome());

      return ResponseEntity.badRequest().body(obj);
    }
  }

  @PostMapping(path = "/api/v1/funcionarios", produces = "application/json", consumes = {"application/json"})
  public ResponseEntity<Object> save(@RequestBody FuncionarioRequest funcionarioRequest) {
    if (funcionarioRequest.getNome() == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      return ResponseEntity.status(201).body(funcionarioServiceImpl.save(funcionarioRequest));

    } catch (BadRequestException ex) {
      HashMap<String, String> obj = new HashMap<>();
      obj.put("msg", "O funcionario já existe para o nome " + funcionarioRequest.getNome());

      return ResponseEntity.badRequest().body(obj);
    }
  }

  @DeleteMapping(path = "/api/v1/funcionarios/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }

    if (funcionarioServiceImpl.delete(id)) {
      return ResponseEntity.status(200).body(true);
    }

    return ResponseEntity.status(200).body(false);
  }
}
