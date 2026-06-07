package com.ptithcm.managernhaxe.model;

public class User {

    public int id;

    public String name;
    public String email;
    public String phone;
    public String role;
    public String token;
    public String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}