package com.epiwo.network;

import android.widget.Toast;

import com.epiwo.logic.User;

import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Siec {
    public static String address = "http://20.93.142.1:8080";
    public static String loginURL = address+ "/api/auth/login";
    public static String registerURL = address+ "/api/user/register";
    //public static String echoURL = address+ "/api/user/helloUser";
    public static URL url;
    public static String jwt;


    public static boolean loginNet(String login, String password){
        LoginToNet backgroundLogin = new LoginToNet();
        String authorization = null;


        try {
            authorization = backgroundLogin.execute(Siec.loginURL,login,password).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Objects.equals( authorization, "200" ))
            return true;

        return false;
    }


    public static boolean register( User registrationData){
        RegisterToNet register = new RegisterToNet();
        String authorization = null;

        try {
            authorization = register.execute(Siec.registerURL,
                                             registrationData.email,
                                             registrationData.password,
                                             registrationData.login,
                                             registrationData.phone,
                                             registrationData.birthDate).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Objects.equals( authorization, "200" ))
            return true;

        return false;
    }

  /*  public static String echo() {
        EchoToNet echo = new EchoToNet();
        String output = null;

        try {
            output = echo.execute(Siec.echoURL,jwt).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;

    }*/

}
