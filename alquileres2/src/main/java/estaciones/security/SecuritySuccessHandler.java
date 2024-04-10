package estaciones.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
		Map<String, Object> claims = fetchUserInfo(usuario);
		if (claims != null) {
			Date caducidad = Date.from(Instant.now().plusSeconds(1000000000));
			String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, "secreto")
					.setExpiration(caducidad).compact();
			try {
				response.getWriter().append(token);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}

	private Map<String, Object> fetchUserInfo(DefaultOAuth2User usuario) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", usuario.getAttribute("login"));
		claims.put("rol", "gestor");
		return claims;
	}
}