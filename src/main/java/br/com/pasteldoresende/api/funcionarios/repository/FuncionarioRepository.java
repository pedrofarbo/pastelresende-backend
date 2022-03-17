package br.com.pasteldoresende.api.funcionarios.repository;

import br.com.pasteldoresende.api.funcionarios.model.Funcionario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
  Iterable<Funcionario> findAllByFreelaFalse();
  Iterable<Funcionario> findAllByFreelaTrue();
  Funcionario findByNome(String nome);
}
