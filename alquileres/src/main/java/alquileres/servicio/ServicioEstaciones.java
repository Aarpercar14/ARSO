package alquileres.servicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import alquileres.modelo.Bicicleta;
import alquileres.modelo.Estacionamiento;
import alquileres.repositorio.RepositorioBicicletas;
import alquileres.repositorio.RepositorioEstaciones;
import repositorio.RepositorioException;

@Service
public class ServicioEstaciones implements IServicioEstaciones {

	private RepositorioEstaciones repositorioEst;
	private RepositorioBicicletas repositorioBicicletas;

	@Autowired
	public ServicioEstaciones(RepositorioEstaciones repositorioEst, RepositorioBicicletas repositorioBicicletas) {
		this.repositorioEst = repositorioEst;
		this.repositorioBicicletas = repositorioBicicletas;
	}

	@Override
	public boolean consultaHueco() {
		return true;
	}

	@Override
	public boolean peticionAparcarBicicleta() {
		return true;
	}

	@Override
	public String altaEstacion(String nombre, int puestos, String direccion, int cordX, int cordY) {
		Estacionamiento estacion = new Estacionamiento(nombre, puestos, direccion, cordX, cordY);
		String id = repositorioEst.save(estacion).getId();
		return id;
	}

	@Override
	public String altaDeUnaBici(String modelo, Estacionamiento estacion) {
		String id = UUID.randomUUID().toString();
		Bicicleta bici = new Bicicleta(id, modelo);
		repositorioBicicletas.save(bici);
		this.estacionarUnaBicileta(id, estacion.getId());
		return id;
	}

	@Override
	public void darDeBajaUnaBici(String idEstacion, String idBici, String motivo) {
		Bicicleta bici = repositorioBicicletas.findById(idBici).get();
		this.retirarUnaBicleta(idBici, idEstacion);
		bici.cambioEstadoBici("no disponible");
		bici.setFechaBaja(LocalDateTime.now());
		bici.setMotivoBaja(motivo);
		repositorioBicicletas.save(bici);
	}

	@Override
	public Page<Bicicleta> getListadoPaginadoGestor(String idEstacion, Pageable page) {
		Estacionamiento est = repositorioEst.findById(idEstacion).get();
		List<Bicicleta> bicis = est.getBicicletas();
		int start = (int) page.getOffset();
		int end = Math.min((start + page.getPageSize()), bicis.size());
		List<Bicicleta> bicisP = bicis.subList(start, end);
		return new PageImpl<>(bicisP, page, bicis.size());
	}

	@Override
	public Page<Estacionamiento> getListadoPaginadoUsuario(Pageable pageable) {
		return repositorioEst.findAll(pageable);
	}

	@Override
	public String infoEstacion(String idEstacio) {
		// TODO
		return "Esto deberia ser un dto?";
	}

	@Override
	public Page<Bicicleta> getListadoBicisDisponibles(String estacion, Pageable pageable) {
		List<Bicicleta> bicis=repositorioEst.findById(estacion).get().findDisponibles();
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), bicis.size());
		List<Bicicleta> bicisP = bicis.subList(start, end);
		return new PageImpl<>(bicisP, pageable, bicis.size());
	}

	@Override
	public void estacionarUnaBicileta(String idBici, String idEstacion) {
		try {
			Estacionamiento estacion = repositorioEst.findById(idEstacion).get();
			Bicicleta bici = repositorioBicicletas.findById(idBici).get();
			estacion.estacionarBici(bici);
			repositorioEst.save(estacion);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}

	private void retirarUnaBicleta(String idBici, String idEstacion) {
		Estacionamiento estacion = repositorioEst.findById(idEstacion).get();
		estacion.sacarBici(idBici);
		repositorioEst.save(estacion);
	}	
}
