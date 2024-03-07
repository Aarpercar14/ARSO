package pruebas;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class PruebasTKN {

	public static void main(String[] args) {
		Map<String, Object> claims = new HashMap<String, Object>();

		claims.put("sub", "yo");
		claims.put("roles", "a");
		Date caducidad = Date.from(Instant.now().plusSeconds(3600)); // 1 hora de validez
		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, "master_key")
				.setExpiration(caducidad).compact();

		System.out.println(token);
	}

}
