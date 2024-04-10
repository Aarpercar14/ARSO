package estaciones.rest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import estaciones.modelo.Alquiler;
import estaciones.modelo.Reserva;
import repositorio.Identificable;

public class UsuarioDTO implements Identificable{
	private String id;
	private ArrayList<Reserva> reservas;
	private ArrayList<Alquiler> alquileres;
	
	public UsuarioDTO(String id) {
		this.id = id;
		this.reservas = new ArrayList<Reserva>();
		this.alquileres = new ArrayList<Alquiler>();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;	
	}
	
	public List<Reserva> getReservas() {
		return reservas;
	}
	
	public void setReservas(List<Reserva> reservas) {
		this.reservas = (ArrayList<Reserva>)reservas;
	}
	
	public List<Alquiler> getAlquileres() {
		return alquileres;
	}
	
	public void setAlquileres(List<Alquiler> alquileres) {
		this.alquileres = (ArrayList<Alquiler>) alquileres;
	}
	
	public int reservasCaducadas() {
		int count = 0;
		for (Reserva r : reservas)
			if(r.getCaducidad().isBefore(LocalDateTime.now()))
				count++;
		return count;
				
	}
	
	public int tiempoUsoHoy() {
		int minutos = 0;
		for (Alquiler a : alquileres)
			if(a.activa() || a.getFin().isEqual(LocalDateTime.now()))
				minutos += a.tiempo();
		return minutos;	
	}
	
	public int tiempoUsoSemana() {
		int minutos = 0;
		LocalDateTime semanaPasada = LocalDateTime.now().minus(1, ChronoUnit.WEEKS);
		for (Alquiler a : alquileres)
			if(a.activa() || a.getFin().isAfter(semanaPasada))
			minutos += a.tiempo();
		return minutos;
	}
	
	public boolean superaTiempo() {
		return (this.tiempoUsoHoy() >= 60 || this.tiempoUsoSemana() >= 180);
	}
	
	public Reserva reservaActiva() {
		if(reservas.get(reservas.size()-1).activa())
			return reservas.get(reservas.size()-1);
		return null;
	}
	
	public Alquiler alquilerActivo() {
		if(alquileres.get(alquileres.size()-1).activa())
			return alquileres.get(alquileres.size()-1);
		return null;
	}
	
	public boolean bloqueado() {
		return this.reservasCaducadas()>=3;
	}
	
	public void addReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public void addAlquiler(Alquiler alquiler) {
		this.alquileres.add(alquiler);
	}
	
	public void eliminarCaducadas() {
		for (Reserva r : reservas) {
			if (r.caducada())
				reservas.remove(r);
		}
	}
}
