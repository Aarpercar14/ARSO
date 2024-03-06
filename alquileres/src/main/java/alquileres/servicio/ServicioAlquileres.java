package alquileres.servicio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import alquileres.modelo.Usuario;
import persistencia.jpa.AlquilerJPA;
import persistencia.jpa.ReservaJPA;
import persistencia.jpa.UsuarioJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioAlquileres implements IServicioAlquileres {

	private Repositorio<UsuarioJPA, String> repoUsuarios = FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
	private IServicioEstaciones servEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

	@Override
	public void reservar(String idUsuario, String IdBicicleta) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			if (usuario.reservaActiva() == null && usuario.alquilerActivo() == null && !usuario.bloqueado()) {
				Reserva reserva = new Reserva(IdBicicleta, LocalDateTime.now(),LocalDateTime.now().plus(30, ChronoUnit.MINUTES));
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
			if (usuario.reservaActiva() != null) {
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
			if (usuario.reservaActiva() == null && usuario.alquilerActivo() == null && !usuario.bloqueado()) {
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
	public Usuario historialUsuario(String idUsuario) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			return usuario;
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void dejarBicicleta(String idUsuario, String isBicicleta) {
		try {
			UsuarioJPA user = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(user);
			if (usuario.alquilerActivo().activa()) {
				if (servEstaciones.peticionAparcarBicicleta()) {
					usuario.getAlquileres().remove(usuario.alquilerActivo());
				}
			}
			user=encodeUsuarioJPA(usuario);
			repoUsuarios.update(user);
		} catch (RepositorioException | EntidadNoEncontrada e) {
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

	private Usuario decodeUsuarioJPA(UsuarioJPA usuarioJPA) {
		Usuario usuario = new Usuario(usuarioJPA.getId());
		usuario.setReservas(decodeReservasJPA(usuarioJPA.getReservas()));
		usuario.setAlquileres(decodeAlquileresJPA(usuarioJPA.getAlquileres()));
		return usuario;
	}
	
	private List<Reserva> decodeReservasJPA(List<ReservaJPA> reservasJPA) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		for(ReservaJPA r : reservasJPA) {
			reservas.add(decodeReservaJPA(r));
		}
		return reservas;
	}

	private Reserva decodeReservaJPA(ReservaJPA reservaJPA) {
		Reserva reserva = new Reserva(reservaJPA.getId(), reservaJPA.getCreada(), reservaJPA.getCaducidad());
		return reserva;
	}
	
	private List<Alquiler> decodeAlquileresJPA(List<AlquilerJPA> alquileresJPA) {
		List<Alquiler> alquileres = new ArrayList<Alquiler>();
		
		for(AlquilerJPA a : alquileresJPA) {
			alquileres.add(decodeAlquilerJPA(a));
		}
		return alquileres;
	}

	private Alquiler decodeAlquilerJPA(AlquilerJPA alquilerJPA) {
		Alquiler alquiler = new Alquiler(alquilerJPA.getId(), alquilerJPA.getInicio());
		return alquiler;
	}

	private UsuarioJPA encodeUsuarioJPA(Usuario usuario) {
		UsuarioJPA usuarioJPA = new UsuarioJPA(usuario.getId(), encodeReservasJPA(usuario.getReservas(),usuario.getId()),
									encodeAlquileresJPA(usuario.getAlquileres(),usuario.getId()));
		return usuarioJPA;
	}
	
	
	private List<ReservaJPA> encodeReservasJPA(List<Reserva> reservas,String id) {
		List<ReservaJPA> reservasJPA = new ArrayList<ReservaJPA>();
		for(Reserva r : reservas) {
			reservasJPA.add(encodeReservaJPA(r,id));
		}
		return reservasJPA;
	}
	
	private ReservaJPA encodeReservaJPA(Reserva reserva,String id) {
		ReservaJPA reservaJPA = new ReservaJPA(reserva.getIdBicicleta(), reserva.getCreada(), reserva.getCaducidad(),id);
		return reservaJPA;
	}
	
	
	private List<AlquilerJPA> encodeAlquileresJPA(List<Alquiler> alquileres,String id) {
		List<AlquilerJPA> alquileresJPA = new ArrayList<>();
		for(Alquiler a : alquileres) {
			alquileresJPA.add(encodeAlquilerJPA(a,id));
		}
		return alquileresJPA;
	}
	
	private AlquilerJPA encodeAlquilerJPA(Alquiler alquiler,String id) {
		AlquilerJPA alquilerJPA = new AlquilerJPA(alquiler.getIdBicicleta(), alquiler.getInicio(), alquiler.getFin(),id);
		return alquilerJPA;
	}

	

}
