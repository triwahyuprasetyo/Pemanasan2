package com.sebangsa.pemanasan2;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sebangsa.pemanasan2.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ProfileActivity extends AppCompatActivity {

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
    private ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewUsername = (TextView) findViewById(R.id.textView_username);
        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewLocation = (TextView) findViewById(R.id.textView_location);
        textViewBio = (TextView) findViewById(R.id.textView_bio);
        textViewPublicPost = (TextView) findViewById(R.id.textView_public_post);
        textViewCommunity = (TextView) findViewById(R.id.textView_community);
        textViewFollowing = (TextView) findViewById(R.id.textView_following);
        textViewFollowers = (TextView) findViewById(R.id.textView_followers);
        imageViewProfile = (ImageView) findViewById(R.id.imageView_profile);
        buttonFollowing = (Button) findViewById(R.id.button_following);
        buttonMention = (Button) findViewById(R.id.button_mention);
        EventBus.getDefault().register(this);
        setTitle("");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(sticky = true)
    public void onUserRealmEvent(User u) {
        if (u != null) {
            Log.i("UserRealm", u.getUsername() + " : " + u.getName());
            Log.i("FOLLOWING", u.getId() + ", " + u.getUsername() + ", " + u.getBio() + ", "
                    + u.getName() + ", " + u.getAction().isFollow() + ", " + u.getAvatar().getMedium() + ", " + u.getStatistic().getFollowing() + ", "
                    + u.getStatistic().getFollowers());
            textViewUsername.setText("@" + u.getUsername());
            textViewName.setText(u.getName());
            textViewLocation.setText("Loc -");
            textViewBio.setText(u.getBio());
            textViewPublicPost.setText("0");
            textViewCommunity.setText("0");
            textViewFollowing.setText(u.getStatistic().getFollowing() + "");
            textViewFollowers.setText(u.getStatistic().getFollowers() + "");
            Glide.with(this).load(u.getAvatar().getMedium().trim()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageViewProfile) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(imageViewProfile.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageViewProfile.setImageDrawable(circularBitmapDrawable);
                }
            });
            imageViewProfile.setBackgroundResource(R.drawable.all_circle_white_bg);
            Log.i("UserRealm", u.getUsername() + " : " + u.getName());
            setImageButtonUser(u);
            EventBus.getDefault().removeStickyEvent(User.class);
        }
    }

    private void setImageButtonUser(User user) {
        if (user.getAction().isFollow()) {
            setButtonProperties(buttonFollowing, R.drawable.i_followed, Color.WHITE, "Following", R.drawable.profile_rounded_corners_imagebutton_green);
        } else {
            setButtonProperties(buttonFollowing, R.drawable.i_follow, Color.BLACK, "Follow", R.drawable.profile_rounded_corners_imagebutton_white);
        }
        setButtonProperties(buttonMention, R.drawable.i_join, Color.BLACK, "Mention", R.drawable.profile_rounded_corners_imagebutton_white);
    }

    private void setButtonProperties(Button button, int icon, int textColor, String text, int backgroundResource) {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), icon);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.7),
                (int) (drawable.getIntrinsicHeight() * 0.7));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, 0, 0);
        button.setCompoundDrawables(sd.getDrawable(), null, null, null);
        button.setBackgroundResource(backgroundResource);
        button.setTextColor(textColor);
        button.setText(text);
        button.setTextSize(15);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }
}
