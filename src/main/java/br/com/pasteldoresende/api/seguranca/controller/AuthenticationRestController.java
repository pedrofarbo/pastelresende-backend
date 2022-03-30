package br.com.pasteldoresende.api.seguranca.controller;

import br.com.pasteldoresende.api.seguranca.JwtAuthenticationRequest;
import br.com.pasteldoresende.api.seguranca.JwtTokenUtil;
import br.com.pasteldoresende.api.seguranca.JwtUser;
import br.com.pasteldoresende.api.seguranca.Mail;
import br.com.pasteldoresende.api.seguranca.model.Usuario;
import br.com.pasteldoresende.api.seguranca.service.EmailService;
import br.com.pasteldoresende.api.seguranca.service.JwtAuthenticationResponse;
import br.com.pasteldoresende.api.seguranca.service.JwtUserDetailsServiceImpl;
import br.com.pasteldoresende.api.seguranca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class AuthenticationRestController {

  @Value("${jwt.header}")
  private String tokenHeader;

  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;

  private final UsuarioService usuarioService;

  private final PasswordEncoder passwordEncoder;

  private final EmailService emailService;

  public AuthenticationRestController(JwtTokenUtil jwtTokenUtil, JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl, UsuarioService usuarioService, EmailService emailService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.jwtUserDetailsServiceImpl = jwtUserDetailsServiceImpl;
    this.usuarioService = usuarioService;
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping(value = "/api/v1/usuario/auth/create", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
    usuario.setAtivo(true);
    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

    usuarioService.save(usuario);

    return new ResponseEntity<>(usuario, HttpStatus.OK);
  }

  @GetMapping(value = "/api/v1/usuario/{username:.+}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<JwtUser> getOne(@PathVariable("username") String username) {
    final JwtUser user = (JwtUser) jwtUserDetailsServiceImpl.loadUserByUsername(username);
    return new ResponseEntity<JwtUser>(user, HttpStatus.OK);
  }

  @PostMapping(value = "/api/v1/usuario/auth/token")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, String device) throws AuthenticationException {
    // Perform the security
    final Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationRequest.getEmail(),
        authenticationRequest.getSenha()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Reload password post-security so we can generate token
    final JwtUser userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(authenticationRequest.getEmail());
    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getName(), token, jwtTokenUtil.getExpirationDateFromToken(token)));
  }

  @GetMapping(value = "/api/v1/usuario/token/{tokenPassword:.+}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<?> getUsernameByToken(@PathVariable("tokenPassword") String tokenPassword) {

    Usuario u = usuarioService.findByTokenPassword(tokenPassword);

    if (u == null) {
      return new ResponseEntity<>("Token Inválido ou Expirado", HttpStatus.NOT_ACCEPTABLE);
    }

    return new ResponseEntity<>(u, HttpStatus.OK);
  }

  @PutMapping(value = "/api/v1/usuario/auth/reset/{username:.+}", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Object> resetSenhaByUsername(@PathVariable("username") String username, @RequestBody Usuario usuario) {

    Usuario u = usuarioService.findByEmail(username);
    if (u == null) {
      return new ResponseEntity<>("Nenhum usuario encontrada com o nome de usuário " + username, HttpStatus.NOT_FOUND);
    }

    try {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, 1);

      u.setResetToken(UUID.randomUUID().toString());
      u.setTokenExp(cal.getTime());

      Mail mail = new Mail();
      mail.setFrom("naoresponda@pasteldoresende.com.br");
      mail.setTo(username);
      mail.setSubject("App - Pastel do Resende - Troca de Senha | " + u.getNome());

      Map<String, Object> model = new HashMap<>();
      model.put("token", u.getResetToken());
      model.put("usuario", u);
      model.put("signature", "Pastel do Resende");
      mail.setModel(model);

      emailService.sendEmail(mail);

      usuarioService.update(u);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Nenhum usuario encontrada com o nome de usuário " + username, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    return new ResponseEntity<>(u, HttpStatus.OK);
  }

  @PutMapping(value = "/api/v1/usuario/change/password/{username:.+}", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Object> setSenhaByUsername(@PathVariable("username") String username, @RequestBody Usuario usuario) {

    Usuario u = usuarioService.findByEmail(username);

    Date date = new Date();

    if (u == null) {
      return new ResponseEntity<>("Nenhuma usuario encontrada com ID " + username, HttpStatus.NOT_FOUND);
    } else if (u.getResetToken() == null && date.compareTo(u.getTokenExp()) > 0) {
      return new ResponseEntity<>("Token Inválido ou Expirado", HttpStatus.NOT_ACCEPTABLE);
    }

    try {
      u.setSenha(passwordEncoder.encode(usuario.getSenha()));
      u.setUltimoReset(new Date());
      u.setResetToken(null);
      u.setTokenExp(null);
      usuarioService.update(u);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Nenhuma usuario encontrada com ID " + username, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    return new ResponseEntity<>(usuario, HttpStatus.OK);
  }

  @GetMapping(value = "/api/v1/usuario/refresh")
  public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
    String token = request.getHeader(tokenHeader);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = jwtUserDetailsServiceImpl.loadUserByUsername(username);

    if (jwtTokenUtil.canTokenBeRefreshed(token, user.getUltimoReset())) {
      String refreshedToken = jwtTokenUtil.refreshToken(token);
      return ResponseEntity.ok(new JwtAuthenticationResponse(user.getName(), refreshedToken, jwtTokenUtil.getExpirationDateFromToken(token)));
    } else {
      return ResponseEntity.badRequest().body(null);
    }
  }

}
