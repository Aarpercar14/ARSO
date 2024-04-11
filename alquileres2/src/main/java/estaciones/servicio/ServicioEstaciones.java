package estaciones.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.EstacionDTOUsuario;
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
	public ServicioEstaciones(RepositorioEstaciones repositorioEst, RepositorioBicicletas repositorioBicicletas, IServicioEventos servEventos) {
		this.repositorioEst = repositorioEst;
		this.repositorioBicicletas = repositorioBicicletas;
		this.servEventos = servEventos;
	}

	@Override
	public boolean consultaHueco(String id) {
		Estacionamiento estacion=repositorioEst.findById(id).get();
		return estacion.getNumPuestos()>0;
	}

	@Override
	public boolean peticionAparcarBicicleta(String id) {
		return this.consultaHueco(id);
	}

	@Override
	public String altaEstacion(String nombre, int puestos, String direccion, int cordX, int cordY) {
		Estacionamiento estacion = new Estacionamiento(nombre, puestos, direccion, cordX, cordY);
		String id = repositorioEst.save(estacion).getId();
		return id;
	}

	@Override
	public String altaBici(String modelo, String estacion) {
		String id = UUID.randomUUID().toString();
		Estacionamiento est=repositorioEst.findById(estacion).get();
		Bicicleta bici = new Bicicleta(id, modelo);
		repositorioBicicletas.save(bici);
		this.estacionarUnaBicileta(id, est.getId());
		return id;
	}

	@Override
	public void bajaBici(String idBici, String motivo) {
		Bicicleta bici = repositorioBicicletas.findById(idBici).get();
		this.retirarUnaBicleta(idBici);
		bici.cambioEstadoBici("desactivada");
		bici.setFechaBaja(LocalDateTime.now());
		bici.setMotivoBaja(motivo);
		repositorioBicicletas.save(bici);
		servEventos.publicarEventoBicicletaDesactivada(idBici);		
	}

	@Override
	public List<Bicicleta> getListadoPaginadoGestor(String idEstacion) {
		Estacionamiento est = repositorioEst.findById(idEstacion).get();
		return est.getBicicletas();
		
	}

	@Override
	public Page<EstacionDTOUsuario> getListadoPaginadoUsuario(Pageable pageable) {
		List<EstacionDTOUsuario> est=new ArrayList<EstacionDTOUsuario>();
		repositorioEst.findAll().forEach(estacion->{
			EstacionDTOUsuario estacionDto= this.parseToEstacionDTOUsuario(estacion);
			est.add(estacionDto);
		});
		int start;
		if(pageable.getOffset()>est.size()) start=1;
		else start= (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), est.size());
		List<EstacionDTOUsuario> estacionesP = est.subList(start, end);
		return new PageImpl<>(estacionesP, pageable, est.size());
	}

	@Override
	public String infoEstacion(String idEstacio) {
		return this.parseToEstacionDTOUsuario(repositorioEst.findById(idEstacio).get()).toString();
	}

	@Override
	public Page<Bicicleta> getListadoBicisDisponibles(String estacion, Pageable pageable) {
		List<Bicicleta> bicis=repositorioEst.findById(estacion).get().findDisponibles();
		int start;
		if(pageable.getOffset()>bicis.size()) start=1;
		else start= (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), bicis.size());
		List<Bicicleta> bicisP = bicis.subList(start, end);
		return new PageImpl<>(bicisP, pageable, bicis.size());
	}

	@Override
	public void estacionarUnaBicileta(String idBici, String idEstacion) {
		try {
			Estacionamiento estacion = repositorioEst.findById(idEstacion).get();
			Bicicleta bici = repositorioBicicletas.findById(idBici).get();
			if(estacion.getNumPuestos()>0) {
				estacion.estacionarBici(bici);
			} else System.out.println("No hay sitios disponibles");
			repositorioEst.save(estacion);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}

	private boolean retirarUnaBicleta(String idBici) {
		for(Estacionamiento e:repositorioEst.findAll()) {
			for(Bicicleta b:e.getBicicletas()) {
				if(b.getId().equals(idBici)) {
					e.sacarBici(idBici);
					repositorioEst.save(e);
					return true;
				}
			}
		}
		return false;
	}	
	
	private EstacionDTOUsuario parseToEstacionDTOUsuario(Estacionamiento estacion) {
		return new EstacionDTOUsuario(estacion.getNombre(), estacion.getNumPuestos()>0, estacion.getPostal(), estacion.getCordY(), estacion.getCordX(), estacion.getFechaAlta());
	}
}
