package br.com.pasteldoresende.api.feiras.converter;

import br.com.pasteldoresende.api.feiras.controller.request.FeiraRequest;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraEnderecoResponse;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.model.FeiraEndereco;
import br.com.pasteldoresende.api.feiras.model.Feira;
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

      feiraResponse.setId(feira.getId());
      feiraResponse.setNome(feira.getNome());
      feiraResponse.setDescricao(feira.getDescricao());
      feiraResponse.setResponsavel(feira.getResponsavel());
      feiraResponse.setObs(feira.getObs());
      feiraResponse.setEndereco(feiraEnderecoResponse);

      feiraResponseList.add(feiraResponse);
    }

    return feiraResponseList;
  }

  public FeiraResponse converterFeiraToFeiraResponse(Feira feira) {
    FeiraEnderecoResponse feiraEnderecoResponse = new FeiraEnderecoResponse();

    FeiraResponse feiraResponse = new FeiraResponse();

    feiraEnderecoResponse.setId(feira.getEndereco().getId());
    feiraEnderecoResponse.setLogradouro(feira.getEndereco().getLogradouro());
    feiraEnderecoResponse.setNumero(feira.getEndereco().getNumero());
    feiraEnderecoResponse.setBairro(feira.getEndereco().getBairro());
    feiraEnderecoResponse.setCidade(feira.getEndereco().getCidade());
    feiraEnderecoResponse.setUf(feira.getEndereco().getUf());
    feiraEnderecoResponse.setCep(feira.getEndereco().getCep());
    feiraEnderecoResponse.setComplemento(feira.getEndereco().getComplemento());

    feiraResponse.setId(feira.getId());
    feiraResponse.setNome(feira.getNome());
    feiraResponse.setDescricao(feira.getDescricao());
    feiraResponse.setResponsavel(feira.getResponsavel());
    feiraResponse.setObs(feira.getObs());
    feiraResponse.setEndereco(feiraEnderecoResponse);

    return feiraResponse;
  }

  public Feira converterFeiraRequestToFeira(FeiraRequest feiraRequest) {
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
    feira.setResponsavel(feiraRequest.getResponsavel());
    feira.setObs(feiraRequest.getObs());
    feira.setEndereco(endereco);

    return feira;
  }
}
