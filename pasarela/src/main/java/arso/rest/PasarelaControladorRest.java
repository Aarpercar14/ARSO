package arso.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pasarela")
public class PasarelaControladorRest {
	
	@GetMapping("auth/oauth/{oauth}")
	public ResponseEntity<String> autorizacion(@PathVariable String oauth){
		 return new ResponseEntity<>(HttpStatus.OK);
	}
}
