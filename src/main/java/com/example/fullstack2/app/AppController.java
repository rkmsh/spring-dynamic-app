package com.example.fullstack2.app;

import com.example.fullstack2.security.JwtUtil;
import com.example.fullstack2.user.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@Slf4j
public class AppController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AppController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> doLogin(@RequestBody UserModel user) {
        log.info("Tyring login with user ==> {}", user.getUsername());
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        String jwt = jwtUtil.generateToken(user.getUsername());

        String script =  "<script>localStorage.setItem('jwt','" + jwt + "'); window.location='/dashboard';</script>";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/html")
                .body(script);
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("username", "meta");
        model.addAttribute("stats", Map.of("users", 42, "requests", 123)); // Replace with real stats
        return "dashboard";
    }

    @PostMapping("/contact")
    public String contact(@RequestBody Contact contact, Model model) {
        model.addAttribute("name", contact.name());
        return "fragments/contact-success";
    }




}

record Contact(String name, String email, String message) {}
