package estaciones.repositorio;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones.modelo.Bicicleta;

@NoRepositoryBean
public interface RepositorioBicicletas
    extends PagingAndSortingRepository<Bicicleta, String> {

	List<Bicicleta> findByModelo(String modelo);

}