package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
