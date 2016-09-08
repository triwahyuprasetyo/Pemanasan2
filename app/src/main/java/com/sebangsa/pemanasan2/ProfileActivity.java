package com.sebangsa.pemanasan2;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
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
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
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
            textViewFollowing.setText(u.getFollowing() + "");
            textViewFollowers.setText(u.getFollowers() + "");

            Glide.with(this).load(u.getMedium().trim()).asBitmap().centerCrop().into(new BitmapImageViewTarget(fab) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(fab.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    fab.setImageDrawable(circularBitmapDrawable);
                    fab.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            });


//            Glide.with(this)
//                    .load(u.getMedium().trim())
//                    .centerCrop()
//                    .placeholder(R.mipmap.ic_launcher)
//                    .crossFade()
//                    .into(fab);

            Log.i("UserRealm", u.getUsername() + " : " + u.getName());

            setImageButtonUser(u);
            EventBus.getDefault().removeStickyEvent(UserRealm.class);
        }
    }

    private void setImageButtonUser(UserRealm user) {
        if (user.isFollow()) {
            //buttonFollowing.setCompoundDrawablesWithIntrinsicBounds(R.drawable.i_followed, 0, 0, 0);
            Drawable drawable = getResources().getDrawable(R.drawable.i_followed);
            drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.8),
                    (int)(drawable.getIntrinsicHeight()*0.8));
            ScaleDrawable sd = new ScaleDrawable(drawable, 0, 20, 20);
            buttonFollowing.setCompoundDrawables(sd.getDrawable(), null, null, null);
            buttonFollowing.setBackgroundResource(R.drawable.profile_rounded_corners_imagebutton_green);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.i_follow);
            drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.8),
                    (int)(drawable.getIntrinsicHeight()*0.8));
            ScaleDrawable sd = new ScaleDrawable(drawable, 0, 20, 20);
            buttonFollowing.setCompoundDrawables(sd.getDrawable(), null, null, null);
            buttonFollowing.setBackgroundResource(R.drawable.rounded_corners_imagebutton_white);
        }
//        Drawable drawable = getResources().getDrawable(R.drawable.i_join);
//        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.8),
//                (int)(drawable.getIntrinsicHeight()*0.8));
//        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 20, 20);
//        buttonFollowing.setCompoundDrawables(sd.getDrawable(), null, null, null);
//        buttonMention.setBackgroundResource(R.drawable.rounded_corners_imagebutton_white);
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
