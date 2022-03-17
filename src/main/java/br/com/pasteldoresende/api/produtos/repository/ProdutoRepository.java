package br.com.pasteldoresende.api.produtos.repository;

import br.com.pasteldoresende.api.feiras.model.Feira;
import br.com.pasteldoresende.api.produtos.model.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {
  Produto findByFeiraAndNome(Feira feira, String nome);
}
