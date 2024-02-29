package alquileres.servicio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Historial;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
import alquileres.repositorio.RepositorioJPAUsuario;
import persistencia.jpa.UsuarioJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;
import repositorio.RepositorioMemoria;

public class ServicioAlquileres implements IServicioAlquileres {
	
	private RepositorioJPA<UsuarioJPA> repoUsuarios = FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
	

	@Override
	public void reservar(String idUsuario, String IdBicicleta) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
				if(usuario.reservaActiva() == null && 
						usuario.alquilerActivo() == null && !usuario.bloqueado()) {
					Reserva reserva = new Reserva(IdBicicleta, LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.MINUTES));
					usuario.addReserva(reserva);
					usuarioJPA = this.encodeUsuarioJPA(usuario);
					repoUsuarios.update(usuarioJPA);
				}
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void confirmarReserva(String idUsuario) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			if(usuario.reservaActiva() != null) {
				Alquiler alquiler = new Alquiler(idUsuario, LocalDateTime.now());
				usuario.addAlquiler(alquiler);
				usuarioJPA = this.encodeUsuarioJPA(usuario);
				repoUsuarios.update(usuarioJPA);
			}
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void alquilar(String idUsuario, String idBicicleta) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idBicicleta);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			if(usuario.reservaActiva() == null &&
					usuario.alquilerActivo() == null && !usuario.bloqueado()) {
				Alquiler alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
				usuario.addAlquiler(alquiler);
				usuarioJPA = this.encodeUsuarioJPA(usuario);
				repoUsuarios.update(usuarioJPA);
			}
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Historial historialUsuario(String idUsuario) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			Historial historial = new Historial(idUsuario, usuario.bloqueado(), usuario.tiempoUsoSemana(), usuario.getReservas(), usuario.getAlquileres());
			return historial;			
		} catch (EntidadNoEncontrada | RepositorioException e) {
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
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			usuario.eliminarCaducadas();
			usuarioJPA = this.encodeUsuarioJPA(usuario);
			repoUsuarios.update(usuarioJPA);
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}

	}
	
	private Usuario decodeUsuarioJPA (UsuarioJPA usuarioJPA) {
		Usuario usuario = new Usuario(usuarioJPA.getId());
		usuario.setReservas(usuarioJPA.getReservas());
		usuario.setAlquileres(usuarioJPA.getAlquileres());
		return usuario;
	}
	
	private UsuarioJPA encodeUsuarioJPA (Usuario usuario) {
		UsuarioJPA usuarioJPA = new UsuarioJPA(usuario.getId(), usuario.getReservas(), usuario.getAlquileres());
		return usuarioJPA;
	}

}
