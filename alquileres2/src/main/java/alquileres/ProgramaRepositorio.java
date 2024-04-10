package alquileres;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import alquileres.repositorio.RepositorioBicicletas;
import alquileres.repositorio.RepositorioEstaciones;

public class ProgramaRepositorio {
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(Alquileres2Application.class, args);
		RepositorioBicicletas repo=contexto.getBean(RepositorioBicicletas.class);
		System.out.println(repo.findAll());
		contexto.close();
	}

}
