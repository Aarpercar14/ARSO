package persistencia.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;
import repositorio.Identificable;

@Entity
@Table(name = "usuario")
public class UsuarioJPA implements Identificable {
	@Id
	private String id;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "usuario_reserva", 
			   joinColumns = @JoinColumn(name = "usuario_fk"), 
			   inverseJoinColumns = @JoinColumn(name = "reserva_fk"))
	private List<Reserva> reservas;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "usuario_alquiler", 
			   joinColumns = @JoinColumn(name = "usuario_fk"),
			   inverseJoinColumns = @JoinColumn(name = "alquiler_fk"))
	private List<Alquiler> alquileres;
	
	public UsuarioJPA(String id, List<Reserva> reservas, List<Alquiler> alquileres) {
		super();
		this.id = id;
		this.reservas = new ArrayList<Reserva>(reservas);
		this.alquileres = new ArrayList<Alquiler>(alquileres);
	}
	
	public UsuarioJPA() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(ArrayList<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}
}
