package estaciones.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.BicicletaDTO;
import estaciones.modelo.Estacionamiento;

public interface IServicioEstaciones {

	public String altaEstacion(String nombre, int puestos, String direccion, double cordX, double cordY);

	public void bajaBici(String idBici, String motivo);

	public String altaBici(String modelo, String estacion);

	public List<Bicicleta> getListadoPaginadoGestor(String idEstacion);

	public List<Estacionamiento> getListadoPaginadoUsuario();

	public Estacionamiento infoEstacion(String idEstacio);

	public List<Bicicleta> getListadoBicisDisponibles(String estacion);

	public void estacionarUnaBicileta(String idBici, String idEstacion);
	
}
