package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.Proposal;
import pl.edu.pwr.app.models.ProposalHost;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long> , PagingAndSortingRepository<Proposal, Long> {
}