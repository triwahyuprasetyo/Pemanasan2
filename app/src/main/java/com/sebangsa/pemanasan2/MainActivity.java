package com.sebangsa.pemanasan2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sebangsa.pemanasan2.model.MessageEvent;
import com.sebangsa.pemanasan2.model.UserRealm;
import com.sebangsa.pemanasan2.service.RealmService;
import com.sebangsa.pemanasan2.service.RetrofitService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MAIN ACTIVITY";
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realmService = RealmService.getRealmService(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onMessageEvent(MessageEvent me) {
        if (me.getMessage().equals("Success")) {
            Intent intent = new Intent(MainActivity.this, FollowingActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RealmResults<UserRealm> users = realmService.getUsers();
        if (users.size() > 0) {
            Log.i(LOG_TAG, "Users exist");
        } else {
            Log.i(LOG_TAG, "Users not exist");
            retrieveFollowing();
        }
    }

    private void retrieveFollowing() {
        RetrofitService rs = RetrofitService.getRetrofitServiceInstance();
        rs.retrieveFollowingUsers();
    }
}
