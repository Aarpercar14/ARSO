package estaciones.servicio;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estaciones.eventos.Evento;
import estaciones.eventos.bus.PublicadorEventos;
import estaciones.modelo.Bicicleta;
import estaciones.repositorio.RepositorioBicicletas;

@Service
public class ServicioEventos implements IServicioEventos {
	
	@Autowired
	private PublicadorEventos publicador;
	
	@Autowired
	private RepositorioBicicletas repBicis;

	@Override
	public void publicarEventoBicicletaDesactivada(String idBici) {
		Evento evento = new Evento(idBici, LocalDateTime.now());
		publicador.sendMessage(evento, ".bicicleta-desactivada");
	}

	@Override
	public void suscribirEventoAlquilerConcluido(String idBici, LocalDateTime fecha) {
		Bicicleta bici = repBicis.findById(idBici).get();
		bici.cambioEstadoBici("disponible");
		repBicis.save(bici);
	}

	@Override
	public void suscribirEventoBicicletaAlquilada(String idBici, LocalDateTime fecha) {
		Bicicleta bici = repBicis.findById(idBici).get();
		bici.cambioEstadoBici("disponible");
		repBicis.save(bici);
	}
}
