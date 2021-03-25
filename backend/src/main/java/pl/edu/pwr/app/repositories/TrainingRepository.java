package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.Training;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Long> {
}
