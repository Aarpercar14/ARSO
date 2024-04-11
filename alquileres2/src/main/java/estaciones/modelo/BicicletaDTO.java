package estaciones.modelo;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad bicicleta")

public class BicicletaDTO {
	@Schema(description="id de la bicicleta")
	@NotNull
	private String id;
	@Schema(description="Nombre del modelo")
	@NotNull
	private String modelo;
	@Schema(description="Estado")
	private String estado;
	
	public BicicletaDTO(String id, String modelo, String estado) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "BicicletaDTO [id=" + id + ", modelo=" + modelo + ", estado=" + estado + "]";
	}

}
