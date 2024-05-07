package estaciones.modelo;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creacion de nuevas estaciones")
public class NuevaEstacionDTO {
	@Schema(description = "Nombre de la estacion")
	@NotNull
	private String nombre;
	@Schema(description = "Numero de puestos")
	private int puestos;
	@Schema(description = "Codigo Postal")
	private String postal;
	@Schema(description = "Coordenada Y")
	private double cordY;
	@Schema(description = "Coordenada X")
	private double cordX;
	@Schema(description = "Fecha de alta")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime fechaAlta;

	public NuevaEstacionDTO(String nombre, int numPuestos, String postal,
			double cordY, double cordX,
			LocalDateTime fechaAlta) {
		this.nombre = nombre;
		this.puestos = numPuestos;
		this.postal = postal;
		this.cordY = cordY;
		this.cordX = cordX;
		this.fechaAlta = fechaAlta;
	}

	public int getPuestos() {
		return puestos;
	}

	public void setPuestos(int puestos) {
		this.puestos = puestos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumPuestos() {
		return puestos;
	}

	public void setNumPuestos(int numPuestos) {
		this.puestos = numPuestos;
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
		return "EstacionDTOUsuario [nombre=" + nombre + ", puestosLibres=" + puestos + ", postal=" + postal + ", cordY="
				+ cordY + ", cordX=" + cordX + ", fechaAlta=" + fechaAlta + "]";
	}
}
