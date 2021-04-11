package pl.edu.pwr.app.service.impl;

import static pl.edu.pwr.app.constant.Authorities.*;

import pl.edu.pwr.app.constant.SecurityConstant;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.models.Role;
import pl.edu.pwr.app.models.UserPrincipals;
import pl.edu.pwr.app.exception.domain.*;
import pl.edu.pwr.app.repositories.UserRepository;

import pl.edu.pwr.app.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.*;
import static pl.edu.pwr.app.models.Role.ROLE_USER;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        //this.loginAttemptService = loginAttemptService;
        //this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.error("User not found : " + email);
            throw new UsernameNotFoundException("User not found :" + email);
        } else {
            //validateLoginAttempt(user);
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipals userPrincipal = new UserPrincipals(user);
            logger.info("Found by email : " + email);
            return userPrincipal;
        }
    }

    @Override
    public User register(String firstName, String lastName, String email, String password) throws UserNotFoundException, UsernameExistException, EmailExistException {
        //validateNewUsernameAndEmail(email);
        User user = new User();
        //user.setUserId(generateUserId());
        //String password = generatePassword();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        userRepository.save(user);
        logger.info("New user password: " + password);
        //emailService.sendNewPasswordEmail(firstName, password, email);
        return user;
    }


//    @Override
//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        return null;
    }

    @Override
    public void deleteUser(String username) throws IOException {

    }


    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }


    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }



    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewEmail = findUserByEmail(newEmail);

        if (userByNewEmail != null) {
            throw new EmailExistException("user exists");
        }
        return userByNewEmail;
    }


}
