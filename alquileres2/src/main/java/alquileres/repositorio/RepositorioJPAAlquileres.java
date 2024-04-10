package alquileres.repositorio;

import persistencia.jpa.AlquilerJPA;
import repositorio.RepositorioJPA;

public class RepositorioJPAAlquileres extends RepositorioJPA<AlquilerJPA>{

	@Override
	public Class<AlquilerJPA> getClase() {
		return AlquilerJPA.class;
	}

	@Override
	public String getNombre() {
		return AlquilerJPA.class.getName().substring(AlquilerJPA.class.getName().lastIndexOf(".")+1);
	}
}