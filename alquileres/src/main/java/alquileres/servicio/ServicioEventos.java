package alquileres.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import alquileres.eventos.Evento;
import alquileres.eventos.bus.Productor;
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

public class ServicioEventos implements IServicioEventos {
	
	private Repositorio<UsuarioJPA, String> repoUsuarios = FactoriaRepositorios.getRepositorio(UsuarioJPA.class);

	@Override
	public void publicarEventoAlquilerConcluido(String idBici, String idEstacion) throws Exception {
		Productor productor = Productor.getInstance();
		productor.abrirConexion();
		productor.enviarEvento(".alquiler-concluido", new Evento(idBici, LocalDateTime.now(), idEstacion));
		productor.cerrarConexion();
	}

	@Override
	public void publicarEventoBicicletaAlquilada(String idBici) throws Exception {
		Productor productor = Productor.getInstance();
		productor.abrirConexion();
		productor.enviarEvento(".bicicleta-alquilada", new Evento(idBici, LocalDateTime.now(), ""));
		productor.cerrarConexion();
	}

	@Override
	public void suscribirEventoBicicletaDesactivada(String idBici) {
		List<UsuarioJPA> usuarios;
		try {
			usuarios = repoUsuarios.getAll();
			for(UsuarioJPA u : usuarios) {
				Usuario user = decodeUsuarioJPA(u);
				if(user.reservaActiva().getIdBicicleta().equals(idBici)) {
					user.getReservas().remove(user.reservaActiva());
					UsuarioJPA actualizado = encodeUsuarioJPA(user);
					try {
						repoUsuarios.update(actualizado);
					} catch (EntidadNoEncontrada e) {
						e.printStackTrace();
					}
				}
					
			}
		} catch (RepositorioException e) {
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
		for (ReservaJPA r : reservasJPA) {
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

		for (AlquilerJPA a : alquileresJPA) {
			alquileres.add(decodeAlquilerJPA(a));
		}
		return alquileres;
	}

	private Alquiler decodeAlquilerJPA(AlquilerJPA alquilerJPA) {
		Alquiler alquiler = new Alquiler(alquilerJPA.getId(), alquilerJPA.getInicio());
		return alquiler;
	}

	private UsuarioJPA encodeUsuarioJPA(Usuario usuario) {
		UsuarioJPA usuarioJPA = new UsuarioJPA(usuario.getId(),
				encodeReservasJPA(usuario.getReservas(), usuario.getId()),
				encodeAlquileresJPA(usuario.getAlquileres(), usuario.getId()));
		return usuarioJPA;
	}

	private List<ReservaJPA> encodeReservasJPA(List<Reserva> reservas, String id) {
		List<ReservaJPA> reservasJPA = new ArrayList<ReservaJPA>();
		for (Reserva r : reservas) {
			reservasJPA.add(encodeReservaJPA(r, id));
		}
		return reservasJPA;
	}

	private ReservaJPA encodeReservaJPA(Reserva reserva, String id) {
		ReservaJPA reservaJPA = new ReservaJPA(reserva.getIdBicicleta(), reserva.getCreada(), reserva.getCaducidad(),
				id);
		return reservaJPA;
	}

	private List<AlquilerJPA> encodeAlquileresJPA(List<Alquiler> alquileres, String id) {
		List<AlquilerJPA> alquileresJPA = new ArrayList<>();
		for (Alquiler a : alquileres) {
			alquileresJPA.add(encodeAlquilerJPA(a, id));
		}
		return alquileresJPA;
	}

	private AlquilerJPA encodeAlquilerJPA(Alquiler alquiler, String id) {
		AlquilerJPA alquilerJPA = new AlquilerJPA(alquiler.getIdBicicleta(), alquiler.getInicio(), alquiler.getFin(),
				id);
		return alquilerJPA;
	}

}
