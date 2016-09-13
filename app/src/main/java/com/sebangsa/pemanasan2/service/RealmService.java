package com.sebangsa.pemanasan2.service;

import android.content.Context;

import com.sebangsa.pemanasan2.model.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by sebangsa on 9/7/16.
 */
public class RealmService {
    private static RealmService realmService;
    private final String LOG_TAG = "REALM SERVICE";
    private Realm realm;

    public RealmService() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmService getRealmService(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        if (realmService == null) {
            realmService = new RealmService();
        }
        return realmService;
    }


    public void saveUser2(final User userRealm) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realmm) {
                realmm.copyToRealmOrUpdate(userRealm);
            }
        });
    }

    public RealmResults<User> getUsers2() {
        return realm.where(User.class).findAll();
    }
}
