package br.com.pasteldoresende.api.seguranca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class JwtUser implements UserDetails, Serializable {

  private static final long serialVersionUID = 8137831977727721227L;

  private final String username;
  private final String name;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;
  private final boolean enabled;
  private final Date ultimoReset;

  public JwtUser(
    String username,
    String password,
    String name,
    Collection<? extends GrantedAuthority> authorities,
    boolean enabled,
    Date ultimoReset
  ) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.authorities = authorities;
    this.enabled = enabled;
    this.ultimoReset = ultimoReset;
  }

  public Date getUltimoReset() {
    return ultimoReset;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public String getName() {
    return name;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
