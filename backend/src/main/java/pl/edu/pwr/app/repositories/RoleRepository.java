package pl.edu.pwr.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.app.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
