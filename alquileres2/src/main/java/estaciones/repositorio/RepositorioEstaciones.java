package estaciones.repositorio;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones.modelo.Estacionamiento;

@NoRepositoryBean
public interface RepositorioEstaciones
    extends PagingAndSortingRepository<Estacionamiento, String> {

	List<Estacionamiento> findByNombre(String nombre);

}