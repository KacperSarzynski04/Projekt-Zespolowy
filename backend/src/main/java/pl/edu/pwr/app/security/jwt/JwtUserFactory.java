package pl.edu.pwr.app.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.edu.pwr.app.models.Role;
import pl.edu.pwr.app.models.Status;
import pl.edu.pwr.app.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {

    }

    public static JwtUser createJwtUser(User user){
        return new JwtUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPassword(), user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(), grantedAuthMap(new ArrayList<>(user.getRoles())));
    }
    private static List<GrantedAuthority> grantedAuthMap(List<Role> userRoles) {
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
