package com.wzy.miaosha7.domain;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public User(int id, String user) {
        this.id = id;
        this.user = user;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                '}';
    }
}
