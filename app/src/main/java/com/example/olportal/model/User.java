package com.example.olportal.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kravchenko on 09/08/16.
 */
public class User implements Serializable {

    @SerializedName("accessToken")
    public String accessToken;
    @SerializedName("olNumber")
    public String olNumber;
    @SerializedName("phone")
    public String phone;
    @SerializedName("code")
    public String code;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;

}
