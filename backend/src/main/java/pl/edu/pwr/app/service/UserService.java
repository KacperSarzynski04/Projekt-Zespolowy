package pl.edu.pwr.app.service;

import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.exception.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String email, String password) throws UserNotFoundException, UsernameExistException, EmailExistException;

    User findUserByEmail(String email);

    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    void deleteUser(String username) throws IOException;
}
