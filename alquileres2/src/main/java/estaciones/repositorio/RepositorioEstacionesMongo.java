package estaciones.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import estaciones.modelo.Estacionamiento;

public interface RepositorioEstacionesMongo extends RepositorioEstaciones, MongoRepository<Estacionamiento, String> {

}
