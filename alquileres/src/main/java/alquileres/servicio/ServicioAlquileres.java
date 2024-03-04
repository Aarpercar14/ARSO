package alquileres.servicio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Historial;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
//github.com/Aarpercar14/ARSO.git
import persistencia.jpa.UsuarioJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioAlquileres implements IServicioAlquileres {
	

	private Repositorio<UsuarioJPA,String> repoUsuarios = FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
	private IServicioEstaciones servEstaciones=FactoriaServicios.getServicio(IServicioEstaciones.class);

	/*@Override
	public void crearUsuario(String idUsuario) {
		Usuario usuario = new Usuario(idUsuario);
		UsuarioJPA usuarioJPA = this.encodeUsuarioJPA(usuario);
		try {
			repoUsuarios.add(usuarioJPA);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Usuario getUsuario(String idUsuario) {
		UsuarioJPA usuarioJPA;
		try {
			usuarioJPA = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			return usuario;
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}		
		return null;
	}*/
	
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
		try {
			UsuarioJPA user=repoUsuarios.getById(idUsuario);
			Usuario usuario=this.decodeUsuarioJPA(user);
			if(usuario.alquilerActivo().activa()) {
				if(servEstaciones.peticionAparcarBicicleta()) {
					usuario.getAlquileres().remove(usuario.alquilerActivo());
				}
			}
		} catch (RepositorioException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
