package alquileres.servicio;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import alquileres.eventos.EventoAlquilerConcluido;
import alquileres.eventos.EventoBicicletaAlquilada;
import estaciones.eventos.EventoDesactivarBicicleta;
import estaciones.eventos.bus.PublicadorEventos;

public class ServicioEventos implements IServicioEventos {
	
	@Autowired
	private PublicadorEventos publicador;

	@Override
	public void publicarEventoAlquilerConcluido(String idBici) {
		EventoAlquilerConcluido evento = new EventoAlquilerConcluido(idBici, LocalDateTime.now());
		publicador.sendMessage(evento, ".alquiler-concluido");
	}

	@Override
	public void publicarEventoBicicletaAlquilada(String idBici) {
		EventoBicicletaAlquilada evento = new EventoBicicletaAlquilada(idBici, LocalDateTime.now());
		publicador.sendMessage(evento, ".bicicleta-alquilada");
	}

	@Override
	public void publicarEventoBicicletaDesactivada(String idBici) {
		EventoDesactivarBicicleta evento = new EventoDesactivarBicicleta(idBici, LocalDateTime.now());
		publicador.sendMessage(evento, ".bicicleta-desactivada");
	}

	@Override
	public void suscribirEventoAlquilerConcluido() {
		// TODO Auto-generated method stub

	}

	@Override
	public void suscribirEventoBicicletaAlquilada() {
		// TODO Auto-generated method stub

	}

	@Override
	public void suscribirEventoBicicletaDesactivada() {
		// TODO Auto-generated method stub

	}

}
