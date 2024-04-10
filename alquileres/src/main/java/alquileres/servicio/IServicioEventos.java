package alquileres.servicio;

public interface IServicioEventos {
	void publicarEventoAlquilerConcluido(String idBici);
	void publicarEventoBicicletaAlquilada(String idBici);
	void publicarEventoBicicletaDesactivada(String idBici);
	void suscribirEventoAlquilerConcluido();
	void suscribirEventoBicicletaAlquilada();
	void suscribirEventoBicicletaDesactivada();
}
