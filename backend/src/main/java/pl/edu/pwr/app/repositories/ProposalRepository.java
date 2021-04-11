package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.Proposal;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long> {
}
