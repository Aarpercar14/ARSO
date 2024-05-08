package arso.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import arso.rest.ClaimsData;
import arso.rest.PasarelaClientRest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	
	private Retrofit retrofit = new Retrofit.Builder().baseUrl("http://usuarios:5058/api/")
			.addConverterFactory(JacksonConverterFactory.create()).build();
	private PasarelaClientRest pasarelaClient = retrofit.create(PasarelaClientRest.class);
	
	
	@SuppressWarnings("unused")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) {
		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
 		try {
			String idGitHub = (String) usuario.getAttribute("login");
			ClaimsData claims = pasarelaClient.getUserClaims(idGitHub).execute().body();
			System.out.println(claims.getClaims());
			if (claims != null) {
				Date caducidad = Date.from(Instant.now().plusSeconds(3600));
				String token = Jwts.builder().setClaims(claims.getClaims()).signWith(SignatureAlgorithm.HS256, "secreto")
						.setExpiration(caducidad).compact();
				try {
					response.getWriter().append(token);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT ha expirado");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}