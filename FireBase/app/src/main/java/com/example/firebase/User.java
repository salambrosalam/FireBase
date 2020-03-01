package com.example.firebase;

public class User {
    public String username;
    public String email;
    public int image;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, int image) {
        this.username = username;
        this.email = email;
        this.image = image;
    }
    public User(String username, String email){
        this.username = username;
        this.email = email;
    }
}
