package estaciones.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import estaciones.modelo.Bicicleta;

public interface RepositorioBicicletasMongo extends RepositorioBicicletas, MongoRepository<Bicicleta, String> {

}
