package pl.edu.pwr.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.BlackListJwtToken;

@Repository
public interface JwtTokenBlackListRepository extends JpaRepository<BlackListJwtToken, Long> {

    BlackListJwtToken findByToken(String token);

    BlackListJwtToken deleteAllByExpiryAtLessThan(long time);


}