package estaciones.eventos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable{
	private static final long serialVersionUID = 1L;
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
