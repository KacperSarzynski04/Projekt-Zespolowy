package pl.edu.pwr.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.TokenBlackList;

import java.util.Optional;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
    Optional<TokenBlackList> findByToken(String token);
}