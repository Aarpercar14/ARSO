package alquileres.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import persistencia.jpa.AlquilerJPA;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioJPAAlquileres extends RepositorioJPA<AlquilerJPA>{

	@Override
	public Class<AlquilerJPA> getClase() {
		return AlquilerJPA.class;
	}

	@Override
	public String getNombre() {
		return AlquilerJPA.class.getName().substring(AlquilerJPA.class.getName().lastIndexOf(".")+1);
	}
	
	public AlquilerJPA getById(String keyword) throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
		Query query = em.createNamedQuery("Alquileres.getById");
		query.setParameter("keyword", "%"+keyword+"%");
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		return (AlquilerJPA)query.getResultList().get(0);
		}catch(RuntimeException ru) {
			throw new RepositorioException("Error buscando bicicletas por palabra clave", ru);
		}
	}

}