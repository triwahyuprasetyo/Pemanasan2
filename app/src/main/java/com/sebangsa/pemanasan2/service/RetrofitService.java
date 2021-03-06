package com.sebangsa.pemanasan2.service;

import android.util.Log;

import com.sebangsa.pemanasan2.model.MessageEvent;
import com.sebangsa.pemanasan2.model.User;
import com.sebangsa.pemanasan2.model.UserWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sebangsa on 8/31/16.
 */
public class RetrofitService {
    private static final String TAG = "RETROFIT_SERVICE";
    private static final String BASE_URL = "http://hangga.web.id/";
    private static RetrofitService retrofitService;
    private SebangsaService service;
    private Subscription subscription;
    private Subscription subscriptionSearch;
    private Observer<UserWrapper> sebangsaObserver;
    private Observer<List<User>> userListObserver;

    public RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(SebangsaService.class);
        initObserverSebangsa();
    }

    public static RetrofitService getRetrofitServiceInstance() {
        if (retrofitService == null) {
            retrofitService = new RetrofitService();
        }
        return retrofitService;
    }

    private void initObserverSebangsa() {
        sebangsaObserver = new Observer<UserWrapper>() {

            @Override
            public void onCompleted() {
                subscription.unsubscribe();
                Log.i(TAG, "Observer Complete & unSubscribe");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, ">>>> onError gets called : " + e.getMessage());
                Log.i(TAG, "Retrieve Following Users Error");
                EventBus.getDefault().post(new MessageEvent("Failure"));
            }

            @Override
            public void onNext(UserWrapper users) {
                List<User> userList = users.getUsers();
                Log.i(TAG, "Retrieve Following Users Success" + userList.size());
                EventBus.getDefault().post(userList);
            }
        };
    }

    public void retrieveAllFollowingUsers() {
        subscription = service.getAllFollowingUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sebangsaObserver);
    }
}
