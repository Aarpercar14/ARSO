package alquileres.servicio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Historial;
import alquileres.modelo.Usuario;
import persistencia.jpa.ReservasJPA;
import persistencia.jpa.UsuarioJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import repositorio.RepositorioMemoria;

public class ServicioAlquileres implements IServicioAlquileres {
	
	private RepositorioMemoria<Usuario> repoUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
	private Repositorio<UsuarioJPA,String> repositorio=FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
	

	@Override
	public void reservar(String idUsuario, String IdBicicleta) {
		try {
			UsuarioJPA usuario = repositorio.getById(idUsuario);
			Usuario u=new Usuario(usuario.getId());
			if(usuario.reservaActiva() == null && u.alquilerActivo() == null && !u.bloqueado()) {
				ReservasJPA reserva = new ReservasJPA(IdBicicleta, LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.MINUTES));
				u.addReserva(reserva);
				repositorio.update(usuario);
			}
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void confirmarReserva(String idUsuario) {
		try {
			Usuario usuario = repoUsuarios.getById(idUsuario);
			if(usuario.reservaActiva() != null) {
				Alquiler alquiler = new Alquiler(idUsuario, LocalDateTime.now());
				usuario.addAlquiler(alquiler);
				repoUsuarios.update(usuario);
			}
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
		}

	}

	@Override
	public void alquilar(String idUsuario, String idBicicleta) {
		try {
			Usuario usuario = repoUsuarios.getById(idBicicleta);
			if(usuario.reservaActiva() == null &&
					usuario.alquilerActivo() == null && !usuario.bloqueado()) {
				Alquiler alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
				usuario.addAlquiler(alquiler);
				repoUsuarios.update(usuario);
			}
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
		}

	}

	@Override
	public Historial historialUsuario(String idUsuario) {
		try {
			Usuario usuario = repoUsuarios.getById(idUsuario);
			Historial historial = new Historial(idUsuario, usuario.bloqueado(), usuario.tiempoUsoSemana(), usuario.getReservas(), usuario.getAlquileres());
			return historial;			
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void dejarBicicleta(String idUsuario, String isBicicleta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void liberarBloqueo(String idUsuario) {
		try {
			Usuario usuario = repoUsuarios.getById(idUsuario);
			usuario.eliminarCaducadas();
			repoUsuarios.update(usuario);
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
		}

	}

}
