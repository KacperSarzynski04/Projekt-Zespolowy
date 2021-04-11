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
    private boolean isActive;
    private boolean isNotLocked;

}

