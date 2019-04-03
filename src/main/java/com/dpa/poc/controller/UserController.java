package com.dpa.poc.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.dpa.poc.data.MongoUserDAO;
import com.dpa.poc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    MongoUserDAO mongoUserDAO;

    @RequestMapping("/create")
    public User greeting(@RequestParam(value="name") String name, @RequestParam(value="age") int age) {

        User user = mongoUserDAO.getUserByName(name);

        if(user == null){
            System.out.println("Creating User");
            user = new User(name, age);
            mongoUserDAO.create(user);
        } else {
            System.out.println("Obtained User");
        }
        return user;
    }
}