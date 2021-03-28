package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
 