package persistencia.jpa;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "usuario_reserva", 
			   joinColumns = @JoinColumn(name = "usuario_fk"), 
			   inverseJoinColumns = @JoinColumn(name = "reserva_fk"))
	private ArrayList<ReservasJPA> reservas;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "usuario_alquiler", 
			   joinColumns = @JoinColumn(name = "usuario_fk"),
			   inverseJoinColumns = @JoinColumn(name = "alquiler_fk"))
	private ArrayList<AlquilerJPA> alquileres;
	@Column(name = "reservasCaducadas")
	private int reservasCaducadas;
	@Column(name = "superaTiempo")
	private boolean superaTiempo;
	@Column(name = "bloqueado")
	private boolean bloqueado;

	public UsuarioJPA(String id, int reservasCaducadas, boolean superaTiempo, boolean bloqueado) {
		super();
		this.id = id;
		this.reservas = new ArrayList<ReservasJPA>();
		this.alquileres = new ArrayList<AlquilerJPA>();
		this.reservasCaducadas = reservasCaducadas;
		this.superaTiempo = superaTiempo;
		this.bloqueado = bloqueado;
	}

	public UsuarioJPA() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<ReservasJPA> getReservas() {
		return reservas;
	}

	public void setReservas(ArrayList<ReservasJPA> reservas) {
		this.reservas = reservas;
	}

	public ArrayList<AlquilerJPA> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(ArrayList<AlquilerJPA> alquileres) {
		this.alquileres = alquileres;
	}

	public int getReservasCaducadas() {
		return reservasCaducadas;
	}

	public void setReservasCaducadas(int reservasCaducadas) {
		this.reservasCaducadas = reservasCaducadas;
	}

	public boolean isSuperaTiempo() {
		return superaTiempo;
	}

	public void setSuperaTiempo(boolean superaTiempo) {
		this.superaTiempo = superaTiempo;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

}
