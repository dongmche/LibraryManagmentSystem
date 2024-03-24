package com.example.Fakeapp.dao.UserManager;

import com.example.Fakeapp.func.Encoder;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String password;
    private String gmail;


    private static final Encoder encoder = new Encoder();


    public User(Long id, String name, String password, String mail) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gmail = mail;
    }
    public User(String name, String password, String mail) {
        this.name = name;
        this.password = password;
        this.gmail = mail;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(java.lang.String username){
        this.name = username;
    }

    public java.lang.String getPassword() {

        return password;
    }

    public void setPassword(java.lang.String password){

        this.password = password;
    }
    public String getGmail() {

        return gmail;
    }

    public void setGmail(java.lang.String gmail) {
        this.gmail = gmail;
    }

    public void secure(){
        this.password = encoder.hash(password);
    }

    public boolean compareUnhashed(String password){
        return this.password.equals(encoder.hash(password));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.getName()) &&
                Objects.equals(gmail, user.getGmail()) &&
                Objects.equals(password, user.getPassword());
    }
}
