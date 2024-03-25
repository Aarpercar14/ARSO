package alquileres.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.Document;

import repositorio.Identificable;
import repositorio.RepositorioException;
@Document(collection = "estacionamiento")
public class Estacionamiento implements Identificable {
	private String id;
	private String nombre;
	private int numPuestos;
	private String postal;
	private double cordY;
	private double cordX;
	private LocalDateTime fechaAlta;
	private ArrayList<Bicicleta> bicicletas;

	public Estacionamiento(String nombre, int numPuesto, String postal, double x, double y) {
		this.nombre = nombre;
		this.numPuestos = numPuesto;
		this.cordX = x;
		this.cordY = y;
		this.fechaAlta = LocalDateTime.now();
		this.bicicletas = new ArrayList<Bicicleta>();
	}

	public Estacionamiento() {

	}

	public void estacionarBici(Bicicleta bici) throws RepositorioException {
		if (bicicletas == null) {
			bicicletas = new ArrayList<Bicicleta>();
		}
		bicicletas.add(bici);
		numPuestos--;
	}

	public void sacarBici(String idBici) {
		for (Bicicleta b : bicicletas) {
			if (b.getId().equals(idBici)) {
				bicicletas.remove(b);
				break;
			}
		}
		numPuestos++;
	}
	
	public List<Bicicleta> findDisponibles(){
		return bicicletas.stream().filter(b->b.getEstado()!="Disponible").collect(Collectors.toList());
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public ArrayList<Bicicleta> getBicicletas() {
		return bicicletas;
	}

	public boolean haySitioLibre() {
		return (this.numPuestos > 0);
	}

	public Bicicleta getBicicleta(String idBici) {
		for (Bicicleta b : bicicletas) {
			if (b.getId().equals(idBici))
				return b;
		}
		return null;
	}

	public double getCordX() {
		return cordX;
	}

	public void setCordX(double cord) {
		this.cordX = cord;
	}

	public double getCordY() {
		return cordY;
	}

	public void setCordY(double cord) {
		this.cordY = cord;
	}

	@Override
	public String toString() {
		return "Estacionamiento [id=" + id + ", nombre=" + nombre + ", numPuestos=" + numPuestos + ", postal=" + postal
				+ ", cordY=" + cordY + ", cordX=" + cordX + ", fechaAlta=" + fechaAlta + "]";
	}
}