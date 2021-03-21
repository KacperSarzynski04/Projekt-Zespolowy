package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pwr.app.models.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
