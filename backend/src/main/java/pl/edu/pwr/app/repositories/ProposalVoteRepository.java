package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.ProposalHost;
import pl.edu.pwr.app.models.ProposalVote;

@Repository
public interface ProposalVoteRepository extends CrudRepository<ProposalVote, Long> {
}
