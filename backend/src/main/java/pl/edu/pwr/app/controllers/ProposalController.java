package pl.edu.pwr.app.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.models.*;
import pl.edu.pwr.app.repositories.*;
import pl.edu.pwr.app.response.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true")
@RequestMapping(path = { "/"})
public class ProposalController {
    private final ProposalRepository proposalRepository;
    private final ProposalHostRepository proposalHostRepository;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final ProposalVoteRepository proposalVoteRepository;
    public ProposalController(ProposalRepository proposalRepository, ProposalHostRepository proposalHostRepository, UserRepository userRepository, TrainingRepository trainingRepository, ProposalVoteRepository proposalVoteRepository) {
        this.proposalRepository = proposalRepository;
        this.proposalHostRepository = proposalHostRepository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.proposalVoteRepository = proposalVoteRepository;
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
    @GetMapping(path = "/topics/vote", params ={"userId","proposalId"})
    public void assignUser(@RequestParam("userId") String userId, @RequestParam("proposalId") String proposalId)
    {
        ProposalVote proposalVote = new ProposalVote(proposalId,userId);
        proposalVoteRepository.save(proposalVote);
        Optional proposalOptional = proposalRepository.findById(Long.parseLong(proposalId));
        Proposal proposal = (Proposal) proposalOptional.get();
        Integer votes = proposal.getVotes();
        if(votes==null){
            votes=0;
        }
        votes++;
        proposal.setVotes(votes);
        proposalRepository.save(proposal);
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
        List<ProposalVote> proposalVoteList= (List<ProposalVote>) proposalVoteRepository.findAll();
        for (ProposalVote proposalVote : proposalVoteList) {
            if (proposalVote.getProposalID().equals(proposalId) && proposalVote.getUserID().equals(userId)) {
                return false;
            }
        }
        return true;
    }
    @GetMapping(path = "/topics/assign", params ={"userId","proposalId"})
    public void assignUser(@RequestParam("userId") long userId, @RequestParam("proposalId") String proposalId){
        ProposalHost proposalhost = new ProposalHost();
        proposalhost.setProposalID(Long.parseLong(proposalId));
        proposalhost.setHostID(userId);
        proposalHostRepository.save(proposalhost);
        Optional proposalOptional = proposalRepository.findById(Long.parseLong(proposalId));
        Proposal proposal = (Proposal) proposalOptional.get();
        Integer assigned = proposal.getAssigned();
        if(assigned==null){
            assigned=0;
        }
        assigned++;
        proposal.setAssigned(assigned);
        proposalRepository.save(proposal);

    }
    @GetMapping(path = "/vote", params ={"userId","proposalId"})
    public void voteUser(@RequestParam("userId") long userId, @RequestParam("proposalId") long proposalId){
        ProposalHost proposalhost = new ProposalHost();
        proposalhost.setProposalID(proposalId);
        proposalhost.setVotedUserID(userId);
        proposalHostRepository.save(proposalhost);
    }

    @DeleteMapping("topics/delete/{id}")
    //@PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") long id) throws IOException {
        proposalRepository.deleteById(id);
        return response(HttpStatus.OK, "Deleted successfully");
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
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

    @GetMapping("/find_topic/{id}")
    public Optional<Proposal> getProposal(@PathVariable("id") long id) {
        return proposalRepository.findById(id);
    }
}