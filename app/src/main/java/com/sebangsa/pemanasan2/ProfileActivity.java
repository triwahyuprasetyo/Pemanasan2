package com.sebangsa.pemanasan2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sebangsa.pemanasan2.model.UserRealm;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUsername;
    private TextView textViewName;
    private TextView textViewLocation;
    private TextView textViewBio;
    private TextView textViewPublicPost;
    private TextView textViewCommunity;
    private TextView textViewFollowing;
    private TextView textViewFollowers;
    private Button buttonMention;
    private Button buttonFollowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        textViewUsername = (TextView) findViewById(R.id.textView_username);
        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewLocation = (TextView) findViewById(R.id.textView_location);
        textViewBio = (TextView) findViewById(R.id.textView_bio);
        textViewPublicPost = (TextView) findViewById(R.id.textView_public_post);
        textViewCommunity = (TextView) findViewById(R.id.textView_community);
        textViewFollowing = (TextView) findViewById(R.id.textView_following);
        textViewFollowers = (TextView) findViewById(R.id.textView_followers);
        buttonFollowing = (Button) findViewById(R.id.button_following);
        buttonFollowing.setOnClickListener(this);
        buttonMention = (Button) findViewById(R.id.button_mention);
        buttonMention.setOnClickListener(this);

        EventBus.getDefault().register(this);

        setTitle("");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(sticky = true)
    public void onUserRealmEvent(UserRealm u) {
        if (u != null) {
            Log.i("UserRealm", u.getUsername() + " : " + u.getName());
            Log.i("FOLLOWING", u.getId() + ", " + u.getUsername() + ", " + u.getBio() + ", "
                    + u.getName() + ", " + u.isFollow() + ", " + u.getMedium() + ", " + u.getFollowing() + ", "
                    + u.getFollowers());
            textViewUsername.setText("@" + u.getUsername());
            textViewName.setText(u.getName());
            textViewLocation.setText("Loc -");
            textViewBio.setText(u.getBio());
            textViewPublicPost.setText("0");
            textViewCommunity.setText("0");
            textViewFollowing.setText(u.getFollowing()+"");
            textViewFollowers.setText(u.getFollowers()+"");
            Log.i("UserRealm", u.getUsername() + " : " + u.getName());

            EventBus.getDefault().removeStickyEvent(UserRealm.class);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }
}
