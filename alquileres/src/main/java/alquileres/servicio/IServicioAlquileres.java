package alquileres.servicio;


import alquileres.modelo.Usuario;

public interface IServicioAlquileres {
	
	/*void crearUsuario(String idUsuario);
	public Usuario getUsuario(String idUsuario);*/
	void reservar(String idUsuario, String IdBicicleta);
	void confirmarReserva(String idUsuario);
	void alquilar(String idUsuario, String idBicicleta);
	Usuario historialUsuario(String idUsuario);
	void dejarBicicleta(String idUsuario, String isBicicleta);
	void liberarBloqueo(String idUsuario);
}
