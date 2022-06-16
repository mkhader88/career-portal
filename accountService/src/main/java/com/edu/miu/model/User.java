package com.edu.miu.model;


import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Table
public class User {
    @PrimaryKey
    private int id;

    private String name;
    private String username;

    private String email;

    private String password;

    private String role;

    public User() {
    }

    public User(String name, String username, String email, String password,String role) {
      //  this.uuid = UUID.randomUUID();
        this.id =(int) Math.random();
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }


//    public UUID getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(UUID uuid) {
//        this.uuid = uuid;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
