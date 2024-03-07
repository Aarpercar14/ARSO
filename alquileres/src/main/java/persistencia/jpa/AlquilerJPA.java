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
@Table(name="alquileres")
public class AlquilerJPA implements Identificable{
	@Id
	private String idBicicleta;
	@Column(name="inicio",columnDefinition="DATE")
	private LocalDateTime inicio;
	@Column(name="fin",columnDefinition="DATE")
	private LocalDateTime fin;
	@ManyToOne
	@JoinColumn(name="usuario_fk")
	private UsuarioJPA usuarioA;
	
	public AlquilerJPA(String idBicicleta, LocalDateTime inicio, LocalDateTime fin,String idUser) {
		super();
		this.idBicicleta = idBicicleta;
		this.inicio = inicio;
		this.fin = fin;
		this.usuarioA=new UsuarioJPA(idUser,new ArrayList<ReservaJPA>(),new ArrayList<AlquilerJPA>());

	}

	public AlquilerJPA() {
	}

	@Override
	public String getId() {
		return idBicicleta;
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
