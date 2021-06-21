/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.service;
import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;
import pl.edu.pwr.app.jwt.JwtFilter;
import pl.edu.pwr.app.models.BlackListJwtToken;
import pl.edu.pwr.app.repositories.JwtTokenBlackListRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenBlackListService {

    private final JwtTokenBlackListRepository blacklistRepository;


    public TokenBlackListService(JwtTokenBlackListRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }


    public void addTokenToBlacklist(String token, BlackListJwtToken tokenBlacklist){
        long expirationTime = JWT.decode(token).getExpiresAt().getTime();
        tokenBlacklist.setToken(token);
        tokenBlacklist.setExpiryAt(expirationTime);
        getBlacklistRepository().save(tokenBlacklist);
    }

    public BlackListJwtToken blacklistCheck(HttpServletRequest request){
        return getBlacklistRepository().findByToken(JwtFilter.getToken(request));
    }
    public JwtTokenBlackListRepository getBlacklistRepository() {
        return blacklistRepository;
    }
}
