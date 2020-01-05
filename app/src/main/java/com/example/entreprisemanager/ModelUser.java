package com.example.entreprisemanager;
import android.net.Uri;

import java.util.ArrayList;

public class ModelUser {


    private String id, fullname, email, token, user_type;


    public ModelUser(String id, String fullname, String email, String token, String user_type) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.token = token;
        this.user_type = user_type;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
