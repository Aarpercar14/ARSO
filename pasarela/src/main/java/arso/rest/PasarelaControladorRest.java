package arso.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pasarela")
public class PasarelaControladorRest {
	
	@GetMapping("auth/oauth2")
	public ResponseEntity<Void> autorizacion(String oauth){ //Con esa string en success handler se envia a servicio usuarios la peticion de verificacion
		 return ResponseEntity.ok().build();
	}
	
	

}
