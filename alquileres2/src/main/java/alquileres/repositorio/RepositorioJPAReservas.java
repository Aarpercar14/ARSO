package alquileres.repositorio;

import persistencia.jpa.ReservaJPA;
import repositorio.RepositorioJPA;

public class RepositorioJPAReservas extends RepositorioJPA<ReservaJPA>{

	@Override
	public Class<ReservaJPA> getClase() {
		return ReservaJPA.class;
	}

	@Override
	public String getNombre() {
		return ReservaJPA.class.getName().substring(ReservaJPA.class.getName().lastIndexOf(".")+1);
	}

}