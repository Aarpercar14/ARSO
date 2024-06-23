package estaciones.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.BicicletaDTO;
import estaciones.modelo.EstacionDTOUsuario;
import estaciones.modelo.Estacionamiento;
import estaciones.modelo.NuevaEstacionDTO;
import estaciones.servicio.IServicioEstaciones;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/estaciones")
@Tag(name = "Estaciones", description = "Aplicación de estaciones")
public class EstacionesControladorRest {

	private IServicioEstaciones servicio;
	
	
	@Autowired
	private PagedResourcesAssembler<Estacionamiento> pagedResourcesAssemblerEst;
	@Autowired
	private PagedResourcesAssembler<EstacionDTOUsuario> pagedResourcesAssemblerEstDTO;
	@Autowired
	private PagedResourcesAssembler<Bicicleta> pagedResourcesAssemblerBici;
	@Autowired
	private PagedResourcesAssembler<BicicletaDTO> pagedResourcesAssemblerBiciDto;

	@Autowired
	public EstacionesControladorRest(IServicioEstaciones servicio) {
		this.servicio = servicio;
	}

	@Operation(summary = "Dar de alta una estacion", description = "Crea una estacion y la da de alta en la base de datos")
	@PostMapping("/alta")
	public ResponseEntity<String> createEstacionamiento(@Valid @RequestBody NuevaEstacionDTO estacion) {
		String iden = servicio.altaEstacion(estacion.getNombre(), estacion.getPuestos(), estacion.getPostal(),
				estacion.getCordY(), estacion.getCordX());
		return new ResponseEntity<>(iden, HttpStatus.OK);
	}

	@Operation(summary = "Dar de alta una bicicleta", description = "Crea una bicicleta y la da de alta en la base de datos y se aparca en la estacion especificada")
	@PostMapping("/bicicleta/{idEstacion}")
	public ResponseEntity<String> createBicicleta(@RequestParam String modelo, @PathVariable String idEstacion) {
		String iden = servicio.altaBici(modelo, idEstacion);
		return new ResponseEntity<>(iden, HttpStatus.OK);
	}

	@Operation(summary = "Dar de baja estacion", description = "Da de baja una bicicleta, la retira de la estacion en la que este y cambia su estado")
	@PostMapping("baja/{idBici}")
	public ResponseEntity<String> darDeBajaBicicleta(@PathVariable String idBici, @RequestParam String motivo) {
		this.servicio.bajaBici(idBici, motivo);
		return new ResponseEntity<>("Bicicleta dada de baja correctamente", HttpStatus.OK);
	}

	@Operation(summary = "Listado de bicicletas", description = "Muestra un listado paginado de todas las bicicletas guardadas en la base de datos")
	@GetMapping("/bicis/{idEstacion}")
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
	@GetMapping("/listaEstaciones")
	public PagedModel<EntityModel<Estacionamiento>> getListadoPaginadoUsuario(@RequestParam int page,
			@RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		List<Estacionamiento> resultado = servicio.getListadoPaginadoUsuario();
		int start;
		if (paginacion.getOffset() > resultado.size())
			start = 1;
		else
			start = (int) paginacion.getOffset();
		int end = Math.min((start + paginacion.getPageSize()), resultado.size());
		List<Estacionamiento> estacionesP = resultado.subList(start, end);
		return this.pagedResourcesAssemblerEst.toModel(new PageImpl<>(estacionesP, paginacion, estacionesP.size()));
	}

	@Operation(summary = "Muestra bicicletas disponibles", description = "Muestra a los usuarios las bicicletas disponibles de una estacion")
	@GetMapping("/bicisDisponibles/{idEstacion}")
	public PagedModel<EntityModel<BicicletaDTO>> getListadoBicisBisponibles(@PathVariable String idEstacion,
			@RequestParam int page, @RequestParam int size) {
		Pageable paginacion = PageRequest.of(page, size, Sort.by("nombre").ascending());
		List<Bicicleta> resultado = servicio.getListadoBicisDisponibles(idEstacion);
		List<BicicletaDTO> dtos = new ArrayList<BicicletaDTO>();
		for (Bicicleta bici : resultado) {
			dtos.add(parseToBicicletaDTO(bici));
		}
		int start;
		if (paginacion.getOffset() > dtos.size())
			start = 1;
		else
			start = (int) paginacion.getOffset();
		int end = Math.min((start + paginacion.getPageSize()), dtos.size());
		List<BicicletaDTO> bicisP = dtos.subList(start, end);
		return this.pagedResourcesAssemblerBiciDto.toModel(new PageImpl<>(bicisP, paginacion, dtos.size()));
	}

	@Operation(summary = "Aparca una bicicleta", description = "Aparca una bicicleta en una estacion si esta tiene algun hueco disponible")
	@PostMapping("/aparcamientoBici/{idEstacion}/{idBici}")
	public ResponseEntity<String> estacionarBici(@PathVariable String idEstacion, @PathVariable String idBici) {
		servicio.estacionarUnaBicileta(idBici, idEstacion);
		return new ResponseEntity<>("\"Bici estacionada\"", HttpStatus.OK);
	}

	@Operation(summary = "Envía info estacion", description = "Envía la info de una estacion")
	@GetMapping(value = "/infoEstacion/{idEstacion}", produces = "application/json")
	public ResponseEntity<String> infoEstacion(@PathVariable String idEstacion) {
		String info = servicio.infoEstacion(idEstacion).toString();
		info = info.replaceFirst("Estacionamiento ", "\"Estacionamiento:");
		return ResponseEntity.ok(info + "\"");
	}
	@DeleteMapping(value = "")
	public ResponseEntity<String> deleteEstacion(@PathVariable String idEstacion) {
		servicio.borrarEstacion(idEstacion);
		return ResponseEntity.ok("borrado");
	}


	private EstacionDTOUsuario parseToEstacionDTOUsuario(Estacionamiento estacion) {
		return new EstacionDTOUsuario(estacion.getId(), estacion.getNombre(), estacion.getNumPuestos() > 0, estacion.getPostal(),
				estacion.getCordY(), estacion.getCordX(), estacion.getFechaAlta());
	}

	private BicicletaDTO parseToBicicletaDTO(Bicicleta bici) {
		return new BicicletaDTO(bici.getId(), bici.getModelo(), bici.getEstado());
	}

}
