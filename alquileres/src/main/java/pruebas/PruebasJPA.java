package pruebas;

import persistencia.jpa.UsuarioJPA;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class PruebasJPA {
	public static void main(String[] args) {
		Repositorio<UsuarioJPA,String> repoUser=FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
		try {
			repoUser.add(new UsuarioJPA("1",0,false,false));
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
