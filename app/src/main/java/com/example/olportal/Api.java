package com.example.olportal;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface Api {
    @POST("sms/send/")
    Observable<Void> smsSend(@Body Map<String, String> phone);

    @POST("sms/verify/")
    Observable<Void> smsVerify(@Body Map<String, String> phone);

    @POST("user/")
    Observable<User> register(@Body User user);
}