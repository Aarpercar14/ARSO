package alquileres.modelo;

import java.util.List;

public class Historial {
	private String idUsuario;
	private boolean bloqueado;
	private int tiempoUso;
	private List<Reserva> reservas;
	private List<Alquiler> alquileres;
	
	public Historial(String idUsuario, boolean bloqueado, int tiempoUso, List<Reserva> reservas, List<Alquiler> alquileres) {
		this.idUsuario = idUsuario;
		this.bloqueado = bloqueado;
		this.tiempoUso = tiempoUso;
		this.reservas = reservas;
		this.alquileres = alquileres;
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public void addAlquiler(Alquiler alquiler) {
		this.alquileres.add(alquiler);
	}
	
	public List<Reserva> getReservas() {
		return reservas;
	}
	
	public List<Alquiler> getAlquileres() {
		return alquileres;
	}
	
	public boolean getBloqueado() {
		return bloqueado;
	}
	
	public int getTiempoUso() {
		return tiempoUso;
	}
}
