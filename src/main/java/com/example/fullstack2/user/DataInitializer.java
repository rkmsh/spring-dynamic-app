package com.example.fullstack2.user;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder pwEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder pwEncoder) {
        this.userRepository = userRepository;
        this.pwEncoder = pwEncoder;
    }

    @PostConstruct
    public void init() {
        String defaultUsername = "beast";

        if (userRepository.findUserinfosByUsername(defaultUsername) == null) {
            Userinfo user = new Userinfo();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(defaultUsername);
            user.setPassword(pwEncoder.encode("beast"));
            user.setEnabled(true);

            userRepository.save(user);
            log.info("✅ Default user 'admin' inserted.");
        }
    }

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        String defaultUsername = "beast";
//
//        if (userRepository.findUserinfosByUsername(defaultUsername) == null) {
//            Userinfo user = new Userinfo();
//            user.setId(UUID.randomUUID().toString());
//            user.setUsername(defaultUsername);
//            user.setPassword(pwEncoder.encode("beast"));
//            user.setEnabled(true);
//
//            userRepository.save(user);
//            log.info("✅ Default user 'admin' inserted.");
//        }
//    }
}