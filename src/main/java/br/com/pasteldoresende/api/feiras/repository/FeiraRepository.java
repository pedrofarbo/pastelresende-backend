package br.com.pasteldoresende.api.feiras.repository;

import br.com.pasteldoresende.api.feiras.model.Feira;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeiraRepository extends CrudRepository<Feira, Long> {
  Feira findByNome(String nome);
}
