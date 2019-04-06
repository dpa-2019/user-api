package com.dpa.poc.exception;

public class UserExistsException extends Exception {

    public UserExistsException(String email){
        super("User Exists Already for Email: "+email);
    }
}
