package br.com.pasteldoresende.api.seguranca;

import br.com.pasteldoresende.api.seguranca.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Usuario usuario) {
    	List<String> permissoes = new ArrayList<>();

    	return new JwtUser(
    			usuario.getEmail(),
    			usuario.getSenha(),
    			usuario.getNome(),
    			mapToGrantedAuthorities(permissoes),
    			usuario.getAtivo(),
				usuario.getUltimoReset());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
    	return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
