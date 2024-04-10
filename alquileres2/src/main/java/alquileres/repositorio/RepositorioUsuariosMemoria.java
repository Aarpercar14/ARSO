package alquileres.repositorio;

import alquileres.modelo.Usuario;
import repositorio.RepositorioMemoria;

public class RepositorioUsuariosMemoria extends RepositorioMemoria<Usuario>{
	public RepositorioUsuariosMemoria() {
		Usuario usuario = new Usuario("Usuario 1");
		this.add(usuario);
	}
}
