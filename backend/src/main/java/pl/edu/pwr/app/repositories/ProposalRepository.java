/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.app.models.Proposal;
import pl.edu.pwr.app.models.ProposalHost;
import pl.edu.pwr.app.models.Training;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long> , PagingAndSortingRepository<Proposal, Long> {
}