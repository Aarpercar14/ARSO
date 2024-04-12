package pruebas;

import alquileres.servicio.IServicioAlquileres;
import servicio.FactoriaServicios;

public class PruebaRabbitMQAparcar {

	public static void main(String[] args) {
		IServicioAlquileres servAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
		
		servAlquileres.dejarBicicleta("Rabbit7", "6618f064e1548259620ca9ee");
	}
}
