package alquileres.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import persistencia.jpa.ReservaJPA;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioJPAReservas extends RepositorioJPA<ReservaJPA>{

	@Override
	public Class<ReservaJPA> getClase() {
		return ReservaJPA.class;
	}

	@Override
	public String getNombre() {
		return ReservaJPA.class.getName().substring(ReservaJPA.class.getName().lastIndexOf(".")+1);
	}
	
	public ReservaJPA getById(String keyword) throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
		Query query = em.createNamedQuery("Repositorio.getById");
		query.setParameter("keyword", "%"+keyword+"%");
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		return (ReservaJPA)query.getResultList().get(0);
		}catch(RuntimeException ru) {
			throw new RepositorioException("Error buscando Repositorio por palabra clave", ru);
		}
	}

}