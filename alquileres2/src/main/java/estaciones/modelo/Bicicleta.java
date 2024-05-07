package estaciones.modelo;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import repositorio.Identificable;

@Document(collection = "bicicletas")
public class Bicicleta implements Identificable {
	@Id
	private ObjectId id;
	private String modelo;
	private LocalDateTime fechaAlta;
	private LocalDateTime fechaBaja;
	private String motivoBaja;
	private String estado;

	public Bicicleta(String modelo) {
		super();
		this.modelo = modelo;
		this.fechaAlta = LocalDateTime.now();
		this.fechaBaja = null;
		this.motivoBaja = null;
		this.estado="disponible";
	}

	public Bicicleta() {
	}

	public void cambioEstadoBici(String estado) {
		this.setEstado(estado);
	}

	@Override
	public String getId() {
		return id.toString();
	}

	@Override
	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public String getModelo() {
		return modelo;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public String getEstado() {
		return estado;
	}

	private void setEstado(String estado) {
		this.estado = estado;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public void setFechaBaja(LocalDateTime localDateTime) {
		this.fechaBaja = localDateTime;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public String toString() {
		return "Bicicleta{" + "id='" + id + '\'' + ", modelo='" + modelo + '\'' + ", fechaAlta=" + fechaAlta
				+ ", fechaBaja=" + fechaBaja + ", motivoBaja='" + motivoBaja + '\'' + ", estado='" + estado + '\''
				+ '\'' + '}';
	}
}