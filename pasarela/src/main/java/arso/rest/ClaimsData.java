package arso.rest;

import java.util.HashMap;
import java.util.Map;

public class ClaimsData {
	private String id;
	private String nombre;
	private String rol;
	
	public ClaimsData() {};
	
	public String getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getRol() {
		return rol;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public Map<String, Object> getClaims() {
		Map<String, Object> map = new HashMap<>();
		map.put(id, this.id);
		map.put(nombre, this.nombre);
		map.put(rol, this.rol);
		return map;
	}
}
