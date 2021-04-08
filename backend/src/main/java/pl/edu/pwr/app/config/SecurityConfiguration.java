package pl.edu.pwr.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import pl.edu.pwr.app.security.jwt.JwtConfig;
import pl.edu.pwr.app.security.jwt.JwtTokenGenerator;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenGenerator jwtTokenGenerator;

    private static final String ADMIN_ENDPOIND = "/auth/admin/**";
    private static final String LOGIN_ENDPOIND = "/auth/login";


    @Autowired
    public SecurityConfiguration(JwtTokenGenerator jwtTokenGenerator){
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOIND).permitAll()
                .antMatchers(ADMIN_ENDPOIND).hasRole("ADMIN")
                .anyRequest(). authenticated()
                .and()
                .apply(new JwtConfig(jwtTokenGenerator));



        ;
    }
}
