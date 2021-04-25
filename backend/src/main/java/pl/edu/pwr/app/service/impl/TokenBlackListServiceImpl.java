package pl.edu.pwr.app.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import pl.edu.pwr.app.models.TokenBlackList;
import pl.edu.pwr.app.repositories.TokenBlackListRepository;
import pl.edu.pwr.app.service.TokenBlackListService;
import com.auth0.jwt.JWT;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TokenBlackListServiceImpl implements TokenBlackListService {
    private final TokenBlackListRepository repository;

    public TokenBlackListServiceImpl(TokenBlackListRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TokenBlackList> findAllTokens() {
        return repository.findAll();
    }

    @Override
    public Optional<TokenBlackList> addToken(TokenBlackList token) {
        if (findToken(token.getToken()).isPresent())
            return Optional.empty();
        final TokenBlackList save = repository.save(token);
        return Optional.of(save);

    }

    @Override
    public Optional<TokenBlackList> findTokenById(long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<TokenBlackList> findToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public void delete(TokenBlackList token) {
        final Optional<TokenBlackList> byId = repository.findById(token.getUuid());
        if (byId.isPresent()) {
            repository.delete(token);
        }
    }

    @Override
    public void update() {
        //the idea is that we remove expired tokens from the DB
        final List<TokenBlackList> allTokens = findAllTokens();
        for (TokenBlackList t : allTokens) {
            final DecodedJWT decode = JWT.decode(t.getToken());
            if (decode.getExpiresAt().before(new Date())) {
                delete(t);
            }
        }
    }

    public Optional<TokenBlackList> addToken(String token) {
        return addToken(new TokenBlackList(token));
    }
}
