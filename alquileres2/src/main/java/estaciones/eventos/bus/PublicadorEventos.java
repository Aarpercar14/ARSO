package estaciones.eventos.bus;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import estaciones.evento.config.RabbitMQConfig;

@Component
public class PublicadorEventos {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMessage(Object evento, String tipo) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SEND + tipo, evento);
	}
}
