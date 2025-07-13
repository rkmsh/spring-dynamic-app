package com.example.fullstack2.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class UserModel {

    private String username;
    private String password;
    private String email;
    private String confirmPassword;
}
