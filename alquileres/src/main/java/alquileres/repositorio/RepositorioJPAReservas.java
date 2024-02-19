package alquileres.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import persistencia.jpa.ReservasJPA;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioJPAReservas extends RepositorioJPA<ReservasJPA>{

	@Override
	public Class<ReservasJPA> getClase() {
		return ReservasJPA.class;
	}

	@Override
	public String getNombre() {
		return ReservasJPA.class.getName().substring(ReservasJPA.class.getName().lastIndexOf(".")+1);
	}
	
	public ReservasJPA getById(String keyword) throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
		Query query = em.createNamedQuery("Usuario.getById");
		query.setParameter("keyword", "%"+keyword+"%");
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		return (ReservasJPA)query.getResultList().get(0);
		}catch(RuntimeException ru) {
			throw new RepositorioException("Error buscando bicicletas por palabra clave", ru);
		}
	}

}