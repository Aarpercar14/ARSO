package pruebas;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class PKTN2 {
	public static void main(String[] args) {
		String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5byIsInJvbGVzIjoiYWx1bW5vIiwiZXhwIjoxNzA5NjUwMzU5fQ.RcRTpMcOUVeGB84Xdc6SYXVGJ2HC87vn2LRF0umKZgc";

		Claims claims = Jwts.parser()
                .setSigningKey("master_key")
                .parseClaimsJws(token)
                .getBody();
		Date caducidad = claims.getExpiration();
		if(caducidad.after(new Date())) System.out.println("no caduca");
		System.out.println(claims);
	}

}
