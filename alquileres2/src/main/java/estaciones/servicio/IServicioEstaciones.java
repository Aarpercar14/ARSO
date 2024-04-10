package estaciones.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import estaciones.modelo.Bicicleta;
import estaciones.modelo.EstacionDTOUsuario;

public interface IServicioEstaciones {

	public boolean consultaHueco(String id);

	public boolean peticionAparcarBicicleta(String id);

	public String altaEstacion(String nombre, int puestos, String direccion, int cordX, int cordY);

	public void bajaBici(String idBici, String motivo);

	public String altaBici(String modelo, String estacion);

	public List<Bicicleta> getListadoPaginadoGestor(String idEstacion);

	public Page<EstacionDTOUsuario> getListadoPaginadoUsuario(Pageable pageable);

	public String infoEstacion(String idEstacio);

	public Page<Bicicleta> getListadoBicisDisponibles(String estacion,Pageable pageable);

	public void estacionarUnaBicileta(String idBici, String idEstacion);

}
