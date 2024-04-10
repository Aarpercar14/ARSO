package alquileres.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import alquileres.modelo.Estacionamiento;

public interface RepositorioEstacionesMongo extends RepositorioEstaciones, MongoRepository<Estacionamiento, String> {

}
