package com.example.olportal;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionToServer{
    private static Api connection = null;
    public static synchronized Api getInstance() {
        if (connection == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.10.126:3000/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            connection = retrofit.create(Api.class);
        }
        return connection;
    }
}