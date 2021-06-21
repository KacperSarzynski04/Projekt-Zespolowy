/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.ProposalHost;
import pl.edu.pwr.app.models.ProposalVote;

@Repository
public interface ProposalVoteRepository extends CrudRepository<ProposalVote, Long> {
}
