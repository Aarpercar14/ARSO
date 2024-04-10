package alquileres.servicio;

public interface IServicioEventos {
	void publicarEventoBicicletaDesactivada(String idBici);
	void suscribirEventoAlquilerConcluido();
	void suscribirEventoBicicletaAlquilada();
}
