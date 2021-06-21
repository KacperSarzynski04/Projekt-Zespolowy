/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.edu.pwr.app.repositories.JwtTokenBlackListRepository;

import static pl.edu.pwr.app.constant.SecurityConstant.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenGenerator jwtTokenGenerator;
    private final JwtTokenBlackListRepository blackListRepository;

    public JwtFilter(JwtTokenGenerator jwtTokenGenerator, JwtTokenBlackListRepository blackListRepository) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.blackListRepository = blackListRepository;
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
            if (blackListRepository.findByToken(token) != null) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                filterChain.doFilter(request, response);
                return;
            }
            String email = jwtTokenGenerator.getSubject(token);
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

    public static String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        return authHeader.substring(7);
    }
}
