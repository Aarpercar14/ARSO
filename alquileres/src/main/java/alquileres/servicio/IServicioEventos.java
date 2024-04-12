package alquileres.servicio;


public interface IServicioEventos {
	void publicarEventoBicicletaAlquilada(String idBici) throws Exception;
	void suscribirEventoBicicletaDesactivada(String idBici);
	void publicarEventoAlquilerConcluido(String idBici, String idEstacion) throws Exception;
}
