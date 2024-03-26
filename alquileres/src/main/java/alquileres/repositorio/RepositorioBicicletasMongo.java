package alquileres.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import alquileres.modelo.Bicicleta;

public interface RepositorioBicicletasMongo extends RepositorioBicicletas, MongoRepository<Bicicleta, String> {

}
