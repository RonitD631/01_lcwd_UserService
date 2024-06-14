package com.user.service.UserService.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "micro_users")
@Getter
@Setter
public class User {

     @Id
     @Column (name = "ID")
     private String userId;

     @Column (name = "NAME")
     private String name;

     @Column (name = "EMAIL")
     private String email;

     @Column (name = "ABOUT")
     private String about;

     @Transient
     private List<Rating> ratings = new ArrayList<>();

}
