package alquileres.modelo;

import java.time.LocalDateTime;


public class Reserva {
	
	private String idBicicleta;
	private LocalDateTime creada;
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
		return caducidad.isBefore(LocalDateTime.now());
	}
}
