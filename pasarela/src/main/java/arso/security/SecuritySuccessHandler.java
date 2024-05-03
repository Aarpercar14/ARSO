package arso.security;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import arso.rest.PasarelaClientRest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	
	private Retrofit retrofit = new Retrofit.Builder().baseUrl("https://localhost:5058/api/")
			.addConverterFactory(JacksonConverterFactory.create()).build();
	private PasarelaClientRest pasarelaClient = retrofit.create(PasarelaClientRest.class);
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
		System.out.println(usuario.toString());
		try {
			String idGitHub = (String) usuario.getAttribute("login");
			String queriedClaims = pasarelaClient.getUserClaims(idGitHub).execute().body();
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> claims = mapper.readValue(queriedClaims, new TypeReference<Map<String, Object>>() {});
			if (claims != null) {
				Date caducidad = Date.from(Instant.now().plusSeconds(3600));
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
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

	private Map<String, Object> fetchUserInfo(DefaultOAuth2User usuario) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", usuario.getAttribute("login"));
		claims.put("rol", "usuario");
		return claims;
	}
}