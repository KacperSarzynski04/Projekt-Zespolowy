package pl.edu.pwr.app.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.app.models.TokenBlackList;

import java.util.List;
import java.util.Optional;

@Service
public interface TokenBlackListService {
    List<TokenBlackList> findAllTokens();

    Optional<TokenBlackList> addToken(TokenBlackList token);

    Optional<TokenBlackList> findTokenById(long id);

    Optional<TokenBlackList> findToken(String token);

    void delete(TokenBlackList token);

    void update();
}