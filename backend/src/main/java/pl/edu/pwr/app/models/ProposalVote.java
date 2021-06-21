/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

    @Entity
    @Data
    public class ProposalVote implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false, updatable = false)
        private Long id;
        private String proposalId;
        private String userId;

        public ProposalVote(String proposalId, String userId) {
            this.proposalId = proposalId;
            this.userId = userId;
        }
        public ProposalVote(){

        }
        public String getProposalID() {
            return this.proposalId;
        }
        public String getUserID(){
            return this.userId;
        }
    }
