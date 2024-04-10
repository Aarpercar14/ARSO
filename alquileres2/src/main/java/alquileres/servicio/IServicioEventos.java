package alquileres.servicio;

public interface IServicioEventos {
	void publicarEventoAlquilerConcluido(String idBici) throws Exception;
	void publicarEventoBicicletaAlquilada(String idBici)throws Exception;
	void publicarEventoBicicletaDesactivada(String idBici);
	void suscribirEventoAlquilerConcluido();
	void suscribirEventoBicicletaAlquilada();
	void suscribirEventoBicicletaDesactivada();
}
