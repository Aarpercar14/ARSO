package alquileres.modelo;

import java.time.Duration;
import java.time.LocalDateTime;


import javax.persistence.Id;

public class Alquiler {
	private String idBicicleta;
	private LocalDateTime inicio;
	private LocalDateTime fin;

	public Alquiler(String idBicicleta, LocalDateTime inicio) {
		this.idBicicleta = idBicicleta;
		this.inicio = inicio;
		this.fin = null;
	}

	public Alquiler() {
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void finalizar(LocalDateTime fin) {
		this.fin = fin;
	}

	public boolean activa() {
		return this.fin == null;
	}

	public int tiempo() {
		if (this.activa()) {
			Duration duracion = Duration.between(inicio, LocalDateTime.now());
			int duracionMinutos = (int) duracion.toMinutes();
			return duracionMinutos;
		}
		Duration duracion = Duration.between(inicio, fin);
		int duracionMinutos = (int) duracion.toMinutes();
		return duracionMinutos;
	}

}
