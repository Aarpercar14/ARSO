package persistencia.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name="alquileres")
public class AlquilerJPA implements Identificable{
	@Id
	private String idBicicleta;
	@Column(name="inicio",columnDefinition="DATE")
	private LocalDateTime inicio;
	@Column(name="fin",columnDefinition="DATE")
	private LocalDateTime fin;
	
	public AlquilerJPA(String idBicicleta, LocalDateTime inicio, LocalDateTime fin) {
		super();
		this.idBicicleta = idBicicleta;
		this.inicio = inicio;
		this.fin = fin;
	}

	public AlquilerJPA() {
	}

	@Override
	public String getId() {
		return idBicicleta;
	}

	@Override
	public void setId(String idBicicleta) {
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
