package alquileres.eventos.bus;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Productor {
	

	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqps://ppvihfpt:BDGv28w-UU11pgRbRFJTStZyrY0L77Yd@stingray.rmq.cloudamqp.com/ppvihfpt");
		
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		/** Declaracion del Exchange **/
		
		final String exchangeName = "cityBike";
		
		boolean durable = true;
		channel.exchangeDeclare(exchangeName, "direct", durable);
		
		/** Envío del mensaje **/
		
		channel.close();
		connection.close();
	}
	
}
