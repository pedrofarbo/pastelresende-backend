package br.com.pasteldoresende.api.funcionarios.service.impl;

import br.com.pasteldoresende.api.funcionarios.controller.request.FuncionarioRequest;
import br.com.pasteldoresende.api.funcionarios.controller.response.FuncionarioResponse;
import br.com.pasteldoresende.api.funcionarios.converter.FuncionarioConverter;
import br.com.pasteldoresende.api.funcionarios.model.Funcionario;
import br.com.pasteldoresende.api.funcionarios.repository.FuncionarioRepository;
import br.com.pasteldoresende.api.funcionarios.service.FuncionarioService;
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
public class FuncionarioServiceImpl implements FuncionarioService {

  private FuncionarioConverter converter;
  private FuncionarioRepository funcionarioRepository;

  @Override
  public List<FuncionarioResponse> getAll(Boolean freela) {
    List<Funcionario> funcionarioList = new ArrayList<>();

    try {
      if (freela == null) {
        Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
        funcionarios.iterator().forEachRemaining(funcionarioList::add);

        if (!funcionarioList.isEmpty()) {
          return converter.converterFuncionarioListToFuncionarioResponseList(funcionarioList);
        } else {
          return new ArrayList<>();
        }
      } else if (!freela) {
        Iterable<Funcionario> funcionarios = funcionarioRepository.findAllByFreelaFalse();
        funcionarios.iterator().forEachRemaining(funcionarioList::add);

        if (!funcionarioList.isEmpty()) {
          return converter.converterFuncionarioListToFuncionarioResponseList(funcionarioList);
        } else {
          return new ArrayList<>();
        }
      } else {
        Iterable<Funcionario> funcionarios = funcionarioRepository.findAllByFreelaTrue();
        funcionarios.iterator().forEachRemaining(funcionarioList::add);

        if (!funcionarioList.isEmpty()) {
          return converter.converterFuncionarioListToFuncionarioResponseList(funcionarioList);
        } else {
          return new ArrayList<>();
        }
      }

    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public FuncionarioResponse getById(Long id) {
    try {
      Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
      return funcionario.map(converter::converterFuncionarioToFuncionarioResponse).orElse(null);
    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public FuncionarioResponse save(FuncionarioRequest funcionarioRequest) {
    Funcionario funcionario = converter.converterFuncionarioRequestToFuncionario(funcionarioRequest);

    try {
      Funcionario f1 = funcionarioRepository.findByNome(funcionarioRequest.getNome());

      if (f1 != null) {
        throw new BadRequestException();
      }
    } catch (BadRequestException bad) {
      throw new BadRequestException();
    }

    try {
      Funcionario f = funcionarioRepository.save(funcionario);

      if (f.getId() != null) {
        return converter.converterFuncionarioToFuncionarioResponse(f);
      } else {
        throw new InternalServerErrorException();
      }

    } catch (Exception e) {
      throw new InternalServerErrorException();
    }
  }

  @Override
  public FuncionarioResponse update(Long id, FuncionarioRequest funcionarioRequest) {
    try {
      Funcionario f1 = funcionarioRepository.findByNome(funcionarioRequest.getNome());

      if (f1 != null && !Objects.equals(f1.getId(), id)) {
        throw new BadRequestException();
      }

    } catch (BadRequestException bad) {
      throw new BadRequestException();
    }

    try {
      Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

      if (funcionarioOptional.isPresent()) {
        Funcionario funcionario = converter.converterFuncionarioRequestToFuncionario(funcionarioRequest);
        funcionario.setId(funcionarioOptional.get().getId());

        if (funcionarioOptional.get().getDescricao() != null && funcionarioRequest.getDescricao() != null) {
          funcionario.setDescricao(funcionarioRequest.getDescricao());
        } else if (funcionarioOptional.get().getDescricao() == null && funcionarioRequest.getDescricao() != null) {
          funcionario.setDescricao(funcionarioRequest.getDescricao());
        } else {
          funcionario.setDescricao(funcionarioOptional.get().getDescricao());
        }

        try {
          Funcionario f = funcionarioRepository.save(funcionario);
          return converter.converterFuncionarioToFuncionarioResponse(f);
        } catch (Exception e) {
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
      Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

      if (funcionarioOptional.isPresent()) {
        try {
          funcionarioRepository.delete(funcionarioOptional.get());
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
