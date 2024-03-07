package persistencia.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name="reservas")
public class ReservaJPA implements Identificable{
	@Id
	private String idBicicleta;
	@Column(name="creada",columnDefinition="TIMESTAMP")
	private LocalDateTime creada;
	@Column(name="caducidad",columnDefinition="TIMESTAMP")
	private LocalDateTime caducidad;
	@ManyToOne
	@JoinColumn(name="usuario_fk")
	private UsuarioJPA usuarioR;
	
	public ReservaJPA(String idBicicleta, LocalDateTime creada, LocalDateTime caducidad,String idUser) {
		super();
		this.idBicicleta = idBicicleta;
		this.creada = creada;
		this.caducidad = caducidad;
		this.usuarioR=new UsuarioJPA(idUser,new ArrayList<ReservaJPA>(),new ArrayList<AlquilerJPA>());
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public UsuarioJPA getUsuarioR() {
		return usuarioR;
	}

	public void setUsuarioR(UsuarioJPA usuarioR) {
		this.usuarioR = usuarioR;
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
