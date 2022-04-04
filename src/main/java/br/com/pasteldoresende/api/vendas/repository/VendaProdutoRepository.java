package br.com.pasteldoresende.api.vendas.repository;

import br.com.pasteldoresende.api.vendas.model.VendaProduto;
import org.springframework.data.repository.CrudRepository;

public interface VendaProdutoRepository extends CrudRepository<VendaProduto, Long> {
}
