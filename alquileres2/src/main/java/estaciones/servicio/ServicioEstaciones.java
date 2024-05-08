package estaciones.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacionamiento;
import estaciones.repositorio.RepositorioBicicletas;
import estaciones.repositorio.RepositorioEstaciones;
import repositorio.RepositorioException;

@Service
public class ServicioEstaciones implements IServicioEstaciones {

	private RepositorioEstaciones repositorioEst;
	private RepositorioBicicletas repositorioBicicletas;
	private IServicioEventos servEventos;

	@Autowired
	public ServicioEstaciones(RepositorioEstaciones repositorioEst, RepositorioBicicletas repositorioBicicletas,
			IServicioEventos servEventos) {
		this.repositorioEst = repositorioEst;
		this.repositorioBicicletas = repositorioBicicletas;
		this.servEventos = servEventos;
	}

	@Override
	public String altaEstacion(String nombre, int puestos, String direccion, double cordX, double cordY) {
		Estacionamiento estacion = new Estacionamiento(nombre, puestos, direccion, cordX, cordY);
		String id = repositorioEst.save(estacion).getId();
		return id;
	}

	@Override
	public String altaBici(String modelo, String estacion) {
		Optional<Estacionamiento> oestacion = repositorioEst.findById(estacion);
		if (!oestacion.isPresent())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Estacionamiento est = oestacion.get();
		Bicicleta bici = new Bicicleta(modelo);
		repositorioBicicletas.save(bici);
		this.estacionarUnaBicileta(bici.getId(), est.getId());
		return bici.getId();
	}

	@Override
	public void bajaBici(String idBici, String motivo) {
		Optional<Bicicleta> obici = repositorioBicicletas.findById(idBici);
		if (!obici.isPresent())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Bicicleta bici = obici.get();
		this.retirarUnaBicicleta(idBici);
		bici.cambioEstadoBici("desactivada");
		bici.setFechaBaja(LocalDateTime.now());
		bici.setMotivoBaja(motivo);
		repositorioBicicletas.save(bici);
		servEventos.publicarEventoBicicletaDesactivada(idBici);
	}

	@Override
	public List<Bicicleta> getListadoPaginadoGestor(String idEstacion) {
		Optional<Estacionamiento> oestacion = repositorioEst.findById(idEstacion);
		if (!oestacion.isPresent())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Estacionamiento est = oestacion.get();
		return est.getBicicletas();
	}

	@Override
	public List<Estacionamiento> getListadoPaginadoUsuario() {
		Iterator<Estacionamiento> est = repositorioEst.findAll().iterator();
		List<Estacionamiento> estaciones = new ArrayList<Estacionamiento>();
		while (est.hasNext()) {
			estaciones.add(est.next());
		}
		return estaciones;
	}

	@Override
	public Estacionamiento infoEstacion(String idEstacion) {
		if (repositorioEst.findById(idEstacion).get() == null) {
			throw new IllegalArgumentException("Id erroneo");
		}
		Optional<Estacionamiento> oestacion = repositorioEst.findById(idEstacion);
		if (!oestacion.isPresent())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Estacionamiento est = oestacion.get();
		return est;
	}

	@Override
	public List<Bicicleta> getListadoBicisDisponibles(String estacion) {
		Iterator<Bicicleta> bici = repositorioBicicletas.findAll().iterator();
		List<Bicicleta> bicis = new ArrayList<Bicicleta>();
		while (bici.hasNext()) {
			bicis.add(bici.next());
		}
		return bicis;
	}

	@Override
	public void estacionarUnaBicileta(String idBici, String idEstacion) {
		try {
			Optional<Estacionamiento> oestacion = repositorioEst.findById(idEstacion);
			if (!oestacion.isPresent())
				throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
			Estacionamiento estacion = oestacion.get();
			Optional<Bicicleta> obici = repositorioBicicletas.findById(idBici);
			if (!obici.isPresent())
				throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
			Bicicleta bici = obici.get();
			if (estacion.getNumPuestos() > 0) {
				estacion.estacionarBici(bici);
			} else
				System.out.println("No hay sitios disponibles");
			repositorioEst.save(estacion);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}

	private boolean retirarUnaBicicleta(String idBici) {
		for (Estacionamiento e : repositorioEst.findAll()) {
			for (Bicicleta b : e.getBicicletas()) {
				if (b.getId().equals(idBici)) {
					e.sacarBici(idBici);
					repositorioEst.save(e);
					return true;
				}
			}
		}
		return false;
	}
}
