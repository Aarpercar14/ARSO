package alquileres.servicio;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import alquileres.modelo.Bicicleta;
import alquileres.modelo.Estacionamiento;
import alquileres.repositorio.RepositorioBicicletas;
import alquileres.repositorio.RepositorioEstaciones;
import repositorio.RepositorioException;

@Service
public class ServicioEstaciones implements IServicioEstaciones {

	private RepositorioEstaciones repositorioEst;
	private RepositorioBicicletas repositorioBicicletas;

	@Autowired
	public ServicioEstaciones(RepositorioEstaciones repositorioEst, RepositorioBicicletas repositorioBicicletas) {
		this.repositorioEst = repositorioEst;
		this.repositorioBicicletas = repositorioBicicletas;
	}

	@Override
	public boolean consultaHueco() {
		return true;
	}

	@Override
	public boolean peticionAparcarBicicleta() {
		return true;
	}

	@Override
	public String altaEstacion(String nombre, int puestos, String direccion, int cordX, int cordY) {
		Estacionamiento estacion = new Estacionamiento(nombre, puestos, direccion, cordX, cordY);
		String id = repositorioEst.save(estacion).getId();
		return id;
	}

	@Override
	public String altaDeUnaBici(String modelo, Estacionamiento estacion) {
		String id = UUID.randomUUID().toString();
		Bicicleta bici = new Bicicleta(id, modelo);
		repositorioBicicletas.save(bici);
		this.estacionarUnaBicileta(id, estacion.getId());
		return id;
	}

	@Override
	public void darDeBajaUnaBici(String idEstacion, String idBici, String motivo) {
		Bicicleta bici = repositorioBicicletas.findById(idBici).get();
		this.retirarUnaBicleta(idBici, idEstacion);
		bici.cambioEstadoBici("no disponible");
		bici.setFechaBaja(LocalDateTime.now());
		bici.setMotivoBaja(motivo);
		repositorioBicicletas.save(bici);
	}

	@Override
	public Page<Bicicleta> getListadoPaginadoGestor(String idEstacion, Pageable page) {
		Estacionamiento est = repositorioEst.findById(idEstacion).get();
		// Mal hecho TODO
		return repositorioBicicletas.findAll(page);
	}

	@Override
	public Page<Estacionamiento> getListadoPaginadoUsuario(Pageable pageable) {
		return repositorioEst.findAll(pageable);
	}

	@Override
	public String infoEstacion(String idEstacio) {
		//TODO
		return "Esto deberia ser un dto?";
	}

	@Override
	public Page<Bicicleta> getListadoBicisDisponibles(String estacion,Pageable pageable) {
		// Mal hecho TODO
		return repositorioBicicletas.findAll(pageable);
	}

	@Override
	public void estacionarUnaBicileta(String idBici, String idEstacion) {
		try {
			Estacionamiento estacion = repositorioEst.findById(idEstacion).get();
			Bicicleta bici = repositorioBicicletas.findById(idBici).get();
			estacion.estacionarBici(bici);
			repositorioEst.save(estacion);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}

	private void retirarUnaBicleta(String idBici, String idEstacion) {
		Estacionamiento estacion = repositorioEst.findById(idEstacion).get();
		estacion.sacarBici(idBici);
		repositorioEst.save(estacion);
	}

//	Alta de una estación de aparcamiento. Recibe como parámetros el nombre, número de puestos, dirección postal y coordenadas geográficas.
//		La aplicación proporcionará un identificador único a la estación y establecerá automáticamente la fecha de alta. 
//		La operación retorna el identificador de la nueva estación.
//	Alta de una bicicleta. La información recibida en la operación es el modelo de la bicicleta y el identificador de la estación en la que se aparca
//		inicialmente. La aplicación proporcionará un identificador único a la bici (código) y establecerá automáticamente la fecha de alta.
//		La operación retorna el identificador de la bici creada. La bici se considera disponible y ocupa sitio en la estación. 
//		Requisito: la estación debe tener un puesto libre para que la bici quede aparcada.
//	Dar de baja una bicicleta. Esta operación recibe como parámetro el identificador de la bici y el motivo de baja. Establece automáticamente la
//		fecha de baja. Esta bici se considera no disponible y ya no ocupa sitio en ninguna estación. Nótese que la bicicleta no es eliminada,
//		sino que cambia su estado.
//	Obtener un listado con todas las bicicletas de una estación. Devuelve las bicicletas que están aparcadas en la estación.
//	
}
