package com.epiwo.network;

import android.widget.Toast;

import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Siec {
    public static String address = "http://20.93.142.1:8080/api/auth/login";
    public static URL url;
    public static String jwt;


    public static boolean loginNet(String login, String password){
        LoginToNet backgroundLogin = new LoginToNet();
        String authorization = null;


        try {
            authorization = backgroundLogin.execute(Siec.address,login,password).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Objects.equals( authorization, "200" ))
            return true;

        return false;
    }

    }
