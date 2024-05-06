package arso.rest;

import java.util.Dictionary;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PasarelaClientRest {
	@GET("usuarios/verificarOauth/{oauth}")
	Call<ClaimsData> getUserClaims(@Path("oauth") String oauth);
}
