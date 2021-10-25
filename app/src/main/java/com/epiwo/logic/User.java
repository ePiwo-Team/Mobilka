package com.epiwo.logic;

import com.epiwo.network.LoginToNet;
import com.epiwo.network.Siec;

import java.util.concurrent.ExecutionException;

public class User {

    Boolean activeAuth = false;

    public User(String login, String password) {
        activeAuth = Siec.loginNet(login, password);
    }

     public Boolean test(){
        return activeAuth;
    }


}
