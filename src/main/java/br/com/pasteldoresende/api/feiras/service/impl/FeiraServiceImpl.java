package br.com.pasteldoresende.api.feiras.service.impl;

import br.com.pasteldoresende.api.feiras.controller.request.FeiraRequest;
import br.com.pasteldoresende.api.feiras.controller.response.FeiraResponse;
import br.com.pasteldoresende.api.feiras.converter.FeiraConverter;
import br.com.pasteldoresende.api.feiras.model.Feira;
import br.com.pasteldoresende.api.feiras.repository.FeiraRepository;
import br.com.pasteldoresende.api.feiras.service.FeiraService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FeiraServiceImpl implements FeiraService {

  private final FeiraRepository feiraRepository;
  private final FeiraConverter converter;

  @Override
  public List<FeiraResponse> getAll() {
    List<Feira> feiras = new ArrayList<>();

    try {
      Iterable<Feira> feirasIterable = feiraRepository.findAll();
      feirasIterable.iterator().forEachRemaining(feiras::add);

      if (!feiras.isEmpty()) {
        return converter.converterFeiraListToFeiraResponseList(feiras);
      } else {
        return new ArrayList<>();
      }
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }

  }

  @Override
  public FeiraResponse getById(Long id) {
    try {
      Optional<Feira> feira = feiraRepository.findById(id);
      return feira.map(converter::converterFeiraToFeiraResponse).orElse(null);
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public FeiraResponse save(FeiraRequest feiraRequest) {
    Feira feira = converter.converterFeiraRequestToFeira(feiraRequest);

    try {
      Feira f1 = feiraRepository.findByNome(feiraRequest.getNome());

      if (f1 != null) {
        throw new BadRequestException();
      }

    } catch (BadRequestException bad) {
      throw new BadRequestException();
    }

    try {
      Feira f = feiraRepository.save(feira);

      if (f.getId() != null) {
        return converter.converterFeiraToFeiraResponse(f);
      } else {
        throw new InternalServerErrorException();
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new InternalServerErrorException();
    }
  }

  @Override
  public FeiraResponse update(Long id, FeiraRequest feiraRequest) {
    try {
      Feira f1 = feiraRepository.findByNome(feiraRequest.getNome());

      if (f1 != null && !Objects.equals(f1.getId(), id)) {
        throw new BadRequestException();
      }

    } catch (BadRequestException bad) {
      throw new BadRequestException();
    }

    try {
      Optional<Feira> feiraOptional = feiraRepository.findById(id);

      if (feiraOptional.isPresent()) {
        Feira feira = converter.converterFeiraRequestToFeira(feiraRequest);
        feira.setId(feiraOptional.get().getId());

        if (feiraOptional.get().getDescricao() != null && feiraRequest.getDescricao() != null) {
          feira.setDescricao(feiraRequest.getDescricao());
        } else if (feiraOptional.get().getDescricao() == null && feiraRequest.getDescricao() != null) {
          feira.setDescricao(feiraRequest.getDescricao());
        } else {
          feira.setDescricao(feiraOptional.get().getDescricao());
        }

        try {
          feira.getEndereco().setId(feiraOptional.get().getEndereco().getId());
          Feira f = feiraRepository.save(feira);
          return converter.converterFeiraToFeiraResponse(f);
        } catch (Exception e) {
          e.printStackTrace();
          throw new InternalServerErrorException();
        }

      } else {
        throw new NotFoundException();
      }

    } catch (NotFoundException no) {
      throw new NotFoundException();
    }
  }

  @Override
  public Boolean delete(Long id) {
    try {
      Optional<Feira> feiraOptional = feiraRepository.findById(id);

      if (feiraOptional.isPresent()) {
        try {
          feiraRepository.delete(feiraOptional.get());
          return true;
        } catch (Exception e) {
          throw new InternalServerErrorException();
        }

      } else {
        return false;
      }

    } catch (NotFoundException no) {
      throw new NotFoundException();
    }
  }

}
