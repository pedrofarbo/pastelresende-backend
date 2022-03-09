package br.com.pasteldoresende.api.feiras.service;

import br.com.pasteldoresende.api.feiras.controller.request.FeiraRequest;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;

import java.util.List;

public interface FeiraService {
  List<FeiraResponse> getAll();
  FeiraResponse getById(Long id);
  FeiraResponse save(FeiraRequest feiraRequest);
  FeiraResponse update(Long id, FeiraRequest feiraRequest);
  Boolean delete(Long id);
}
