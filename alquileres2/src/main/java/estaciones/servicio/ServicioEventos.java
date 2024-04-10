package estaciones.servicio;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import estaciones.eventos.EventoDesactivarBicicleta;
import estaciones.eventos.bus.PublicadorEventos;

public class ServicioEventos implements IServicioEventos {
	
	@Autowired
	private PublicadorEventos publicador;

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
}
