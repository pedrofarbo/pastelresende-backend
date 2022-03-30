package br.com.pasteldoresende.api.estoque.repository;

import br.com.pasteldoresende.api.estoque.model.Estoque;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueRepository extends CrudRepository<Estoque, Long> {
  @Query("SELECT DISTINCT e FROM Estoque e JOIN FETCH e.produto p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.feira f WHERE f.id = :id ORDER BY p.nome")
  Iterable<Estoque> findAllByFeiraId(Long id);

  @Query("SELECT DISTINCT e FROM Estoque e JOIN FETCH e.produto p LEFT JOIN FETCH p.categoria LEFT JOIN FETCH p.feira WHERE p.id = :id")
  Optional<Estoque> findByProdutoId(Long id);
}
