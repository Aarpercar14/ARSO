package alquileres.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alquileres.servicio.IServicioEstaciones;
import estaciones.modelo.Bicicleta;
import estaciones.modelo.EstacionDTOUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/estaciones")
@Tag(name = "Estaciones", description = "Aplicación de estaciones")
public class EstacionesControladorRest {

	private IServicioEstaciones servicio;

	@Autowired
	private PagedResourcesAssembler<EstacionDTOUsuario> pagedResourcesAssemblerEstDTO;
	@Autowired
	private PagedResourcesAssembler<Bicicleta> pagedResourcesAssemblerBici;

	@Autowired
	public EstacionesControladorRest(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}

	@Operation(summary = "Dar de alta una estacion", description = "Crea una estacion y la da de alta en la base de datos")
	@PostMapping("/estacion/{idEstacion}")
	@PreAuthorize("hasAuthority('gestor')")
	public ResponseEntity<String> createEstacionamiento(@PathVariable String idEstacion, @RequestParam int puestos,
			@RequestParam String direccion, @RequestParam int cordX, @RequestParam int cordY) {
		String iden = servicio.altaEstacion(idEstacion, puestos, direccion, cordX, cordY);
		return new ResponseEntity<>(iden, HttpStatus.OK);
	}

	@Operation(summary = "Dar de alta una bicicleta", description = "Crea una bicicleta y la da de alta en la base de datos y se aparca en la estacion especificada")
	@PostMapping("/bicicleta/{idEstacion}")
	@PreAuthorize("hasAuthority('gestor')")
	public ResponseEntity<String> createBicicleta(@RequestParam String modelo, @PathVariable String idEstacion) {
		String iden = servicio.altaBici(modelo, idEstacion);
		return new ResponseEntity<>(iden, HttpStatus.OK);
	}

	@Operation(summary = "Dar de baja estacion", description = "Da de baja una bicicleta, la retira de la estacion en la que este y cambia su estado")
	@PostMapping("baja/{idBici}")
	@PreAuthorize("hasAuthority('gestor')")
	public ResponseEntity<String> darDeBajaBicicleta(@PathVariable String idBici, @RequestParam String motivo) {
		this.servicio.bajaBici(idBici, motivo);
		return new ResponseEntity<>("Bicicleta dada de baja correctamente", HttpStatus.OK);
	}

	@Operation(summary = "Listado de bicicletas", description = "Muestra un listado paginado de todas las bicicletas guardadas en la base de datos")
	@GetMapping("/listado/bicis/{idEstacion}")
	@PreAuthorize("hasAuthority('gestor')")
	public PagedModel<EntityModel<Bicicleta>> getListadoPaginadoGestor(@PathVariable String idEstacion,
			@RequestParam int page, @RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		List<Bicicleta> bicis = servicio.getListadoPaginadoGestor(idEstacion);
		List<EntityModel<Bicicleta>> listaModelo = new ArrayList<EntityModel<Bicicleta>>();
		EntityModel<Bicicleta> model;
		for (Bicicleta b : bicis) {
			model = EntityModel.of(b);
			String url = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionesControladorRest.class)
					.darDeBajaBicicleta(b.getId(), "indefinido")).toUri().toString();
			model.add(Link.of(url, "Dar De Baja"));
			listaModelo.add(model);
		}

		int start;
		if (paginacion.getOffset() > listaModelo.size())
			start = 0;
		else
			start = (int) paginacion.getOffset();
		int end = Math.min((start + paginacion.getPageSize()), listaModelo.size());
		List<Bicicleta> bicisP = bicis.subList(start, end);

		return this.pagedResourcesAssemblerBici.toModel(new PageImpl<>(bicisP, paginacion, listaModelo.size()));
	}

	@Operation(summary = "Listado de estaciones", description = "Muestra un listado paginado de todas las estaciones en la base de datos una estacion y la da de alta en la base de datos")
	@GetMapping("/listado/estaciones")
	@PreAuthorize("hasAuthority('usuario')")
	public PagedModel<EntityModel<EstacionDTOUsuario>> getListadoPaginadoUsuario(@RequestParam int page,
			@RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		Page<EstacionDTOUsuario> resultado = servicio.getListadoPaginadoUsuario(paginacion);
		return this.pagedResourcesAssemblerEstDTO.toModel(resultado);
	}

	@Operation(summary = "Dar de alta estacion", description = "Crea una estacion y la da de alta en la base de datos")
	@GetMapping("/listado/bicisDisponibles/{idEstacion}")
	@PreAuthorize("hasAuthority('usuario')")
	public PagedModel<EntityModel<Bicicleta>> getListadoBicisBisponibles(@PathVariable String idEstacion,
			@RequestParam int page, @RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		Page<Bicicleta> resultado = servicio.getListadoBicisDisponibles(idEstacion, paginacion);
		return this.pagedResourcesAssemblerBici.toModel(resultado);
	}

	@Operation(summary = "Aparca una bicicleta", description = "Aparca una bicicleta en una estacion si esta tiene algun hueco disponible")
	@PostMapping("/aparcamientoBici/{idEstacion}/{idBici}")
	@PreAuthorize("hasAuthority('usuario')")
	public ResponseEntity<String> estacionarBici(@PathVariable String idEstacion, @PathVariable String idBici) {
		servicio.estacionarUnaBicileta(idBici, idEstacion);
		return new ResponseEntity<>("Bici estacionada", HttpStatus.OK);
	}
	
	@Operation(summary = "informacion de las estaciones",description="informacion npara el usuario sobre una estacion concreta")
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('usuario')")
	public EntityModel<EstacionDTOUsuario> infoEstacionUsuario(@PathVariable String id){
		EntityModel<EstacionDTOUsuario> model=EntityModel.of(servicio.infoEstacion(id));
		return model;
	}

}
