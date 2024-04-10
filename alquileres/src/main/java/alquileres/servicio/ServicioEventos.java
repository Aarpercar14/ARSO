package alquileres.servicio;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import alquileres.eventos.EventoAlquilerConcluido;
import alquileres.eventos.EventoBicicletaAlquilada;

public class ServicioEventos implements IServicioEventos {
	

	@Override
	public void publicarEventoAlquilerConcluido(String idBici) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqps://ppvihfpt:BDGv28w-UU11pgRbRFJTStZyrY0L77Yd@stingray.rmq.cloudamqp.com/ppvihfpt");

		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		final String exchangeName = "citybike";
		boolean durable = true;
		channel.exchangeDeclare(exchangeName, "topic", durable);
		
		final String queueName = "citybike-estaciones";
		channel.queueDeclare(queueName, durable, false, false, null);
		
		String routingKey = "citybike.alquileres";
		channel.queueBind(queueName, exchangeName, routingKey+".*");
		
		EventoAlquilerConcluido evento = new EventoAlquilerConcluido(idBici, LocalDateTime.now());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonEvento = mapper.writeValueAsString(evento);
		
		channel.basicPublish(exchangeName, routingKey+"alquiler-concluido", new AMQP.BasicProperties.Builder()
					.build(), jsonEvento.getBytes());
		
		channel.close();
		connection.close();
	}

	@Override
	public void publicarEventoBicicletaAlquilada(String idBici) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqps://ppvihfpt:BDGv28w-UU11pgRbRFJTStZyrY0L77Yd@stingray.rmq.cloudamqp.com/ppvihfpt");

		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		final String exchangeName = "citybike";
		boolean durable = true;
		channel.exchangeDeclare(exchangeName, "topic", durable);
		
		final String queueName = "citybike-estaciones";
		channel.queueDeclare(queueName, durable, false, false, null);
		
		String routingKey = "citybike.alquileres";
		channel.queueBind(queueName, exchangeName, routingKey+".*");
		
		EventoBicicletaAlquilada evento = new EventoBicicletaAlquilada(idBici, LocalDateTime.now());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonEvento = mapper.writeValueAsString(evento);
		
		channel.basicPublish(exchangeName, routingKey+"bicicleta-alquilada", new AMQP.BasicProperties.Builder()
					.build(), jsonEvento.getBytes());
		
		channel.close();
		connection.close();
	}

	@Override
	public void suscribirEventoBicicletaDesactivada() {
		// TODO Auto-generated method stub

	}

}
