package org.example.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long userId;

    String name;
    String password;
    String email;
    String phoneNumber;

    //List of roles
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    List<Role> roleList =  new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<UserSession> userSessionList;

    // Cardinality is Many to Many

    //Relation between Role and User (Cardinality)

    // Role             User
    //  1                 M
    //  M                 1

    // M         :        M

}