package com.example.olportal;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionToServer{
    private static Api _connection = null;
    public static synchronized Api getInstance() {
        if (_connection == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.10.126:3000/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            _connection = retrofit.create(Api.class);
        }
        return _connection;
    }
}