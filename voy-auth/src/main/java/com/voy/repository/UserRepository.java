package com.voy.repository;


import com.voy.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Bozo on 19/10/2015.
 */


public interface UserRepository extends JpaRepository<User,Long> {


    User findByLogin(String login);

    User findByIdPerson(Long id);



}
