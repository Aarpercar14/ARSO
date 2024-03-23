package alquileres.modelo;
import java.time.LocalDateTime;
import repositorio.Identificable;
public class Bicicleta implements Identificable {
	private String id;
	private String modelo;
	private LocalDateTime fechaAlta;
	private LocalDateTime fechaBaja;
	private String motivoBaja;
	private String estado;

	public Bicicleta(String id, String modelo) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.fechaAlta = LocalDateTime.now();
		this.fechaBaja = null;
		this.motivoBaja = null;
		this.estado="Disponible";
	}

	public Bicicleta() {
	}

	public void cambioEstadoBici(String estado) {
		this.setEstado(estado);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
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