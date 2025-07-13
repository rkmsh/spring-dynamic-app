package com.example.fullstack2.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends ListCrudRepository<Userinfo ,Integer> {

    @Query(value = "select * from userinfo where username = :username limit 1", nativeQuery = true)
    Userinfo findUserinfosByUsername(String username);
}
