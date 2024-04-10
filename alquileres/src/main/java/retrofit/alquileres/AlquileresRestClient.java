package retrofit.alquileres;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AlquileresRestClient {
	
	@GET("estaciones/infoEstacion/{idEstacion}")
	Call<String> getInfoEstacion(@Path("idEstacion") String idEstacion);
	
	@POST("estaciones/aparcamientoBici/{idEstacion}/{idBici}")
	Call<Void> dejarBicicleta(@Path("idEstacion") String idEstacion, @Path("idBici") String idBici);

}
