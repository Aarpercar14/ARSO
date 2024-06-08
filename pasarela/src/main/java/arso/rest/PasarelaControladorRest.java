package arso.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

	 @Value("${spring.security.oauth2.client.registration.github.clientId}")
	    private String clientId;

	    @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
	    private String clientSecret;

	    private RestTemplate restTemplate = new RestTemplate();

	    @GetMapping("/oauth/redirect")
	    public ResponseEntity<Map<String, Object>> oauthRedirect(@RequestParam String code) {
	        RestTemplate restTemplate = new RestTemplate();

	        HttpHeaders headers = new HttpHeaders();
	        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	        map.add("client_id", clientId);
	        map.add("client_secret", clientSecret);
	        map.add("code", code);

	        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

	        ResponseEntity<Map> response = restTemplate.exchange(
	            "https://github.com/login/oauth/access_token",
	            HttpMethod.POST,
	            entity,
	            Map.class
	        );

	        if (response.getStatusCode() != HttpStatus.OK) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        Map<String, String> tokenData = (Map<String, String>) response.getBody();
	        String accessToken = tokenData.get("access_token");
	        String tokenType = tokenData.get("token_type");

	        // Fetch user profile with the access token
	        HttpHeaders userHeaders = new HttpHeaders();
	        userHeaders.set("Authorization", tokenType + " " + accessToken);

	        HttpEntity<String> userEntity = new HttpEntity<>(userHeaders);
	        ResponseEntity<Map> userResponse = restTemplate.exchange(
	            "https://api.github.com/user",
	            HttpMethod.GET,
	            userEntity,
	            Map.class
	        );

	        if (userResponse.getStatusCode() != HttpStatus.OK) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        Map<String, Object> userData = userResponse.getBody();
	        Map<String, Object> responseData = new HashMap<>();
	        responseData.put("userData", userData);
	        responseData.put("token", accessToken);
	        responseData.put("tokenType", tokenType);

	        return ResponseEntity.ok(responseData);
	    }

}