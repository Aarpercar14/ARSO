package estaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones.repositorio.RepositorioBicicletas;

public class ProgramaRepositorio {
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(EstacionesApplication.class, args);
		RepositorioBicicletas repo=contexto.getBean(RepositorioBicicletas.class);
		System.out.println(repo.findAll());
		contexto.close();
	}

}
