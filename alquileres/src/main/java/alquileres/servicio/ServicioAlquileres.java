package alquileres.servicio;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import retrofit.alquileres.AlquileresRestClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import servicio.FactoriaServicios;

public class ServicioAlquileres implements IServicioAlquileres {

	private Repositorio<UsuarioJPA, String> repoUsuarios = FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
	private Retrofit retrofit = new Retrofit.Builder().baseUrl("http://estaciones:8082/")
			.addConverterFactory(JacksonConverterFactory.create()).build();
	private AlquileresRestClient alquileresClient = retrofit.create(AlquileresRestClient.class);
	private IServicioEventos servEventos = FactoriaServicios.getServicio(IServicioEventos.class);

	@Override
	public void reservar(String idUsuario, String IdBicicleta) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			if (usuarioJPA == null)
				usuarioJPA = crearUsuario(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			Reserva reserva = new Reserva(IdBicicleta, LocalDateTime.now(),
					LocalDateTime.now().plus(1, ChronoUnit.MINUTES));
			// Aunque originalmente el tiempo de caducidad para las reservas era 30 minutos
			// lo hemos reducido a un minuto para facilitar las pruebas
			usuario.addReserva(reserva);

			repoUsuarios.delete(usuarioJPA);
			usuarioJPA = this.encodeUsuarioJPA(usuario);
			repoUsuarios.add(usuarioJPA);
			try {
				servEventos.publicarEventoBicicletaReservada(IdBicicleta);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			Alquiler alquiler = new Alquiler(usuario.reservaActiva().getIdBicicleta(), LocalDateTime.now());
			usuario.addAlquiler(alquiler);
			repoUsuarios.delete(usuarioJPA);
			usuarioJPA = this.encodeUsuarioJPA(usuario);
			repoUsuarios.add(usuarioJPA);
			try {
				servEventos.publicarEventoBicicletaAlquilada(alquiler.getIdBicicleta());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void alquilar(String idUsuario, String idBicicleta) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			if (usuarioJPA == null)
				usuarioJPA = crearUsuario(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
			Alquiler alquiler = new Alquiler(idBicicleta, LocalDateTime.now());
			usuario.addAlquiler(alquiler);
			repoUsuarios.delete(usuarioJPA);
			usuarioJPA = this.encodeUsuarioJPA(usuario);
			repoUsuarios.add(usuarioJPA);
			try {
				servEventos.publicarEventoBicicletaAlquilada(alquiler.getIdBicicleta());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Usuario historialUsuario(String idUsuario) {
		try {
			UsuarioJPA usuarioJPA = repoUsuarios.getById(idUsuario);
			if (usuarioJPA != null) {
				Usuario usuario = this.decodeUsuarioJPA(usuarioJPA);
				return usuario;
			}
			System.out.println("El usuario no existe");
			return null;
		} catch (EntidadNoEncontrada | RepositorioException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void dejarBicicleta(String idUsuario, String idEstacion) {
		try {
			UsuarioJPA user = repoUsuarios.getById(idUsuario);
			Usuario usuario = this.decodeUsuarioJPA(user);
			String info;
			info = alquileresClient.getInfoEstacion(idEstacion).execute().body();
			if (hayHuecosDisponibles(info)) {
				try {

					servEventos.publicarEventoAlquilerConcluido(usuario.alquilerActivo().getIdBicicleta(), idEstacion);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				info = alquileresClient.dejarBicicleta(idEstacion, usuario.alquilerActivo().getIdBicicleta()).execute()
						.body();
				Alquiler alq = usuario.alquilerActivo();
				alq.finalizar(LocalDateTime.now());
				System.out.println("Alquileres:");
				for (Alquiler a : usuario.getAlquileres())
					System.out.println(
							"IdBici= " + a.getIdBicicleta() + ", Inicio= " + a.getInicio() + ", Fin= " + a.getFin());

			}
			System.out.println("Usuario: Id: " + usuario.getId() + ", alquileres: " + usuario.getAlquileres().toString()
					+ ", reservas: " + usuario.getReservas());
			repoUsuarios.delete(user);
			user = encodeUsuarioJPA(usuario);
			repoUsuarios.add(user);
			System.out.println(repoUsuarios.getById(idUsuario));
		} catch (RepositorioException | EntidadNoEncontrada | IOException e) {
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
		for (ReservaJPA r : reservasJPA) {
			reservas.add(decodeReservaJPA(r));
		}
		return reservas;
	}

	private Reserva decodeReservaJPA(ReservaJPA reservaJPA) {
		Reserva reserva = new Reserva(reservaJPA.getIdBicicleta(), reservaJPA.getCreada(), reservaJPA.getCaducidad());
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
		Alquiler alquiler = new Alquiler(alquilerJPA.getIdBicicleta(), alquilerJPA.getInicio());
		if (alquilerJPA.getFin().isAfter(alquilerJPA.getInicio()))
			alquiler.setFin(alquilerJPA.getFin());
		return alquiler;
	}

	private UsuarioJPA encodeUsuarioJPA(Usuario usuario) {
		UsuarioJPA usuarioJPA = new UsuarioJPA(usuario.getId(), encodeReservasJPA(usuario.getReservas()),
				encodeAlquileresJPA(usuario.getAlquileres()));

		for (ReservaJPA r : usuarioJPA.getReservas())
			r.setUsuarioR(usuarioJPA);
		for (AlquilerJPA a : usuarioJPA.getAlquileres())
			a.setUsuarioA(usuarioJPA);
		return usuarioJPA;
	}

	private List<ReservaJPA> encodeReservasJPA(List<Reserva> reservas) {
		List<ReservaJPA> reservasJPA = new ArrayList<ReservaJPA>();
		for (Reserva r : reservas) {
			reservasJPA.add(encodeReservaJPA(r));
		}
		return reservasJPA;
	}

	private ReservaJPA encodeReservaJPA(Reserva reserva) {
		ReservaJPA reservaJPA = new ReservaJPA(reserva.getIdBicicleta(), reserva.getCreada(), reserva.getCaducidad());
		return reservaJPA;
	}

	private List<AlquilerJPA> encodeAlquileresJPA(List<Alquiler> alquileres) {
		List<AlquilerJPA> alquileresJPA = new ArrayList<>();
		for (Alquiler a : alquileres) {
			alquileresJPA.add(encodeAlquilerJPA(a));
		}
		return alquileresJPA;
	}

	private AlquilerJPA encodeAlquilerJPA(Alquiler alquiler) {
		AlquilerJPA alquilerJPA = new AlquilerJPA(alquiler.getIdBicicleta(), alquiler.getInicio());
		if (!alquiler.activa())
			alquilerJPA.setFin(alquiler.getFin());
		return alquilerJPA;
	}

	private UsuarioJPA crearUsuario(String idUsuario) throws EntidadNoEncontrada {
		UsuarioJPA usuario = new UsuarioJPA(idUsuario, new ArrayList<ReservaJPA>(), new ArrayList<AlquilerJPA>());
		try {
			repoUsuarios.add(usuario);
			return repoUsuarios.getById(idUsuario);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean hayHuecosDisponibles(String info) {
		Pattern pattern = Pattern.compile("numPuestos=(\\d+)");
		Matcher matcher = pattern.matcher(info);
		if (matcher.find()) {
			String numPuestosStr = matcher.group(1); // Obtiene el primer grupo de captura
			int numPuestos = Integer.parseInt(numPuestosStr);
			if (numPuestos > 0)
				return true;
			return false;
		}
		return false;
	}
}
