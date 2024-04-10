package alquileres.eventos.bus;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import alquileres.eventos.Evento;
import alquileres.servicio.IServicioEventos;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import servicio.FactoriaServicios;

public class Consumidor {
	
	public static final IServicioEventos servEventos = FactoriaServicios.getServicio(IServicioEventos.class);
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqps://ppvihfpt:BDGv28w-UU11pgRbRFJTStZyrY0L77Yd@stingray.rmq.cloudamqp.com/ppvihfpt");
		
		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();
		
		final String exchangeName = "citybike";
		final String queueName = "citybike-alquileres";
		final String bindingKey = "citybike-estaciones.*";
		
		boolean durable = true;
		boolean exclusive = false;
		boolean autodelete = false;
		Map<String, Object> properties = null;
		channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);
		
		channel.queueBind(queueName, exchangeName, bindingKey);
		
		/** Configuracion del consumidor **/
		
		boolean autoAck = false;
		String cola = "citybike-alquileres";
		String etiquetaConsumidor = "citybike.alquileres";
		
		// Consumidor push
		
		channel.basicConsume(cola, autoAck, etiquetaConsumidor,
				new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
							byte[] body) throws IOException {
						String routingKey = envelope.getRoutingKey();
						String contentType = properties.getContentType();
						long deliveryTag = envelope.getDeliveryTag();
						
						String contenido = new String(body);
						
						ObjectMapper mapper = new ObjectMapper();
						
						Evento evento = mapper.readValue(contenido, Evento.class);
						String funcionalidad = routingKey.replaceFirst("citybike\\.estaciones\\.", "");
						switch (funcionalidad) {
						case "bicicleta-alquilada":
							servEventos.suscribirEventoBicicletaDesactivada(evento.getIdBicicleta());
							break;
						default:
							break;
						}
						
						channel.basicAck(deliveryTag, false);
					}
		});
		
		channel.close();
		connection.close();
		
	}

}
