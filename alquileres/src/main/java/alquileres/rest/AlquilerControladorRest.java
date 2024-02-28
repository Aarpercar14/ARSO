package alquileres.rest;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import alquileres.servicio.IServicioAlquileres;
import servicio.FactoriaServicios;

@Path("alquileres")
public class AlquilerControladorRest {
    private IServicioAlquileres servicio =  FactoriaServicios.getServicio(IServicioAlquileres.class);
    
    @POST
    @Path("/{idUsuario}/{idBicicleta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActividad( @PathParam("idUsuario") String idUsuario, @PathParam("idBicicleta") String idBicicleta)
    		throws Exception {
    	   return Response.status(Response.Status.OK)
    	                  .entity(servicio.reservar(idUsuario,idBicicleta)).build();
    	}
}
