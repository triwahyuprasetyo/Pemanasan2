package com.sebangsa.pemanasan2.model.sub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by sebangsa on 9/13/16.
 */
public class Avatar extends RealmObject {
    @Expose
    @SerializedName("medium")
    private String medium;

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}