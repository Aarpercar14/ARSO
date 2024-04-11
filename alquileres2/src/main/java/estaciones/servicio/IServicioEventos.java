package estaciones.servicio;

import java.time.LocalDateTime;

public interface IServicioEventos {
	void publicarEventoBicicletaDesactivada(String idBici);
	void suscribirEventoAlquilerConcluido(String idBici, LocalDateTime fecha);
	void suscribirEventoBicicletaAlquilada(String idBici, LocalDateTime fecha);
}
