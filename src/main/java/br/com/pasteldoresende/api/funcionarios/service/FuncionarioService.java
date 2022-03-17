package br.com.pasteldoresende.api.funcionarios.service;

import br.com.pasteldoresende.api.funcionarios.controller.request.FuncionarioRequest;
import br.com.pasteldoresende.api.funcionarios.controller.response.FuncionarioResponse;

import java.util.List;

public interface FuncionarioService {
  List<FuncionarioResponse> getAll(Boolean freela);
  FuncionarioResponse getById(Long id);
  FuncionarioResponse save(FuncionarioRequest funcionarioRequest);
  FuncionarioResponse update(Long id, FuncionarioRequest funcionarioRequest);
  Boolean delete(Long id);
}
