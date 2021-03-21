package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pwr.app.models.User;

public interface UserRepository extends CrudRepository<User, Long> {}
