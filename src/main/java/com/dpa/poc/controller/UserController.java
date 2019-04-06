package com.dpa.poc.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.dpa.poc.data.MongoUserDAO;
import com.dpa.poc.entity.RegisterUser;
import com.dpa.poc.entity.User;
import com.dpa.poc.exception.InvalidPasswordException;
import com.dpa.poc.exception.InvalidUserException;
import com.dpa.poc.exception.UserExistsException;
import com.dpa.poc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "user")
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    UserService userService;

    @PutMapping("/register")
    public ResponseEntity register(@RequestBody RegisterUser user) {


        try {
            userService.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }


        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping("/login")
    public ResponseEntity login(@RequestBody RegisterUser user) {
        User loggedInUser = null;
        try {
            loggedInUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        } catch (InvalidUserException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(loggedInUser, HttpStatus.OK);

    }
}