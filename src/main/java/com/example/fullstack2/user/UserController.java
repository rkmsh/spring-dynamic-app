package com.example.fullstack2.user;

import com.example.fullstack2.user.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserModel user) {
        userService.save(new Userinfo(UUID.randomUUID().toString(), user.getUsername(), passwordEncoder.encode(user.getPassword()), true));
        return ResponseEntity.status(HttpStatus.OK).header("HX-Redirect", "/login").build();
    }
}


