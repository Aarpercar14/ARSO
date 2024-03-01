package alquileres.rest;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import alquileres.servicio.IServicioAlquileres;
import servicio.FactoriaServicios;

@Path("alquileres")
public class AlquilerControladorRest {
	
    private IServicioAlquileres servicio =  FactoriaServicios.getServicio(IServicioAlquileres.class);
    
    @POST
    @Path("/usuarios/{idUsuario}/reservas/{idBicicleta}")
    public Response reservar( @PathParam("idUsuario") String idUsuario, 
    							@PathParam("idBicicleta") String idBicicleta)
    									throws Exception {
    	
    		servicio.reservar(idUsuario, idBicicleta);   		
    		return Response.status(Response.Status.NO_CONTENT).build();
    		
    	}
    
    @POST
    @Path("/usuarios/{idUsuario}/reserva/confirmar")
    public Response confirmarReserva(@PathParam("idUsuario") String idUsuario) throws Exception {
    	servicio.confirmarReserva(idUsuario);
    	return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @POST
    @Path("/usuarios/{idUsuario}/alquileres/{idBicicleta}")
    public Response alquilar(@PathParam("idUsuario") String idUsuario,
    									@PathParam("idBicicleta") String idBicicleta)
    											throws Exception {
    	servicio.alquilar(idUsuario, idBicicleta);
    	return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @POST
    @Path("/usuarios/{idUsuario}/desbloquear")
    public Response desbloquearUsuario(@PathParam("idUsuario") String idUsuario) throws Exception {
    	servicio.liberarBloqueo(idUsuario);
    	return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @GET
    @Path("/usuarios/{idUsuario}/historial")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getHistorialUsuario(@PathParam("idUsuario") String idUsuario) throws Exception {
    	return Response.status(Response.Status.OK)
    			.entity(servicio.historialUsuario(idUsuario)).build();
    }
    
    
}
