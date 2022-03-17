package br.com.pasteldoresende.api.funcionarios.converter;

import br.com.pasteldoresende.api.funcionarios.controller.request.FuncionarioRequest;
import br.com.pasteldoresende.api.funcionarios.controller.response.FuncionarioResponse;
import br.com.pasteldoresende.api.funcionarios.model.Funcionario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FuncionarioConverter {
  public List<FuncionarioResponse> converterFuncionarioListToFuncionarioResponseList(List<Funcionario> funcionarioList) {
    List<FuncionarioResponse> funcionarioResponseList = new ArrayList<>();

    funcionarioList.forEach(funcionario -> {
      FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
      funcionarioResponse.setId(funcionario.getId());
      funcionarioResponse.setNome(funcionario.getNome());
      funcionarioResponse.setDescricao(funcionario.getDescricao() != null ? funcionario.getDescricao() : null);
      funcionarioResponse.setFreela(funcionario.getFreela());

      funcionarioResponseList.add(funcionarioResponse);
    });

    return funcionarioResponseList;
  }

  public FuncionarioResponse converterFuncionarioToFuncionarioResponse(Funcionario funcionario) {
    FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

    funcionarioResponse.setId(funcionario.getId());
    funcionarioResponse.setNome(funcionario.getNome());
    funcionarioResponse.setDescricao(funcionario.getDescricao() != null ? funcionario.getDescricao() : null);
    funcionarioResponse.setFreela(funcionario.getFreela());

    return funcionarioResponse;
  }

  public Funcionario converterFuncionarioRequestToFuncionario(FuncionarioRequest funcionarioRequest) {
    Funcionario funcionario = new Funcionario();
    funcionario.setNome(funcionarioRequest.getNome());

    if(funcionarioRequest.getDescricao() != null) {
      funcionario.setDescricao(funcionarioRequest.getDescricao());
    }

    if(funcionarioRequest.getFreela()) {
      funcionario.setFreela(funcionarioRequest.getFreela());
    } else {
      funcionario.setFreela(false);
    }

    return funcionario;
  }
}
