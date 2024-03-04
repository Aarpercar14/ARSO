package alquileres.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reservas")
public class Reserva {
	@Id
	private String idBicicleta;
	@Column(name="creada",columnDefinition="DATE")
	private LocalDateTime creada;
	@Column(name="caducidad",columnDefinition="DATE")
	private LocalDateTime caducidad;
	
	public Reserva(String idBicicleta, LocalDateTime creada, LocalDateTime caducidad) {
		this.idBicicleta = idBicicleta;
		this.creada = creada;
		this.caducidad = caducidad;
	}
	
	public Reserva() {}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}
	
	public LocalDateTime getCreada() {
		return creada;
	}

	public LocalDateTime getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(LocalDateTime caducidad) {
		this.caducidad = caducidad;
	}
	
	public boolean activa() {
		return !this.caducada();
	}	
	
	public boolean caducada() {
		return LocalDateTime.now().isAfter(caducidad);
	}
}
