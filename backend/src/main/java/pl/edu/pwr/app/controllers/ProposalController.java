package pl.edu.pwr.app.controllers;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.AppApplication;
import pl.edu.pwr.app.models.Proposal;
import pl.edu.pwr.app.models.ProposalHost;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.repositories.ProposalHostRepository;
import pl.edu.pwr.app.repositories.ProposalRepository;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true")
@RequestMapping(path = { "/"})
public class ProposalController {
    private final ProposalRepository proposalRepository;
    private final ProposalHostRepository proposalHostRepository;
    public ProposalController(ProposalRepository proposalRepository, ProposalHostRepository proposalHostRepository) {
        this.proposalRepository = proposalRepository;
        this.proposalHostRepository = proposalHostRepository;
    }
    @GetMapping("/topics")
    public List<Proposal> getProposals(){
        List<Proposal> proposalList = (List<Proposal>) proposalRepository.findAll();
        Collections.sort(proposalList);
        return proposalList;
    }
    @PostMapping(path = "/topics", params = {"assignAsTrainer"})
    public void addProposal(@RequestBody Proposal proposal, @RequestParam("assignAsTrainer") boolean assign){
        ProposalHost proposalHost;
        proposalRepository.save(proposal);
        if(assign){
            proposalHost = new ProposalHost(proposal.getId(), proposal.getAuthorId(), proposal.getAuthorId());
        } else
            proposalHost = new ProposalHost(proposal.getId(), proposal.getAuthorId());


        proposalHostRepository.save(proposalHost);
    }
}