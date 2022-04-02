package br.com.pasteldoresende.api.feiras.converter;

import br.com.pasteldoresende.api.feiras.controller.request.FeiraRequest;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraEnderecoResponse;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.model.FeiraEndereco;
import br.com.pasteldoresende.api.feiras.model.Feira;
import br.com.pasteldoresende.api.funcionarios.controller.request.FuncionarioRequest;
import br.com.pasteldoresende.api.funcionarios.controller.response.FuncionarioResponse;
import br.com.pasteldoresende.api.funcionarios.model.Funcionario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeiraConverter {
  public List<FeiraResponse> converterFeiraListToFeiraResponseList(List<Feira> feiras) {
    List<FeiraResponse> feiraResponseList = new ArrayList<>();

    for (Feira feira : feiras) {
      FeiraResponse feiraResponse = new FeiraResponse();
      FeiraEnderecoResponse feiraEnderecoResponse = new FeiraEnderecoResponse();

      feiraEnderecoResponse.setId(feira.getEndereco().getId());
      feiraEnderecoResponse.setLogradouro(feira.getEndereco().getLogradouro());
      feiraEnderecoResponse.setNumero(feira.getEndereco().getNumero());
      feiraEnderecoResponse.setBairro(feira.getEndereco().getBairro());
      feiraEnderecoResponse.setCidade(feira.getEndereco().getCidade());
      feiraEnderecoResponse.setUf(feira.getEndereco().getUf());
      feiraEnderecoResponse.setCep(feira.getEndereco().getCep());
      feiraEnderecoResponse.setComplemento(feira.getEndereco().getComplemento());

      FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

      if(feira.getResponsavel() != null) {
        if(feira.getResponsavel().getId() != null) {
          funcionarioResponse.setId(feira.getResponsavel().getId());
        }

        funcionarioResponse.setNome(feira.getResponsavel().getNome());
        funcionarioResponse.setDescricao(feira.getResponsavel().getDescricao());
        funcionarioResponse.setFreela(feira.getResponsavel().getFreela());
      }

      feiraResponse.setId(feira.getId());
      feiraResponse.setNome(feira.getNome());
      feiraResponse.setDescricao(feira.getDescricao());
      feiraResponse.setResponsavel(funcionarioResponse);
      feiraResponse.setObs(feira.getObs());
      feiraResponse.setEndereco(feiraEnderecoResponse);

      feiraResponseList.add(feiraResponse);
    }

    return feiraResponseList;
  }

  public FeiraResponse converterFeiraToFeiraResponse(Feira feira) {
    FeiraEnderecoResponse feiraEnderecoResponse = new FeiraEnderecoResponse();

    FeiraResponse feiraResponse = new FeiraResponse();

    if(feira.getEndereco() != null) {
      feiraEnderecoResponse.setId(feira.getEndereco().getId());
      feiraEnderecoResponse.setLogradouro(feira.getEndereco().getLogradouro());
      feiraEnderecoResponse.setNumero(feira.getEndereco().getNumero());
      feiraEnderecoResponse.setBairro(feira.getEndereco().getBairro());
      feiraEnderecoResponse.setCidade(feira.getEndereco().getCidade());
      feiraEnderecoResponse.setUf(feira.getEndereco().getUf());
      feiraEnderecoResponse.setCep(feira.getEndereco().getCep());
      feiraEnderecoResponse.setComplemento(feira.getEndereco().getComplemento());
      feiraResponse.setEndereco(feiraEnderecoResponse);
    } else {
      feiraResponse.setEndereco(null);
    }

    FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

    if(feira.getResponsavel() != null) {
      if(feira.getResponsavel().getId() != null) {
        funcionarioResponse.setId(feira.getResponsavel().getId());
      }

      funcionarioResponse.setNome(feira.getResponsavel().getNome());
      funcionarioResponse.setDescricao(feira.getResponsavel().getDescricao());
      funcionarioResponse.setFreela(feira.getResponsavel().getFreela());
    }

    feiraResponse.setId(feira.getId());
    feiraResponse.setNome(feira.getNome());
    feiraResponse.setDescricao(feira.getDescricao());
    feiraResponse.setResponsavel(funcionarioResponse);
    feiraResponse.setObs(feira.getObs());

    return feiraResponse;
  }

  public Feira converterFeiraRequestToFeira(FeiraRequest feiraRequest, Funcionario funcionario) {
    Feira feira = new Feira();
    FeiraEndereco endereco = new FeiraEndereco();

    endereco.setLogradouro(feiraRequest.getEndereco().getLogradouro());
    endereco.setNumero(feiraRequest.getEndereco().getNumero());
    endereco.setBairro(feiraRequest.getEndereco().getBairro());
    endereco.setCidade(feiraRequest.getEndereco().getCidade());
    endereco.setUf(feiraRequest.getEndereco().getUf());
    endereco.setCep(feiraRequest.getEndereco().getCep());
    endereco.setComplemento(feiraRequest.getEndereco().getComplemento());

    feira.setNome(feiraRequest.getNome());
    feira.setDescricao(feiraRequest.getDescricao());
    feira.setResponsavel(funcionario);
    feira.setObs(feiraRequest.getObs());
    feira.setEndereco(endereco);

    return feira;
  }
}
