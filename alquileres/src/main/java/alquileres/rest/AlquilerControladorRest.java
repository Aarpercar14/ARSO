package alquileres.rest;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import alquileres.modelo.Usuario;
import alquileres.servicio.IServicioAlquileres;
import io.jsonwebtoken.Claims;
import servicio.FactoriaServicios;

@Path("alquileres")
public class AlquilerControladorRest {
	
    private IServicioAlquileres servicio =  FactoriaServicios.getServicio(IServicioAlquileres.class);
    @Context
	private UriInfo uriInfo;
    @Context
    private HttpServletRequest servletRequest;
    
    //Por defecto la aplicacion no soportaba los 
    //tipos de datos de localDiteTime sin este modulo
    ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("usuario")
    @Path("/TestService")
    public String info(){
    	if (this.servletRequest.getAttribute("claims") != null) {//Mostrar por consola identificacion del usuario
    	    Claims claims = (Claims) this.servletRequest.getAttribute("claims");
    	    System.out.println("Usuario autenticado: " + claims.getSubject());
    	    System.out.println("Roles: " + claims.get("roles"));
    	}
        return "This is the testservice";
    }
    
    @POST
    @Path("/usuarios/{idUsuario}/reservas/{idBicicleta}")
    @RolesAllowed("usuario")
    public Response reservar( @PathParam("idUsuario") String idUsuario, 
    							@PathParam("idBicicleta") String idBicicleta)
    									throws Exception {
    	
    		servicio.reservar(idUsuario, idBicicleta);   		
    		return Response.status(Response.Status.OK).build();
    	}
    
    @POST
    @Path("/usuarios/{idUsuario}/reserva")
    @RolesAllowed("usuario")
    public Response confirmarReserva(@PathParam("idUsuario") String idUsuario) throws Exception {
    	servicio.confirmarReserva(idUsuario);
    	return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @POST
    @Path("/usuarios/{idUsuario}/alquileres/{idBicicleta}")
    @RolesAllowed("usuario")
    public Response alquilar(@PathParam("idUsuario") String idUsuario,
    									@PathParam("idBicicleta") String idBicicleta)
    											throws Exception {
    	servicio.alquilar(idUsuario, idBicicleta);
    	return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @PUT
    @Path("/usuarios/{idUsuario}")
    @RolesAllowed("admin")
    public Response desbloquearUsuario(@PathParam("idUsuario") String idUsuario) throws Exception {
    	servicio.liberarBloqueo(idUsuario);
    	return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @GET
    @Path("/usuarios/{idUsuario}/historial")
    @RolesAllowed("admin")
      @Produces({MediaType.APPLICATION_JSON})
    public Response getHistorialUsuario(@PathParam("idUsuario") String idUsuario) throws Exception {
    	Usuario usuario = servicio.historialUsuario(idUsuario);
    	if(usuario==null) return Response.status(Response.Status.NO_CONTENT).build();
    	return Response.status(Response.Status.OK)
    			.entity(usuarioToDTO(usuario)).build();
    }
    
    private UsuarioDTO usuarioToDTO(Usuario usuario) {
    	UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId());
    	usuarioDTO.setReservas(usuario.getReservas());
    	usuarioDTO.setAlquileres(usuario.getAlquileres());
    	return usuarioDTO;
    }
    
    
}
