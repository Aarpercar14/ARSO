package pruebas;

import alquileres.servicio.IServicioAlquileres;
import servicio.FactoriaServicios;

public class PruebaRabbitMQAlquilar {

	public static void main(String[] args) {
		IServicioAlquileres servAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);

		servAlquileres.alquilar("Rabbit7", "4774e136-3b33-4e67-9012-51da499f207a");
	}
}
