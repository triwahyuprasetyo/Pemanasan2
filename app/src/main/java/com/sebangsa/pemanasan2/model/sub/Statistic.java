package com.sebangsa.pemanasan2.model.sub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by sebangsa on 9/13/16.
 */
public class Statistic extends RealmObject {

    @Expose
    @SerializedName("following")
    private int following;

    @Expose
    @SerializedName("followers")
    private int followers;

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
