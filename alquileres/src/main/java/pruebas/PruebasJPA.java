package pruebas;

import java.util.ArrayList;
import persistencia.jpa.AlquilerJPA;
import persistencia.jpa.ReservaJPA;
import persistencia.jpa.UsuarioJPA;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class PruebasJPA {
	public static void main(String[] args) {
		Repositorio<UsuarioJPA,String> repoUser=FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
		try {
			repoUser.add(new UsuarioJPA("Pepe Martinez",new ArrayList<ReservaJPA>(),new ArrayList<AlquilerJPA>()));
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
