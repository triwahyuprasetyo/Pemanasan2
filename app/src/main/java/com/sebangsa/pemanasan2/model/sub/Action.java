package com.sebangsa.pemanasan2.model.sub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by sebangsa on 9/13/16.
 */
public class Action extends RealmObject {

    @Expose
    @SerializedName("is_follow")
    private boolean follow;

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }
}
