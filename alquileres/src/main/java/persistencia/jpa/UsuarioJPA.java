package persistencia.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name = "usuario")
public class UsuarioJPA implements Identificable {
	@Id
	private String id;
	@OneToMany(mappedBy="usuario")
	private List<ReservaJPA> reservas;
	@OneToMany(mappedBy="usuario")
	private List<AlquilerJPA> alquileres;
	
	public UsuarioJPA(String id, List<ReservaJPA> arrayList, List<AlquilerJPA> arrayList2) {
		super();
		this.id = id;
		this.reservas = new ArrayList<ReservaJPA>(arrayList);
		this.alquileres = new ArrayList<AlquilerJPA>(arrayList2);
	}
	
	public UsuarioJPA() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ReservaJPA> getReservas() {
		return reservas;
	}

	public void setReservas(ArrayList<ReservaJPA> reservas) {
		this.reservas = reservas;
	}

	public List<AlquilerJPA> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(ArrayList<AlquilerJPA> alquileres) {
		this.alquileres = alquileres;
	}
}
