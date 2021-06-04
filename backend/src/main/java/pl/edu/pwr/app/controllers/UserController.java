package pl.edu.pwr.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import pl.edu.pwr.app.models.*;
import pl.edu.pwr.app.repositories.UserRepository;
import pl.edu.pwr.app.service.MailService;
import pl.edu.pwr.app.service.TokenBlackListService;
import pl.edu.pwr.app.service.UserService;
import pl.edu.pwr.app.service.impl.RandomString;
import pl.edu.pwr.app.service.impl.UserServiceImpl;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = { "/", "/user"})
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenGenerator jwtTokenGenerator;
    private TokenBlackListService tokenBlackListService;
    private MailService mailService;
    private UserServiceImpl userServiceImpl;
    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenGenerator jwtTokenGenerator, TokenBlackListService tokenBlackListService, MailService mailService, UserServiceImpl userServiceImpl) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.tokenBlackListService = tokenBlackListService;
        this.mailService = mailService;
        this.userServiceImpl = userServiceImpl;
    }

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User findUserID(int id){
        List<User> userList = (List<User>) userRepository.findAll();
        User resultUser = null;
        for (User user : userList) {
            if(user.getId()==id){
                resultUser=user;
                return resultUser;
            }
        }
        return resultUser;
    }

    @GetMapping(path = "/makeAdmin",params={"userId","admin"})
    public boolean makeAdmins(@RequestParam("userId") int userId,@RequestParam("admin") boolean admin){
        User user = findUserID(userId);
        if(admin){
            user.setRole("ROLE_ADMIN");
        }else{
            user.setRole("ROLE_USER");
        }
        userRepository.save(user);
        return true;
    }
    @GetMapping(path = "/checkAdmin",params={"userId"})
    public boolean checkAdmins(@RequestParam("userId") int userId){
        User user = findUserID(userId);
        String adminString = "ROLE_ADMIN";
        if(user.getRole().equals(adminString))
        {
            return true;
        }
        return false;
    }

    @GetMapping(path = "/send",params={"mail"})
    public boolean sendMail(@RequestParam("mail") String mail) throws MessagingException, UserNotFoundException, UsernameExistException, EmailExistException {
        RandomString randomString = new RandomString();
        String password = randomString.getAlphaNumericString(8);
        List<User> userList = (List<User>) userRepository.findAll();
        User user = userList.stream()
                .filter(customer -> mail.equals(customer.getEmail()))
                .findAny()
                .orElse(null);
        if(user==null){
            return false;
        }
        mailService.sendMail(mail,
                "Zmiana hasła",
                "Twoje nowe hasło to : "+password+". Masz 10 minut na ustawienie przy pomocy tego hasła nowego hasła dla swojego konta.", true);
        password = userServiceImpl.encodePassword(password);
        user.setPassword(password);
        userRepository.save(user);

        return true;
    }

    @GetMapping("/users")
    public Page<User> list(@RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> pageResult = userRepository.findAll(pageRequest);
        List<User> users = pageResult
                .stream()
                .map(User::new)
                .collect(toList());
        return new PageImpl<>(users, pageRequest, pageResult.getTotalElements());
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
        getAuthentication(user.getEmail(), user.getPassword());
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
        // blad
        tokenBlackListService.addTokenToBlacklist(token, tokenBlacklist);
        System.out.println("User logged out");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    private void getAuthentication(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
