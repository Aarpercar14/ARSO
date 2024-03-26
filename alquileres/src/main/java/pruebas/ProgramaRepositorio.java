package pruebas;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import alquiler.AlquileresApp;
import alquileres.repositorio.RepositorioEstaciones;

public class ProgramaRepositorio {
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(AlquileresApp.class, args);
		RepositorioEstaciones repo=contexto.getBean(RepositorioEstaciones.class);
		System.out.println(repo.count());
		contexto.close();
	}
}
