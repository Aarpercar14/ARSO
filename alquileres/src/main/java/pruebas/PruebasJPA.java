package pruebas;

import java.util.ArrayList;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import persistencia.jpa.UsuarioJPA;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class PruebasJPA {
	public static void main(String[] args) {
		Repositorio<UsuarioJPA,String> repoUser=FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
		try {
			repoUser.add(new UsuarioJPA("5",new ArrayList<Reserva>(),new ArrayList<Alquiler>()));
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
