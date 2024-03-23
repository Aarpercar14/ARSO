package alquileres.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import alquileres.modelo.Bicicleta;
import alquileres.modelo.Estacionamiento;

public interface IServicioEstaciones {

	public boolean consultaHueco();

	public boolean peticionAparcarBicicleta();

	public String altaEstacion(String nombre, int puestos, String direccion, int cordX, int cordY);

	public void darDeBajaUnaBici(String idEstacion, String idBici, String motivo);

	public String altaDeUnaBici(String modelo, Estacionamiento estacion);

	public Page<Bicicleta> getListadoPaginadoGestor(String idEstacion,Pageable pageable);

	public Page<Estacionamiento> getListadoPaginadoUsuario(Pageable pageable);

	public String infoEstacion(String idEstacio);

	public Page<Bicicleta> getListadoBicisDisponibles(String estacion,Pageable pageable);

	public void estacionarUnaBicileta(String idBici, String idEstacion);

}
