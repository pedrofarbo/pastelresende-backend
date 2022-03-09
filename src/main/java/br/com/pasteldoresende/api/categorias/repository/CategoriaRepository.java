package br.com.pasteldoresende.api.categorias.repository;

import br.com.pasteldoresende.api.categorias.model.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
  Categoria findByNome(String nome);
}
