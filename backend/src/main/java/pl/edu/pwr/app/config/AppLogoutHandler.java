package pl.edu.pwr.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.edu.pwr.app.constant.SecurityConstant;
import pl.edu.pwr.app.service.impl.TokenBlackListServiceImpl;
import com.auth0.jwt.JWT;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AppLogoutHandler implements LogoutHandler {
    @Autowired
    private TokenBlackListServiceImpl tokenService;

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        String token = req.getHeader(SecurityConstant.JWT_TOKEN_HEADER);

        if (token == null || !token.startsWith("Bearer ")) {
            sendError(res, "No Token to disable");
        } else {
            token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
            //if the token is expired
            if (JWT.decode(token).getExpiresAt().before(new Date())) {
                sendError(res, "Token is already expired");
            }
            tokenService.addToken(token);
            tokenService.update();
            res.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private void sendError(HttpServletResponse res, String message) {
        try {
            res.sendError(403, message);
        } catch (IOException ignore) {}
    }
}
