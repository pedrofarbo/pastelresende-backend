package br.com.pasteldoresende.api.seguranca.service;

import br.com.pasteldoresende.api.seguranca.JwtUser;
import br.com.pasteldoresende.api.seguranca.JwtUserFactory;
import br.com.pasteldoresende.api.seguranca.model.Usuario;
import br.com.pasteldoresende.api.seguranca.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public JwtUser loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("Nenhum usuario encontrado '%s'.", email));
    } else {
      return JwtUserFactory.create(
        user);
    }
  }
}
