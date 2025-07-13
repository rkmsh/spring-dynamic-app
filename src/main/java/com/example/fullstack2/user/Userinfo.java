package com.example.fullstack2.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "userinfo")
class Userinfo {

    @Id
    private String id;

    private String username;
    private String password;
    private Boolean enabled;

    public Userinfo() {

    }
}
