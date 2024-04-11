package estaciones;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.eventos.Evento;
import estaciones.eventos.bus.PublicadorEventos;

public class TestEventos {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = SpringApplication.run(EstacionesApplication.class, args);
		
		PublicadorEventos sender = context.getBean(PublicadorEventos.class);
		
		Evento evento = new Evento("test", LocalDateTime.now());
		
		sender.sendMessage(evento, "test");
		
		context.close();
		
		System.out.println("fin. ");
	}
}
