package arso.rest;

import java.io.IOException;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@RestController
@RequestMapping("/pasarela")
public class PasarelaControladorRest {

	private Retrofit retrofit = new Retrofit.Builder().baseUrl("http://usuarios:5058/api/")
			.addConverterFactory(JacksonConverterFactory.create()).build();
	private PasarelaClientRest pasarelaClient = retrofit.create(PasarelaClientRest.class);

	@GetMapping("auth/oauth/{oauth}")
	public ResponseEntity<String> autorizacion(@PathVariable String oauth) {
		try {
			ClaimsData claims = pasarelaClient.getUserClaims(oauth).execute().body();
			return new ResponseEntity<>(claims.getRol(), HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>("Error al comprobar token", HttpStatus.OK);

	}

}