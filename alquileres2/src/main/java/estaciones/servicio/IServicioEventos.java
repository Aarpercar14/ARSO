package estaciones.servicio;

import java.time.LocalDateTime;

public interface IServicioEventos {
	void publicarEventoBicicletaDesactivada(String idBici);
	void suscribirEventoBicicletaAlquilada(String idBici, LocalDateTime fecha);
	void suscribirEventoAlquilerConcluido(String idBicicleta, String idEstacion, LocalDateTime fecha);
}
