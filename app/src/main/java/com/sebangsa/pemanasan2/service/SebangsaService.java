package com.sebangsa.pemanasan2.service;

import com.sebangsa.pemanasan2.model.UserWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by sebangsa on 8/30/16.
 */
public interface SebangsaService {
    @GET("sdp-latihan/following.php")
    Call<UserWrapper> getFollowingUsers2();

    @GET("sdp-latihan/following.php")
    Observable<UserWrapper> getAllFollowingUsers();
}
