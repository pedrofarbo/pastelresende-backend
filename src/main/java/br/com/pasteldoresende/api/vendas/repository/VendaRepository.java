package br.com.pasteldoresende.api.vendas.repository;

import br.com.pasteldoresende.api.vendas.model.Venda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Long> {
  @Query("SELECT DISTINCT e FROM Venda e JOIN FETCH e.feira f JOIN FETCH e.produtos ps LEFT JOIN FETCH ps.produto p WHERE f.id = :id ORDER BY p.nome")
  Iterable<Venda> findAllByFeiraId(Long id);
}
