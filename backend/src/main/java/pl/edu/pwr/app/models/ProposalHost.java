package pl.edu.pwr.app.models;

import javax.persistence.*;

@Entity
@Table(name = "proposal_host")
public class ProposalHost {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    private long hostID;
    private long proposalID;


    public ProposalHost(long hostID, long proposalID) {
        this.hostID = hostID;
        this.proposalID = proposalID;
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
