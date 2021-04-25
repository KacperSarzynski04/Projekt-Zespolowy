package pl.edu.pwr.app.models;


import javax.persistence.*;

@Entity
@Table(name = "proposal")
public class Proposal implements Comparable<Proposal>{
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column
    private long authorId;
    @Column
    private final String topic;
    @Column
    private final String description;

    public long getId() {
        return id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public Proposal(long authorId, String topic, String description) {
        this.authorId = authorId;
        this.topic = topic;
        this.description = description;
    }
    public Proposal(){
        this.topic = "no topic";
        this.description = "no description";
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Proposal o) {
        if(this.getTopic().compareTo(o.getTopic())>0) return 1;
        if(this.getTopic().compareTo(o.getTopic())<0) return -1;
        return 0;
    }
}