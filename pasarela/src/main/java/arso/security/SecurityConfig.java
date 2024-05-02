package arso.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecuritySuccessHandler successHandler;
	@Autowired
	private JwtRequestFilter authenticationRequestFilter;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().disable().csrf().disable().authorizeRequests()
					.antMatchers("/estaciones/**").permitAll()
					.antMatchers("/estaciones/estacion/**").hasAuthority("gestor")
					.antMatchers("/estaciones/bicicleta/**").hasAuthority("gestor")
					.antMatchers("/estaciones/baja/**").hasAuthority("gestor")
					.antMatchers("/estaciones/listado/bicis/**").hasAuthority("gestor")
					.antMatchers("/estaciones/listado/estaciones/**").hasAuthority("usuario")
					.antMatchers("/estaciones/listado/bicisDisponibles/**").hasAuthority("usuario")
					.antMatchers("/api/usuarios/codigo/**").hasAuthority("gestor")
					.antMatchers("/api/usuarios/alta/**").hasAuthority("usuario")
					.antMatchers("/api/usuarios/baja/**").hasAuthority("gestor")
					.antMatchers("/api/usuarios/listadoUsuario/**").hasAuthority("gestor")
					.antMatchers("/api/usuarios/verificarOauth/**").hasAuthority("usuario")
					.antMatchers("/pasarela/auth/oauth2/**").authenticated()
					.antMatchers("/alquileres/**").permitAll()
					.antMatchers("/usuarios/**").permitAll()
					.and()
					.oauth2Login()
					.successHandler(this.successHandler)
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(this.authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}