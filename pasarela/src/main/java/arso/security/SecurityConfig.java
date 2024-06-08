package arso.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecuritySuccessHandler successHandler;
	@Autowired
	private JwtRequestFilter authenticationRequestFilter;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().httpBasic().disable().csrf().disable().authorizeRequests()
					.antMatchers("/alquileres/**").authenticated()
					.antMatchers("/estaciones/**").authenticated()
					.antMatchers("/api/usuarios/baja**").hasAuthority("gestor")
					.antMatchers("/api/usuarios/listadoUsuario**").hasAuthority("gestor")
					.antMatchers("/api/usuarios/**").permitAll()
					.antMatchers("/pasarela/**").permitAll()
                    .antMatchers("/api/oauth/token").permitAll()
					.and()
					.oauth2Login()
					.successHandler(this.successHandler)
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(this.authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}