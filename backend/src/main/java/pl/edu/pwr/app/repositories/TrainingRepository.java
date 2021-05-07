package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.models.User;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Long>, PagingAndSortingRepository<Training, Long> {
    Training findById(long id);
}
