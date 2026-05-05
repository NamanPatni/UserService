package org.example.dto;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.models.Role;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class SignUpRequestDto {


    String name;
    String password;
    String email;
    String phoneNumber;

    //List of roles
    @ManyToMany
    List<Role> roleList =  new ArrayList<>();

}
