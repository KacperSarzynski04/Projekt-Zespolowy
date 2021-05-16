package pl.edu.pwr.app.models;

import javax.persistence.*;

@Entity
@Table(name = "proposal_host")
public class ProposalHost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    private long proposalID;
    private long authorID;
    private long hostID;
    private long votedUserID;


    public ProposalHost(long proposalId, long authorId) {
        this.proposalID = proposalId;
        this.authorID = authorId;

    }

    public ProposalHost() {
    }

    public ProposalHost(ProposalHost proposalHost) {
        this.proposalID = proposalHost.getProposalID();
        this.authorID = proposalHost.getAuthorID();
        this.hostID = proposalHost.getHostID();
        this.id = proposalHost.getId();
        this.votedUserID = proposalHost.getVotedUserID();
    }

    public long getVotedUserID() {
        return votedUserID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }




    public ProposalHost(long proposalID, long authorID, long hostID) {
        this.proposalID = proposalID;
        this.authorID = authorID;
        this.hostID = hostID;
    }

    public long getHostID() {
        return hostID;
    }

    public void setHostID(long hostID) {
        this.hostID = hostID;
    }

    public long getProposalID() {
        return proposalID;
    }



    public void setProposalID(long proposalID) {
        this.proposalID = proposalID;
    }

}
