package com.sebangsa.pemanasan2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sebangsa on 8/30/16.
 */
public class UserWrapper {
    @SerializedName(value = "friends")
    public List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
