package persistencia.jpa;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name="reservas")
public class ReservasJPA implements Identificable{
	@Column(name="id")
	private String idBicicletas;
	@Column(name="creada",columnDefinition="DATE")
	private LocalDateTime creada;
	@Column(name="caducidad",columnDefinition="DATE")
	private LocalDateTime caducidad;
	
	public ReservasJPA(String idBicicletas, LocalDateTime creada, LocalDateTime caducidad) {
		super();
		this.idBicicletas = idBicicletas;
		this.creada = creada;
		this.caducidad = caducidad;
	}

	public ReservasJPA() {
		// TODO Auto-generated constructor stub
	}

	public String getIdBicicletas() {
		return idBicicletas;
	}

	public void setIdBicicletas(String idBicicletas) {
		this.idBicicletas = idBicicletas;
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
