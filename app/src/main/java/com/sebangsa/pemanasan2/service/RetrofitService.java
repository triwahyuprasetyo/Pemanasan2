package com.sebangsa.pemanasan2.service;

import android.util.Log;

import com.sebangsa.pemanasan2.model.User;
import com.sebangsa.pemanasan2.model.UserRealm;
import com.sebangsa.pemanasan2.model.UserWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sebangsa on 8/31/16.
 */
public class RetrofitService {
    private static final String BASE_URL = "http://hangga.web.id/";
    private static RetrofitService retrofitService;
    private SebangsaService service;

    public RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(SebangsaService.class);
    }

    public static RetrofitService getRetrofitServiceInstance() {
        if (retrofitService == null) {
            retrofitService = new RetrofitService();
        }
        return retrofitService;
    }

    public void retrieveFollowingUsers() {
        Call<UserWrapper> call = service.getFollowingUsers();
        call.enqueue(new Callback<UserWrapper>() {
            @Override
            public void onResponse(Call<UserWrapper> call, Response<UserWrapper> response) {
                List<UserRealm> userList = new ArrayList<UserRealm>();
                UserRealm user;
                for (User u : response.body().getUsers()) {
                    user = new UserRealm();
                    user.setId(u.getId());
                    user.setUsername(u.getUsername());
                    user.setBio(u.getBio());
                    user.setName(u.getName());
                    user.setFollow(u.getAction().isFollow());
                    user.setMedium(u.getAvatar().getMedium());
                    user.setFollowing(u.getStatistic().getFollowing());
                    user.setFollowers(u.getStatistic().getFollowers());
                    userList.add(user);
                    Log.i("FOLLOWING", u.getId() + ", " + u.getUsername() + ", " + u.getBio() + ", " + u.getName() + ", " + u.getAction().isFollow() + ", " + u.getAvatar().getMedium() + ", " + u.getStatistic().getFollowing() + ", " + u.getStatistic().getFollowers());
                }
            }

            @Override
            public void onFailure(Call<UserWrapper> call, Throwable t) {

            }
        });
    }
}
