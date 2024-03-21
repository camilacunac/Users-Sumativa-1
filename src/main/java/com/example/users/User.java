package com.example.users;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String id;
    private String email;
    private String password;
    private String role;
    private String direccion;

    public User(String email, String password, String role, String direccion) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.role = role;
        this.direccion = direccion;
    }

    // Getter para el campo 'id'
    public String getId() {
        return id;
    }

    // Getter para el campo 'email'
    public String getEmail() {
        return email;
    }

    // Getter para el campo 'password'
    public String getPassword() {
        return password;
    }

    // Getter para el campo 'role'
    public String getRole() {
        return role;
    }

    // Getter para el campo 'direccion'
    public String getDireccion() {
        return direccion;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
