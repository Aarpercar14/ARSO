package alquileres.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alquileres.modelo.Estacionamiento;
import alquileres.servicio.IServicioEstaciones;

@RestController
@RequestMapping("/encuestas")
public class EstacionesControladorRest {
	
	private IServicioEstaciones servicio;

	@Autowired
	private PagedResourcesAssembler<Estacionamiento> pagedResourcesAssembler;
	
	@Autowired
	public EstacionesControladorRest(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}
	

}
