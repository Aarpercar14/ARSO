package alquileres.servicio;


import estaciones.modelo.Usuario;

public interface IServicioAlquileres {
	
	/*void crearUsuario(String idUsuario);
	public Usuario getUsuario(String idUsuario);*/
	void reservar(String idUsuario, String IdBicicleta);
	void confirmarReserva(String idUsuario);
	void alquilar(String idUsuario, String idBicicleta);
	Usuario historialUsuario(String idUsuario);
	void dejarBicicleta(String idUsuario, String idEstacion);
	void liberarBloqueo(String idUsuario);
}
