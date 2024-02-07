package alquileres.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class Alquiler {
	private String idBicicleta;
	private LocalDateTime inicio;
	private LocalDateTime fin;

	public Alquiler(String idBicicleta, LocalDateTime inicio) {
		this.idBicicleta = idBicicleta;
		this.inicio = inicio;
		this.fin = null;
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
		return this.fin.equals(null);
	}

	public int tiempo() {
		if (this.activa()) {
			Duration duracion = Duration.between(inicio, LocalDateTime.now());
			int duracionMinutos = duracion.toMinutesPart();
			return duracionMinutos;
		}
		Duration duracion = Duration.between(inicio, fin);
		int duracionMinutos = duracion.toMinutesPart();
		return duracionMinutos;
	}

}
