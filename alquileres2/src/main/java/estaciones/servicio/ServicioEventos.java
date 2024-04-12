package estaciones.servicio;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import estaciones.eventos.Evento;
import estaciones.eventos.bus.PublicadorEventos;
import estaciones.modelo.Bicicleta;
import estaciones.modelo.Estacionamiento;
import estaciones.repositorio.RepositorioBicicletas;
import estaciones.repositorio.RepositorioEstaciones;
import repositorio.RepositorioException;

@Service
public class ServicioEventos implements IServicioEventos {
	
	@Autowired
	private PublicadorEventos publicador;
	
	@Autowired
	private RepositorioBicicletas repBicis;
	
	@Autowired
	private RepositorioEstaciones repEstaciones;
	

	@Override
	public void publicarEventoBicicletaDesactivada(String idBici) {
		
		Evento evento = new Evento(idBici, LocalDateTime.now(), "");
		
		publicador.sendMessage(evento, ".bicicleta-desactivada");
	}

	@Override
	public void suscribirEventoBicicletaAlquilada(String idBici, LocalDateTime fecha) {
		Bicicleta bici = repBicis.findById(idBici).get();
		bici.cambioEstadoBici("no disponible");
		retirarBici(idBici);
		repBicis.save(bici);
	}
	
	@Override
	public void suscribirEventoAlquilerConcluido(String idBici, String idEstacion, LocalDateTime fecha) {
		Bicicleta bici = repBicis.findById(idBici).get();
		bici.cambioEstadoBici("disponible");
		repBicis.save(bici);
//		estacionarBici(idBici, idEstacion);
	}
	
	private boolean retirarBici(String idBici) {
		for(Estacionamiento e:repEstaciones.findAll()) {
			for(Bicicleta b:e.getBicicletas()) {
				if(b.getId().equals(idBici)) {
					e.sacarBici(idBici);
					repEstaciones.save(e);
					return true;
				}
			}
		}
		return false;
	}
	
	private void estacionarBici(String idBici, String idEstacion) {
		try {
			Estacionamiento estacion = repEstaciones.findById(idEstacion).get();
			Bicicleta bici = repBicis.findById(idBici).get();
			if(estacion.getNumPuestos()>0) {
				estacion.estacionarBici(bici);
			} else System.out.println("No hay sitios disponibles");
			repEstaciones.save(estacion);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}
}
