package estaciones.modelo;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad Estacion")
public class EstacionDTOUsuario {
	@Schema(description="Id de usuario")
	private String id;
	@Schema(description="Nombre de la estacion")
	@NotNull
	private String nombre;
	@Schema(description="Si hay puestos libres")
	private boolean puestosLibres;
	@Schema(description="Codigo Postal")
	private String postal;
	@Schema(description="Coordenada Y")
	private double cordY;
	@Schema(description="Coordenada X")
	private double cordX;
	@Schema(description="Fecha de alta")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime fechaAlta;
	public EstacionDTOUsuario(String id,String nombre, boolean numPuestos, String postal, double cordY, double cordX, LocalDateTime fechaAlta) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.puestosLibres = numPuestos;
		this.postal = postal;
		this.cordY = cordY;
		this.cordX = cordX;
		this.fechaAlta=fechaAlta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean getNumPuestos() {
		return puestosLibres;
	}
	public void setNumPuestos(boolean numPuestos) {
		this.puestosLibres = numPuestos;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public double getCordY() {
		return cordY;
	}
	public void setCordY(double cordY) {
		this.cordY = cordY;
	}
	public double getCordX() {
		return cordX;
	}
	public void setCordX(double cordX) {
		this.cordX = cordX;
	}
	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	@Override
	public String toString() {
		return "EstacionDTOUsuario [nombre=" + nombre + ", puestosLibres=" + puestosLibres + ", postal=" + postal
				+ ", cordY=" + cordY + ", cordX=" + cordX + ", fechaAlta=" + fechaAlta + "]";
	}
	
	
	
}
