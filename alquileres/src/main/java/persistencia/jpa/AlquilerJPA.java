package persistencia.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name="alquiler")
public class AlquilerJPA implements Identificable{
	@Column(name="idBicicleta")
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

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

}
