package br.com.pasteldoresende.api.seguranca.service;

import br.com.pasteldoresende.api.seguranca.model.Usuario;
import br.com.pasteldoresende.api.seguranca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

  private UserRepository userRepository;

  public Optional<Usuario> findById(Long id) {
    return userRepository.findById(id);
  }

  public Usuario findByTokenPassword(String token) {
    return userRepository.findByResetToken(token);
  }

  public Usuario findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public void save(Usuario usuario) {
    userRepository.save(usuario);
  }

  public boolean setAtivo(Long id) {

    try {
      Optional<Usuario> usuario = userRepository.findById(id);

      if (usuario.isPresent()) {
        if (usuario.get().getAtivo()) {
          usuario.get().setAtivo(false);
        } else {
          usuario.get().setAtivo(true);
        }

        userRepository.save(usuario.get());
      }

    } catch (Exception e) {
      return false;
    }

    return true;
  }

  public void update(Usuario usuario) {
    Optional<Usuario> entity = userRepository.findById(usuario.getId());

    if (entity.isPresent()) {
      entity.get().setEmail(usuario.getEmail());
      entity.get().setSenha(usuario.getSenha());

      userRepository.save(entity.get());
    }
  }

  public List<Usuario> findAllUsuarios() {
    return userRepository.findAllUsuarios();
  }

  public boolean isUsuarioLoginUnique(Long id, String username) {
    Usuario usuario = findByEmail(username);
    return (usuario == null || ((id != null) && (usuario.getId() == id)));
  }
}
