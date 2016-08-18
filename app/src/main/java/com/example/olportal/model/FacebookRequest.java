package com.example.olportal.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by kravchenko on 18/08/16.
 */
public class FacebookRequest {
    @SerializedName("userSocialId")
    public String userSocialId;
    @SerializedName("accessToken")
    public String accessToken;
    @SerializedName("expires")
    public String expires;
    @SerializedName("permissions")
    public Set<String> permissions;
}