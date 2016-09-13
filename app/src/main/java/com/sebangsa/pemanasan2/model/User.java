package com.sebangsa.pemanasan2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sebangsa.pemanasan2.model.sub.Action;
import com.sebangsa.pemanasan2.model.sub.Avatar;
import com.sebangsa.pemanasan2.model.sub.Statistic;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sebangsa on 8/30/16.
 */
public class User extends RealmObject {

    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("bio")
    private String bio;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("action")
    private Action action;

    @Expose
    @SerializedName("avatar")
    private Avatar avatar;

    @Expose
    @SerializedName("statistic")
    private Statistic statistic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

}
