package pruebas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import persistencia.jpa.AlquilerJPA;
import persistencia.jpa.ReservaJPA;
import persistencia.jpa.UsuarioJPA;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class PruebasJPA {
	public static void main(String[] args) {
		Repositorio<UsuarioJPA,String> repoUser=FactoriaRepositorios.getRepositorio(UsuarioJPA.class);
		Repositorio<ReservaJPA, String> se=FactoriaRepositorios.getRepositorio(ReservaJPA.class);
		Repositorio<AlquilerJPA, String> ae=FactoriaRepositorios.getRepositorio(AlquilerJPA.class);
		try {
			
			ArrayList<ReservaJPA> res= new ArrayList<ReservaJPA>();
			ArrayList<AlquilerJPA> alq=new ArrayList<AlquilerJPA>();
			/*
			ReservaJPA resi=new ReservaJPA("bici9", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30),"6");
			AlquilerJPA alqi=new AlquilerJPA("bici10", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30),"6");
			res.add(resi);
			alq.add(alqi);
			*/
			
			repoUser.add(new UsuarioJPA("7",res,alq));
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}