/* This source code is licensed under MIT License (the "License").
   You may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   https://opensource.org/licenses/MIT

 */
package pl.edu.pwr.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Date lastLoginDate;
    private String role;
    private String[] authorities;
    private int countTrainingsAssigned;
    private int countProposalsAssigned;
    private boolean isActive;
    private boolean isNotLocked;

    public User(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public int getCountProposalsAssigned() {
        return countProposalsAssigned;
    }

    public void setCountProposalsAssigned(int countProposalsAssigned) {
        this.countProposalsAssigned = countProposalsAssigned;
    }

    public int getCountTrainingsAssigned() {
        return countTrainingsAssigned;
    }

    public void setCountTrainingsAssigned(int countTrainingsAssigned) {
        this.countTrainingsAssigned = countTrainingsAssigned;
    }
}

