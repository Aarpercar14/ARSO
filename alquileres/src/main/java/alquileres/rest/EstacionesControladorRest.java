package alquileres.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alquileres.modelo.Estacionamiento;
import alquileres.servicio.IServicioEstaciones;

@RestController
@RequestMapping("/estaciones")
public class EstacionesControladorRest {
	
	private IServicioEstaciones servicio;

	@Autowired
	private PagedResourcesAssembler<Estacionamiento> pagedResourcesAssembler;
	
	@Autowired
	public EstacionesControladorRest(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}
	@PostMapping("/Alta")
	public ResponseEntity<String> createEstacionamiento(@PathVariable String id,@PathVariable int puestos,@PathVariable String direccion,@PathVariable int cordX,@PathVariable int cordY){
		String iden=servicio.altaEstacion(id, puestos, direccion, cordX, cordY);
		return new ResponseEntity<>(iden , HttpStatus.OK);
	}
	

}
