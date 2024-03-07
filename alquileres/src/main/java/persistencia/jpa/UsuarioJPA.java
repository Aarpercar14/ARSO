package persistencia.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name = "usuario")
@NamedQuery(name="Usuario.getById", query=" SELECT u FROM UsuarioJPA u WHERE u.id = :iD")
public class UsuarioJPA implements Identificable {
	@Id
	private String id;
	@OneToMany(mappedBy="usuarioR",cascade = CascadeType.ALL)
	private List<ReservaJPA> reservas;
	@OneToMany(mappedBy="usuarioA",cascade = CascadeType.ALL)
	private List<AlquilerJPA> alquileres;
	
	public UsuarioJPA(String id, List<ReservaJPA> arrayList, List<AlquilerJPA> arrayList2) {
		super();
		this.id = id;
		this.reservas = new ArrayList<ReservaJPA>(arrayList);
		this.alquileres = new ArrayList<AlquilerJPA>(arrayList2);
	}
	
	public UsuarioJPA() {

	}

	public void setReservas(List<ReservaJPA> reservas) {
		this.reservas = reservas;
	}

	public void setAlquileres(List<AlquilerJPA> alquileres) {
		this.alquileres = alquileres;
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