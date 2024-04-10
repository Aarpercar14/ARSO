package alquileres.eventos;

import java.time.LocalDateTime;

public class EventoAlquilerConcluido {
	
	private String idBicicleta;
	private LocalDateTime fecha;
	
	public EventoAlquilerConcluido(String idBicicleta, LocalDateTime fecha) {
		super();
		this.idBicicleta = idBicicleta;
		this.fecha = fecha;
	}
	
	public EventoAlquilerConcluido() {}
	
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
