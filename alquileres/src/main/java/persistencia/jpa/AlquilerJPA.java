package persistencia.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name = "alquileres")
public class AlquilerJPA implements Identificable {

	@Id
	private String id;
	@Column(name = "idBici")
	private String idBicicleta;
	@Column(name = "inicio", columnDefinition = "TIMESTAMP")
	private LocalDateTime inicio;
	@Column(name = "fin", columnDefinition = "TIMESTAMP")
	private LocalDateTime fin;
	@ManyToOne
	@JoinColumn(name = "usuario_fk")
	private UsuarioJPA usuarioA;

	public AlquilerJPA(String idBicicleta, LocalDateTime inicio) {
		super();
		this.idBicicleta = idBicicleta;
		this.inicio = inicio;
		this.fin = inicio.minusMinutes(30);
		this.usuarioA = null;
	}

	public AlquilerJPA() {
	}

	@PrePersist
	private void ensureId() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public UsuarioJPA getUsuarioA() {
		return usuarioA;
	}

	public void setUsuarioA(UsuarioJPA usuarioA) {
		this.usuarioA = usuarioA;
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
