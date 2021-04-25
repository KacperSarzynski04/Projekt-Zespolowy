package pl.edu.pwr.app.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.edu.pwr.app.models.TokenBlackList;
import pl.edu.pwr.app.service.impl.TokenBlackListServiceImpl;

import static pl.edu.pwr.app.constant.SecurityConstant.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenGenerator jwtTokenGenerator;


    public JwtFilter(JwtTokenGenerator jwtTokenGenerator, TokenBlackListServiceImpl tokenBlackListService) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authHeader.substring(TOKEN_PREFIX.length());
            String email = jwtTokenGenerator.getSubject(token);
            System.out.println("Token");
            if (jwtTokenGenerator.isTokenValid(email, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtTokenGenerator.getAuthorities(token);
                Authentication authentication = jwtTokenGenerator.getAuthentication(email, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);

    }
}
