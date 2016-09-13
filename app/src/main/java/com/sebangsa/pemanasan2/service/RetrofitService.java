package com.sebangsa.pemanasan2.service;

import android.util.Log;

import com.sebangsa.pemanasan2.FollowingActivity;
import com.sebangsa.pemanasan2.model.MessageEvent;
import com.sebangsa.pemanasan2.model.User;
import com.sebangsa.pemanasan2.model.UserWrapper;

import org.greenrobot.eventbus.EventBus;

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
    private RealmService realmService;

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

    public void retrieveFollowingUsers2() {
        Call<UserWrapper> call = service.getFollowingUsers2();
        call.enqueue(new Callback<UserWrapper>() {
            @Override
            public void onResponse(Call<UserWrapper> call, Response<UserWrapper> response) {
                List<User> userList = response.body().getUsers();

                realmService = FollowingActivity.realmService;
                for (User user2 : userList) {
                    Log.i("USER ID", user2.getId() + "");
                    realmService.saveUser2(user2);
                }
                Log.i("FOLLOWING", "Success" + userList.size());
                EventBus.getDefault().post(userList);
            }

            @Override
            public void onFailure(Call<UserWrapper> call, Throwable t) {
                Log.i("FOLLOWING", "Error");
                EventBus.getDefault().post(new MessageEvent("Failure"));
            }
        });
    }
}
