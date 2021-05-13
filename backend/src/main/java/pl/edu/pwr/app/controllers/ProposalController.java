package pl.edu.pwr.app.controllers;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.AppApplication;
import pl.edu.pwr.app.models.Proposal;
import pl.edu.pwr.app.models.ProposalHost;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.repositories.ProposalHostRepository;
import pl.edu.pwr.app.repositories.ProposalRepository;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    /*
    @GetMapping("/topics")
    public List<Proposal> getProposals(){
        List<Proposal> proposalList = (List<Proposal>) proposalRepository.findAll();
        Collections.sort(proposalList);
        return proposalList;
    }
    */
    @GetMapping("/topics")
    public Page<Proposal> list(@RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Proposal> pageResult = proposalRepository.findAll(pageRequest);
        List<Proposal> proposals = pageResult
                .stream()
                .map(Proposal::new)
                .collect(toList());
        return new PageImpl<>(proposals, pageRequest, pageResult.getTotalElements());
    }
    @GetMapping(path = "/assigned", params ={"userId","proposalId"})
    public boolean checkVisibleAssignButton(@RequestParam("userId") String userId, @RequestParam("proposalId") String proposalId){
        List<ProposalHost> proposalHosts= (List<ProposalHost>) proposalHostRepository.findAll();
        for (ProposalHost proposalHost:proposalHosts) {
            if (proposalHost.getProposalID() == Long.parseLong(proposalId) && proposalHost.getHostID() == Long.parseLong(userId)) {
                return false;
            }
        }
        return true;
    }
    @GetMapping(path = "/voted", params ={"userId","proposalId"})
    public boolean checkVisibleVoteButton(@RequestParam("userId") String userId, @RequestParam("proposalId") String proposalId){
        List<ProposalHost> proposalHosts= (List<ProposalHost>) proposalHostRepository.findAll();
        for (ProposalHost proposalHost:proposalHosts) {
            if (proposalHost.getProposalID() == Long.parseLong(proposalId) && proposalHost.getVotedUserID() == Long.parseLong(userId)) {
                return false;
            }
        }
        return true;
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