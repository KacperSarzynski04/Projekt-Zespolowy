/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
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
    private String topic;
    @Column
    private String description;
    // assigned trainers?
    @Column
    private Integer assigned;

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Column
    private Integer votes;

    public Proposal(Proposal proposal) {
        this.id = proposal.getId();
        this.votes = proposal.getVotes();
        this.description = proposal.getDescription();
        this.authorId = proposal.getAuthorId();
        this.assigned = proposal.getAssigned();
        this.topic = proposal.getTopic();
    }

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
        this.votes = 0;
    }
    public Proposal(){
        this.topic = "no topic";
        this.description = "no description";
        this.votes = 0;
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