/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.BlackListJwtToken;

@Repository
public interface JwtTokenBlackListRepository extends JpaRepository<BlackListJwtToken, Long> {

    BlackListJwtToken findByToken(String token);

    BlackListJwtToken deleteAllByExpiryAtLessThan(long time);


}