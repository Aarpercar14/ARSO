package persistencia.jpa;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alquileres.modelo.Alquiler;
import alquileres.modelo.Reserva;

@Entity
@Table(name="usuario")
public class UsuarioJPA {
	@Column(name="id")
	private String id;
	@Column(name="reservas")
	private ArrayList<Reserva> reservas;
	@Column(name="alquileres")
	private ArrayList<Alquiler> alquileres;
	@Column(name="reservasCaducadas")
	private int reservasCaducadas;
	@Column(name="superaTiempo")
	private boolean superaTiempo;
	@Column(name="bloqueado")
	private boolean bloqueado;
	
	public UsuarioJPA() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}

	public ArrayList<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(ArrayList<Alquiler> alquileres) {
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
