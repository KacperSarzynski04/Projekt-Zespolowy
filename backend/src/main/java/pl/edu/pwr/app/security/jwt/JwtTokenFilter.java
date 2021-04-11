package pl.edu.pwr.app.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenGenerator jwtTokenGenerator;

    public JwtTokenFilter(JwtTokenGenerator jwtTokenGenerator){
        this.jwtTokenGenerator = jwtTokenGenerator;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        String token = jwtTokenGenerator.resolveToken((HttpServletRequest) servletRequest);
        if (token != null && jwtTokenGenerator.validateToken(token)) {
            Authentication auth = jwtTokenGenerator.getAuth(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
