package alquileres.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import persistencia.jpa.UsuarioJPA;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioJPAUsuario extends RepositorioJPA<UsuarioJPA> {

	@Override
	public Class<UsuarioJPA> getClase() {
		return UsuarioJPA.class;
	}

	@Override
	public String getNombre() {
		return UsuarioJPA.class.getName().substring(UsuarioJPA.class.getName().lastIndexOf(".") + 1);
	}

	public UsuarioJPA getById(String keyword) throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			Query query = em.createQuery("SELECT u FROM UsuarioJPA u WHERE u.id LIKE :iD");
			query.setParameter("iD", "%" + keyword + "%");
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			if (query.getResultList().isEmpty())
				return null;

			return (UsuarioJPA) query.getSingleResult();
		} catch (RuntimeException ru) {
			throw new RepositorioException("Error buscando usuario por palabra clave", ru);
		}
	}

}
