package com.example.olportal;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface Api {
    @POST("sms/send/")
    Observable<Void> smsSend(@Body Map<String, String> phone);

    @POST("sms/verify/")
    Observable<Void> smsVerify(@Body Map<String, String> phone);

    @POST("user/")
    Observable<User> register(@Body User user);

    @PUT("user/")
    Observable<Void> sendEmail(@Body Map<String,String> email, @Header("Authorization") String token);
}