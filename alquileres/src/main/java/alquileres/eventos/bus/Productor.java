package alquileres.eventos.bus;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import alquileres.eventos.Evento;

public class Productor {
	private static Productor instance;
	private final ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	
	public Productor() throws Exception {
		this.factory = new ConnectionFactory();
		factory.setUri("amqps://ppvihfpt:BDGv28w-UU11pgRbRFJTStZyrY0L77Yd@stingray.rmq.cloudamqp.com/ppvihfpt");
	}
	
	public static synchronized Productor getInstance() throws Exception {
		if (instance == null)
			instance = new Productor();
		return instance;
	}
	
	public void abrirConexion() throws Exception {
		connection = factory.newConnection();
		channel = connection.createChannel();
	}
	

	public void enviarEvento(String tipo, Evento evento) throws Exception {
		final String exchangeName = "citybike";
		boolean durable = true;
		channel.exchangeDeclare(exchangeName, "topic", durable);
		
		final String queueName = "citybike-estaciones";
		channel.queueDeclare(queueName, durable, false, false, null);
		
		String routingKey = "citybike.alquileres";
		channel.queueBind(queueName, exchangeName, routingKey+".*");
				
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		String jsonEvento = mapper.writeValueAsString(evento);		
		
		channel.basicPublish(exchangeName, routingKey + tipo, new AMQP.BasicProperties.Builder()
					.build(), jsonEvento.getBytes());
	}
	
	public void cerrarConexion() throws Exception {
		channel.close();
		connection.close();
	}
	
}
