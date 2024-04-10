package alquileres.eventos;

import java.time.LocalDateTime;

public class Evento {
	private String idBicicleta;
	private LocalDateTime fecha;
	
	public Evento(String idBicicleta, LocalDateTime fecha) {
		this.idBicicleta = idBicicleta;
		this.fecha = fecha;
	}
	
	public Evento() {}
	
	public String getIdBicicleta() {
		return idBicicleta;
	}
	
	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
}
