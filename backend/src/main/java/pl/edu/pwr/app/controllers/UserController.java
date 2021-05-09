package pl.edu.pwr.app.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.exception.domain.EmailExistException;
import pl.edu.pwr.app.exception.domain.UserNotFoundException;
import pl.edu.pwr.app.exception.domain.UsernameExistException;
import pl.edu.pwr.app.jwt.JwtFilter;
import pl.edu.pwr.app.jwt.JwtTokenGenerator;
import pl.edu.pwr.app.models.BlackListJwtToken;
import pl.edu.pwr.app.models.UserPrincipals;
import pl.edu.pwr.app.repositories.UserRepository;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.service.TokenBlackListService;
import pl.edu.pwr.app.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = { "/", "/user"})
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenGenerator jwtTokenGenerator;
    private TokenBlackListService tokenBlackListService;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenGenerator jwtTokenGenerator, TokenBlackListService tokenBlackListService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.tokenBlackListService = tokenBlackListService;
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
        headers.add("Jwt-Token", jwtTokenGenerator.createToken(userP));
        return headers;

    }

    //@ApiOperation(value = "", authorizations = { @Authorization(value="Bearer ") })
    @RequestMapping(value = "logout",  method = RequestMethod.POST)
    public ResponseEntity<Void> logoutUser(BlackListJwtToken tokenBlacklist, HttpServletRequest request, Authentication authentication) throws ServletException {
        String token = JwtFilter.getToken(request);
        tokenBlackListService.addTokenToBlacklist(token, tokenBlacklist);
        System.out.println("User logged out");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void getAuthenticaton(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
