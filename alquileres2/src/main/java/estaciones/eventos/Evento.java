package estaciones.eventos;

import java.time.LocalDateTime;

public class Evento {
	
	private String idBicicleta;
	private LocalDateTime fecha;
	
	public Evento(String idBicicleta, LocalDateTime fecha) {
		super();
		this.idBicicleta = idBicicleta;
		this.fecha = fecha;
	}
	
	public Evento() {}
	
	public String getIdBicicleta() {
		return this.idBicicleta;
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
