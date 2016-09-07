package com.sebangsa.pemanasan2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sebangsa.pemanasan2.model.UserRealm;
import com.sebangsa.pemanasan2.service.RealmService;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MAIN ACTIVITY";
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realmService = RealmService.getRealmService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RealmResults<UserRealm> users = realmService.getUsers();
        if (users.size() > 0)
            Log.i(LOG_TAG, "Users exist");
        else
            Log.i(LOG_TAG, "Users not exist");
    }
}
