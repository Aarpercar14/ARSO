package estaciones.eventos.bus;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import estaciones.evento.config.RabbitMQConfig;
import estaciones.servicio.IServicioEventos;
import estaciones.eventos.Evento;


@Component
public class EventListener {
	
	@Autowired
	private IServicioEventos servEventos;
	
	@RabbitListener(queues = RabbitMQConfig.REC_QUEUE)
	public void handleEvent(Message mensaje, @Header("amqp_receivedRoutingKey") String routingKey) {
		ObjectMapper mapper = new ObjectMapper();
		String body = new String(mensaje.getBody());
		System.out.println(body);
		try {
			Evento evento = mapper.readValue(body, Evento.class);
			
			
			
			String funcionalidad = routingKey.replaceFirst("citybike\\.alquileres\\.", "");
			System.out.println(funcionalidad);
			switch (funcionalidad) {
			case "bicicleta-alquilada":
				servEventos.suscribirEventoBicicletaAlquilada(evento.getIdBicicleta(), evento.getFecha());
				break;
			case "alquiler-concluido":
				servEventos.suscribirEventoAlquilerConcluido(evento.getIdBicicleta(), evento.getFecha());
				break;
			default:
				break;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}
}
