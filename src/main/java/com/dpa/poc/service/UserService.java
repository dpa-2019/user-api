package com.dpa.poc.service;

import com.dpa.poc.data.MongoUserDAO;
import com.dpa.poc.entity.RegisterUser;
import com.dpa.poc.entity.User;
import com.dpa.poc.exception.InvalidPasswordException;
import com.dpa.poc.exception.InvalidUserException;
import com.dpa.poc.exception.UserExistsException;
import com.dpa.poc.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserService {

    @Autowired
    private String salt;

    @Autowired
    private MongoUserDAO userDAO;

    public User registerUser(RegisterUser user) throws UserExistsException {

        //TODO: Validate User logic here

        Optional<String> hashedPassword = PasswordUtil.hashPassword(user.getPassword(), salt);
        user.setPassword(hashedPassword.get());

        boolean userCreated = userDAO.create(user);

        user.setPassword(null);
        return (User)user;
    }

    public User authenticateUser(String email, String password) throws InvalidUserException, InvalidPasswordException {
         RegisterUser user = userDAO.getUserByEmail(email);

        if(user == null || user.getPassword() == null){
            throw new InvalidUserException(email);
        }

        boolean validPassword = PasswordUtil.verifyPassword(password, user.getPassword(), salt);
        if(validPassword){
            return (User)user;
        }

        throw new InvalidPasswordException(email);
    }
}
