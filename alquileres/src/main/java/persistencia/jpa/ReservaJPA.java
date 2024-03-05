package persistencia.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name="reservas")
public class ReservaJPA implements Identificable{
	@Id
	private String idBicicleta;
	@Column(name="creada",columnDefinition="DATE")
	private LocalDateTime creada;
	@Column(name="caducidad",columnDefinition="DATE")
	private LocalDateTime caducidad;
	
	public ReservaJPA(String idBicicleta, LocalDateTime creada, LocalDateTime caducidad) {
		super();
		this.idBicicleta = idBicicleta;
		this.creada = creada;
		this.caducidad = caducidad;
	}

	public ReservaJPA() {}

	@Override
	public String getId() {
		return idBicicleta;
	}

	@Override
	public void setId(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public LocalDateTime getCreada() {
		return creada;
	}

	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}

	public LocalDateTime getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(LocalDateTime caducidad) {
		this.caducidad = caducidad;
	}

}
