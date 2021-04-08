package pl.edu.pwr.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.security.jwt.JwtUser;
import pl.edu.pwr.app.security.jwt.JwtUserFactory;
import pl.edu.pwr.app.service.UserService;

@Service
@Slf4j
public class JwtUserDetailService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.createJwtUser(user);
        log.info("");
        return jwtUser;
    }
}
