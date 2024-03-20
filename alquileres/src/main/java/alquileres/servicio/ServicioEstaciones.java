package alquileres.servicio;

import org.springframework.stereotype.Service;

@Service
public class ServicioEstaciones implements IServicioEstaciones{

	@Override
	public boolean consultaHueco() {
		return true;
	}

	@Override
	public boolean peticionAparcarBicicleta() {
		return true;
	}
	

}
