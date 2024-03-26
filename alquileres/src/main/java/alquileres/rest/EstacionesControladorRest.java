package alquileres.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alquileres.modelo.Bicicleta;
import alquileres.modelo.Estacionamiento;
import alquileres.servicio.IServicioEstaciones;

@RestController
@RequestMapping("/estaciones")
public class EstacionesControladorRest {

	private IServicioEstaciones servicio;

	@Autowired
	private PagedResourcesAssembler<Estacionamiento> pagedResourcesAssemblerEst;
	private PagedResourcesAssembler<Bicicleta> pagedResourcesAssemblerBici;

	@Autowired
	public EstacionesControladorRest(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}

	@PostMapping("/Nueva/Estacion/{idEstacion}")
	public ResponseEntity<String> createEstacionamiento(@PathVariable String idEstacion, @PathVariable String id,
			@RequestParam int puestos, @RequestParam String direccion, @RequestParam int cordX,
			@RequestParam int cordY) {
		String iden = servicio.altaEstacion(id, puestos, direccion, cordX, cordY);
		return new ResponseEntity<>(iden, HttpStatus.OK);
	}

	@PostMapping("/Nueva/Bicicleta/{idEstacion}")
	public ResponseEntity<String> createBicicleta(@RequestParam String modelo, @PathVariable String idEstacion) {
		String iden = servicio.altaBici(modelo, idEstacion);
		return new ResponseEntity<>(iden, HttpStatus.OK);
	}

	@GetMapping("/Listado/Bicis/{idEstacion}")
	public PagedModel<EntityModel<Bicicleta>> getListadoPaginadoGestor(@PathVariable String idEstacion,
			@RequestParam int page, @RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		Page<Bicicleta> resultado = servicio.getListadoPaginadoGestor(idEstacion, paginacion);
		return this.pagedResourcesAssemblerBici.toModel(resultado);
	}

	@GetMapping("/Listado/Estaciones/{idEstacion}")
	public PagedModel<EntityModel<Estacionamiento>> getListadoPaginadoUsuario(@RequestParam int page,
			@RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		Page<Estacionamiento> resultado = servicio.getListadoPaginadoUsuario(paginacion);
		return this.pagedResourcesAssemblerEst.toModel(resultado);
	}

	@GetMapping("/Listado/BicisDisponibles/{idEstacion}")
	public PagedModel<EntityModel<Bicicleta>> getListadoBicisBisponibles(@PathVariable String idEstacion,
			@RequestParam int page, @RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		Page<Bicicleta> resultado = servicio.getListadoBicisDisponibles(idEstacion, paginacion);
		return this.pagedResourcesAssemblerBici.toModel(resultado);
	}

	@PostMapping("/{idEstacion}/EstacionarBici/{idBici}")
	public ResponseEntity<String> estacionarBici(@PathVariable String idEstacion, @PathVariable String idBici) {
		servicio.estacionarUnaBicileta(idBici, idEstacion);
		return new ResponseEntity<>("Bici estacionada", HttpStatus.OK);
	}

}
