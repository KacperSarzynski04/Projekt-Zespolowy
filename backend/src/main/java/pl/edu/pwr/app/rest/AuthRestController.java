package pl.edu.pwr.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.app.dto.AuthenticationRequestDto;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.security.jwt.JwtTokenGenerator;
import pl.edu.pwr.app.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth/")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenGenerator jwtTokenGenerator;

    private final UserService userService;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, JwtTokenGenerator jwtTokenGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            User user = userService.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + email + " not found");
            }

            String token = jwtTokenGenerator.createToken(email, user.getRoles());

            Map<Object, Object> response = new HashMap<>();

            // cors headers
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Access-Control-Allow-Origin", "http://localhost:4200");
            responseHeaders.add("Access-Control-Allow-Credentials", "true");

            response.put("email", email);
            response.put("token", token);

            return ResponseEntity.ok().headers(responseHeaders).body(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
