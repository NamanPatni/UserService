package org.example.dto;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.models.BaseModel;
import org.example.models.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponseDto {

    String name;
    String email;

    //List of roles
    List<Role> roleList =  new ArrayList<>();
}
