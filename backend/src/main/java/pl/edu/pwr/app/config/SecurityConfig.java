package pl.edu.pwr.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.edu.pwr.app.constant.SecurityConstant;
import pl.edu.pwr.app.jwt.JwtAccessError;
import pl.edu.pwr.app.jwt.JwtAuthEntry;
import pl.edu.pwr.app.jwt.JwtAuthFilter;
import pl.edu.pwr.app.jwt.JwtFilter;
import pl.edu.pwr.app.service.TokenBlackListService;
import pl.edu.pwr.app.service.UserService;
import pl.edu.pwr.app.service.impl.TokenBlackListServiceImpl;
import pl.edu.pwr.app.service.impl.UserServiceImpl;
import static pl.edu.pwr.app.constant.SecurityConstant.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtFilter jwtFilter;
    private JwtAccessError jwtAccessDeniedHandler;
    private JwtAuthEntry jwtAuthEntry;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenBlackListServiceImpl tokenBlackListService;
    private UserServiceImpl userService;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter,
                          JwtAccessError jwtAccessDeniedHandler,
                          JwtAuthEntry jwtAuthEntry,
                          @Qualifier("UserDetailsService")UserDetailsService userDetailsService,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          TokenBlackListServiceImpl tokenBlackListService,
                          UserServiceImpl userService) {
        this.jwtFilter = jwtFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthEntry = jwtAuthEntry;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenBlackListService = tokenBlackListService;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthEntry)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    LogoutHandler logoutHandler() {
        return new AppLogoutHandler();
    }
}
