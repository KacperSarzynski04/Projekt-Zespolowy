package pl.edu.pwr.app.service;

import pl.edu.pwr.app.models.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);

    void delete(Long id);
}
