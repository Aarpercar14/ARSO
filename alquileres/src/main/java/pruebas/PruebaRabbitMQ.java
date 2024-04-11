package pruebas;

import alquileres.servicio.IServicioAlquileres;
import servicio.FactoriaServicios;

public class PruebaRabbitMQ {

	public static void main(String[] args) {
		IServicioAlquileres servAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
		
		servAlquileres.alquilar("Rabbit5", "bicicletaRabbit5");
		servAlquileres.dejarBicicleta("Rabbit5", "bicicletaRabbit5");

	}

}
