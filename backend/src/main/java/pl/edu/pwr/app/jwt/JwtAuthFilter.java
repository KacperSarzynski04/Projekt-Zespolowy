package pl.edu.pwr.app.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.edu.pwr.app.constant.SecurityConstant;
import pl.edu.pwr.app.models.TokenBlackList;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.service.TokenBlackListService;
import pl.edu.pwr.app.service.impl.UserServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class JwtAuthFilter extends BasicAuthenticationFilter {

    @Value("jwt.secret.word")
    String secret;

    private final TokenBlackListService tokenService;
    private final UserServiceImpl userService;
    public JwtAuthFilter(AuthenticationManager authManager, TokenBlackListService tokenService, UserServiceImpl userService) {
        super(authManager);
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstant.JWT_TOKEN_HEADER);

        //this if ignores the Token
        final String uri = req.getRequestURI();
        if (header == null
                || !header.startsWith(SecurityConstant.TOKEN_PREFIX)
                || uri.equals("/login")
                || uri.equals("/logout")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req, res);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(SecurityConstant.JWT_TOKEN_HEADER);
        if (token != null && !isOnBlackList(token)) {
            // parse the token.
            String user;
            try {
                user = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                        .build()
                        .verify(token.replace(SecurityConstant.TOKEN_PREFIX, ""))
                        .getSubject();
                final User optionalEmail = userService.findUserByEmail(user);
                if (!optionalEmail.equals(null)) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            } catch (TokenExpiredException ignored) {}
            return null;
        }
        return null;
    }

    private boolean isOnBlackList(String token) {
        logger.info("Token " + token);
        token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        final Optional<TokenBlackList> bToken = tokenService.findToken(token);
        return bToken.isPresent();
    }
}