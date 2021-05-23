package pl.edu.pwr.app.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.models.Proposal;
import pl.edu.pwr.app.models.ProposalHost;
import pl.edu.pwr.app.models.Training;
import pl.edu.pwr.app.models.User;
import pl.edu.pwr.app.repositories.ProposalHostRepository;
import pl.edu.pwr.app.repositories.ProposalRepository;
import pl.edu.pwr.app.repositories.TrainingRepository;
import pl.edu.pwr.app.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true")
@RequestMapping(path = { "/"})
public class ProposalController {
    private final ProposalRepository proposalRepository;
    private final ProposalHostRepository proposalHostRepository;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    public ProposalController(ProposalRepository proposalRepository, ProposalHostRepository proposalHostRepository, UserRepository userRepository, TrainingRepository trainingRepository) {
        this.proposalRepository = proposalRepository;
        this.proposalHostRepository = proposalHostRepository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
    }
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
    @GetMapping(path = "/assign", params ={"userId","proposalId"})
    public void assignUser(@RequestParam("userId") long userId, @RequestParam("proposalId") long proposalId){
        ProposalHost proposalhost = new ProposalHost();
        proposalhost.setId(47);
        proposalhost.setHostID(15);
        proposalhost.setProposalID(proposalId);
        proposalhost.setHostID(userId);
        proposalHostRepository.save(proposalhost);
    }
    @GetMapping(path = "/vote", params ={"userId","proposalId"})
    public void voteUser(@RequestParam("userId") long userId, @RequestParam("proposalId") long proposalId){
        ProposalHost proposalhost = new ProposalHost();
        proposalhost.setProposalID(proposalId);
        proposalhost.setVotedUserID(userId);
        proposalHostRepository.save(proposalhost);
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

    @GetMapping(path = "/assignedUsers",params={"proposalId"})
    public List<User> showAssignedUsers(@RequestParam("proposalId") int proposalId){
        List<Long> listUserId = new ArrayList<Long>();
        List<ProposalHost> proposalHostList =(List<ProposalHost>) proposalHostRepository.findAll();
        List<Training> trainingList = (List<Training>) trainingRepository.findAll();
        for (ProposalHost proposalHost:proposalHostList) {
            if(proposalHost.getProposalID()==proposalId){
                Long userId = proposalHost.getHostID();
                if(userId!=null){
                    listUserId.add(userId);
                }
            }
        }
        List<User> userList = (List<User>) userRepository.findAll();
        List<User> resultList = new ArrayList<>();
        for (Long userId: listUserId) {
            for(User user: userList){
                if(userId==user.getId()){
                    int countAssignedTraining = 0;
                    int countAssignedProposal = 0;
                    for (ProposalHost proposalHost:proposalHostList) {
                        if(proposalHost.getHostID()==userId){
                            countAssignedProposal++;
                        }
                    }
                    for(Training training:trainingList){
                        if(training.getUserId()!=null){
                            if(Integer.parseInt(training.getUserId())==userId){
                                countAssignedTraining++;
                            }
                        }
                    }
                    user.setCountProposalsAssigned(countAssignedProposal);
                    user.setCountTrainingsAssigned(countAssignedTraining);
                    resultList.add(user);
                }
            }
        }
        return resultList;
    }

}