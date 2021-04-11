package pl.edu.pwr.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.exception.domain.EmailExistException;
import pl.edu.pwr.app.exception.domain.UserNotFoundException;
import pl.edu.pwr.app.exception.domain.UsernameExistException;
import pl.edu.pwr.app.jwt.JwtTokenGenerator;
import pl.edu.pwr.app.models.UserPrincipals;
import pl.edu.pwr.app.repositories.UserRepository;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.service.UserService;

import java.util.List;

import static pl.edu.pwr.app.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = { "/", "/user"})
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenGenerator jwtTokenGenerator;


    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenGenerator jwtTokenGenerator) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/find/{email}")
    public String getUser(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        return user.toString();
    }

    @PostMapping("/users")
    void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/testpage")
    public String test(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException {
        return "TEST";
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        getAuthenticaton(user.getEmail(), user.getPassword());
        User userLogin = userService.findUserByEmail(user.getEmail());
        UserPrincipals userP = new UserPrincipals(userLogin);
        HttpHeaders loginHeader = getHeaders(userP);
        return new ResponseEntity<>(userLogin, loginHeader, HttpStatus.OK);
    }



    private HttpHeaders getHeaders(UserPrincipals userP) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenGenerator.createToken(userP));
        return headers;

    }

    private void getAuthenticaton(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
