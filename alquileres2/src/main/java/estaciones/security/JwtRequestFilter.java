package estaciones.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	public static final String HEADER_STRING = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

		Claims claim = null;
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.replace("Bearer ", "");
			claim = Jwts.parser().setSigningKey("secreto").parseClaimsJws(token).getBody();
		}
		if (claim != null) {
			ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(claim.get("rol").toString()));
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claim.getSubject(), null,
					authorities);
			// Establecemos la autenticación en el contexto de seguridad
			// Se interpreta como que el usuario ha superado la autenticación
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		try {
			chain.doFilter(request, response);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
}