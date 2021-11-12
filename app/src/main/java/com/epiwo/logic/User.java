package com.epiwo.logic;


import com.epiwo.network.Siec;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class User {

    Boolean activeAuth = false;
    public String login;
    public String password;
    public String phone;
    public String birthDate;
    public String email;


    public User(String login, String password, String phone, String birthDate, String email) {
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
    }

    public User(String login, String password) {
        activeAuth = Siec.loginNet(login, password);
    }

    public Boolean test(){
        return activeAuth;
    }

    public Boolean checkPassword(String passwordCheck){
        return(Objects.equals(passwordCheck, password));
    }

    public Boolean register(){
        return Siec.register(this);


    }
}
