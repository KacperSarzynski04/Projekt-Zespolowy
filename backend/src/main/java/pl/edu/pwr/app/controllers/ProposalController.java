package pl.edu.pwr.app.controllers;

import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.models.Proposal;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.repositories.ProposalRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProposalController {
    private final ProposalRepository proposalRepository;

    public ProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }
    @GetMapping("/proposals")
    public List<Proposal> getProposals(){
        List<Proposal> proposalList = (List<Proposal>) proposalRepository.findAll();
        Collections.sort(proposalList);
        return proposalList;
    }
    @PostMapping("/proposals")
    public void addProposal(@RequestBody Proposal proposal){
        proposalRepository.save(proposal);

    }
}
