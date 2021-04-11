package pl.edu.pwr.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.edu.pwr.app.models.Role;
import pl.edu.pwr.app.models.Status;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.repositories.RoleRepository;
import pl.edu.pwr.app.repositories.UserRepository;
import pl.edu.pwr.app.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        User registeredUser = userRepository.save(user);
        log.info("Register - user: {} registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("getAll - {} users found", users.size());
        return users;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        log.info("findByEmail - user: {} found by email: {}", user, email);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("findById - no user found by id: {}", id);
            return null;
        }
        log.info("findById - user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("delete - user with id: {} deleted", id);
    }
}
