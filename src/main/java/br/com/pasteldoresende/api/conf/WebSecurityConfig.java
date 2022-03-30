package br.com.pasteldoresende.api.conf;

import br.com.pasteldoresende.api.seguranca.JwtAuthenticationEntryPoint;
import br.com.pasteldoresende.api.seguranca.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint unauthorizedHandler;
  private final UserDetailsService userDetailsService;

  public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, UserDetailsService userDetailsService) {
    this.unauthorizedHandler = unauthorizedHandler;
    this.userDetailsService = userDetailsService;
  }

  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
      .userDetailsService(this.userDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
    return new JwtAuthenticationTokenFilter();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    StrictHttpFirewall firewall = new StrictHttpFirewall();
    firewall.setAllowUrlEncodedSlash(true);
    return firewall;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
    web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      // we don't need CSRF because our token is invulnerable
      .csrf().disable()

      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

      // don't create session
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

      .authorizeRequests()
      .antMatchers("/api/v1/usuario/auth/**").permitAll()
      .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
      .anyRequest().authenticated();

    // Custom JWT based security filter
    httpSecurity
      .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    // disable page caching
    httpSecurity.headers().cacheControl();
  }
}
