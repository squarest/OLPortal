package com.example.olportal.connectionToServer;

import android.content.Context;
import android.util.Log;

import com.example.olportal.SharedPreferencesHelper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionToServer {

    public static final String API_BASE_URL = "http://192.168.10.126:3000/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());


    public static Api createConnection() {
        return createConnection(null);
    }

    public static Api createConnection(Context context) {
        if (context != null) {
            SharedPreferencesHelper sharedPreferences = new SharedPreferencesHelper(context.getSharedPreferences(SharedPreferencesHelper.USER_PREFERENCES, Context.MODE_PRIVATE));
            String authToken = sharedPreferences.getString(SharedPreferencesHelper.ACCESS_TOKEN);
            Log.d("TAG","sharedToken" + authToken);
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + authToken)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);

            });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(Api.class);
    }
}