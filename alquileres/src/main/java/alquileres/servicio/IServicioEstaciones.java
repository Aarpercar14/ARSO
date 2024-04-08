package alquileres.servicio;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IServicioEstaciones {

	@GET("estaciones/public/hueco/{id}")
	Call<Boolean> consultaHueco(@Path("id")String id) ;

	@GET("estaciones/public/peticion/{id}")
	Call<Boolean> peticionAparcarBicicleta(@Path("id")String id);

	

}
