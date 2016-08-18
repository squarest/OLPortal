package com.example.olportal.activities.drawer;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by kravchenko on 18/08/16.
 */
public class FacebookRequest {
    @SerializedName("userSocialId")
    String userSocialId;
    @SerializedName("accessToken")
    String accessToken;
    @SerializedName("expires")
    String expires;
    @SerializedName("permissions")
    Set<String> permissions;
}