package org.example.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    Date lastUpdatedAt;
    Date createdAt;
}
