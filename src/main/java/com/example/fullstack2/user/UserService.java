package com.example.fullstack2.user;

import com.example.fullstack2.user.model.UserModel;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    void save(Userinfo userinfo) {
        Userinfo res =  userRepository.findUserinfosByUsername(userinfo.getUsername());
        if (res != null) {
            userRepository.save(new Userinfo(res.getId(), userinfo.getUsername(), userinfo.getPassword(), userinfo.getEnabled()));
        }  else {
            userRepository.save(userinfo);
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Userinfo user = userRepository.findUserinfosByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }

        return (User) User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER") // or load roles from DB
                .disabled(!user.getEnabled())
                .build();
    }
}

