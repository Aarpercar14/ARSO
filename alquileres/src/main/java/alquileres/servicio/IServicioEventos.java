package alquileres.servicio;


public interface IServicioEventos {
	void publicarEventoBicicletaAlquilada(String idBici) throws Exception;
	void suscribirEventoBicicletaDesactivada(String idBici);
	public void publicarEventoBicicletaReservada(String idBici) throws Exception;
	void publicarEventoAlquilerConcluido(String idBici, String idEstacion) throws Exception;
}
