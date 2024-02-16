package persistencia.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="alquiler")
public class alquilerJPA {
	@Column(name="idBicicleta")
	private String idBicicleta;
	@Column(name="inicio",columnDefinition="DATE")
	private LocalDateTime inicio;
	@Column(name="fin",columnDefinition="DATE")
	private LocalDateTime fin;
	
	public alquilerJPA() {
		// TODO Auto-generated constructor stub
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

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

}
