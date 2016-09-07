package com.sebangsa.pemanasan2;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sebangsa.pemanasan2.model.MessageEvent;
import com.sebangsa.pemanasan2.model.UserRealm;
import com.sebangsa.pemanasan2.service.RealmService;
import com.sebangsa.pemanasan2.service.RetrofitService;
import com.sebangsa.pemanasan2.ui.SebangsaRecyclerViewAdapter;
import com.sebangsa.pemanasan2.ui.SimpleDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class FollowingActivity extends AppCompatActivity implements View.OnKeyListener {
    private final String LOG_TAG = "FOLLOWING ACTIVITY";
    private RecyclerView recView;
    private SebangsaRecyclerViewAdapter adapter;
    private EditText editTextSearch;
    private List<UserRealm> userList;
    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        actionBarSetup();

        editTextSearch = (EditText) findViewById(R.id.editText_search);
        editTextSearch.setOnKeyListener(this);

        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.addItemDecoration(new SimpleDividerItemDecoration(this));

        userList = new ArrayList<UserRealm>();

        realmService = RealmService.getRealmService(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userList.size() > 0) {
            setAdapter(userList);
        } else {
            RealmResults<UserRealm> users = realmService.getUsers();
            if (users.size() > 0) {
                Log.i(LOG_TAG, "Users exist");
            } else {
                Log.i(LOG_TAG, "Users not exist");
                retrieveFollowing();
            }
        }
    }

    private void retrieveFollowing() {
        RetrofitService rs = RetrofitService.getRetrofitServiceInstance();
        rs.retrieveFollowingUsers();
    }

    @Subscribe
    public void onUserRealmEvent(List<UserRealm> users) {
        userList = users;
        Log.i("FOLLOWINGGG", "Selesai " + users.size());
        setAdapter(users);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent me) {
        Toast.makeText(getApplicationContext(), me.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void actionBarSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle("dennywidyatmokoasli");
            ab.setSubtitle("Following");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(LOG_TAG, query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(LOG_TAG, newText);
                if (userList.size() > 0) {
                    searchUser(newText.toLowerCase().trim());
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (userList.size() > 0) {
            searchUser(editTextSearch.getText().toString().toLowerCase().trim());
        }
        return false;
    }

    private void setAdapter(List<UserRealm> userList) {
        adapter = new SebangsaRecyclerViewAdapter(userList, this);
        recView.setAdapter(adapter);
    }

    private void searchUser(String query) {
        List<UserRealm> userListTemp = new ArrayList<UserRealm>();
        for (UserRealm u : userList) {
            if (u.getUsername().toLowerCase().contains(query)) {
                userListTemp.add(u);
            }
        }
        setAdapter(userListTemp);
    }
}
