package br.com.pasteldoresende.api.vendas.service;

import br.com.pasteldoresende.api.vendas.controller.request.VendaRequest;
import br.com.pasteldoresende.api.vendas.controller.request.VendaUpdateRequest;
import br.com.pasteldoresende.api.vendas.controller.response.VendaResponse;

import java.util.List;

public interface VendaService {
  List<VendaResponse> getAll();
  VendaResponse getById(Long id);
  VendaResponse create(VendaRequest vendaRequest);
  List<VendaResponse> getAllByFeiraId(Long id);
  VendaResponse update(Long id, VendaUpdateRequest vendaRequest);
}
