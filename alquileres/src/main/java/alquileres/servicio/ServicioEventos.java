package alquileres.servicio;

import java.time.LocalDateTime;

import alquileres.eventos.Evento;
import alquileres.eventos.bus.Productor;

public class ServicioEventos implements IServicioEventos {
	

	@Override
	public void publicarEventoAlquilerConcluido(String idBici) throws Exception {
		Productor productor = Productor.getInstance();
		productor.abrirConexion();
		productor.enviarEvento("alquiler-concluido", new Evento(idBici, LocalDateTime.now()));
		productor.cerrarConexion();
	}

	@Override
	public void publicarEventoBicicletaAlquilada(String idBici) throws Exception {
		Productor productor = Productor.getInstance();
		productor.abrirConexion();
		productor.enviarEvento("bicicleta-alquilada", new Evento(idBici, LocalDateTime.now()));
		productor.cerrarConexion();
	}

	@Override
	public void suscribirEventoBicicletaDesactivada() {
		

	}

}
