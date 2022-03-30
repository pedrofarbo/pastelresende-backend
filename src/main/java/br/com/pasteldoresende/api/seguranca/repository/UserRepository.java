package br.com.pasteldoresende.api.seguranca.repository;

import br.com.pasteldoresende.api.seguranca.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {
  Usuario findByEmail(String email);
  Usuario findByResetToken(String token);

  @Query("SELECT u FROM Usuario u ORDER BY u.nome ASC")
  List<Usuario> findAllUsuarios();
}
