package com.sebangsa.pemanasan2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.sebangsa.pemanasan2.model.UserRealm;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(sticky = true)
    public void onUserRealmEvent(UserRealm user) {
        if (user != null) {
            Log.i("UserRealm", user.getUsername() + " : " + user.getName());
            EventBus.getDefault().removeStickyEvent(UserRealm.class);
        }
    }
}
