package com.example.olportal.activities.drawer;

import android.graphics.drawable.Drawable;

/**
 * Created by kravchenko on 11/08/16.
 */
public class SocialNetwork {
    public Drawable icon;
    public String name;
    public String userName;

    public SocialNetwork() {
    }
    public SocialNetwork(Drawable icon) {
        this.icon=icon;
    }public SocialNetwork(Drawable icon, String name) {
        this.icon=icon;
        this.name=name;
    }
    public SocialNetwork(Drawable icon, String name,String userName) {
        this.icon=icon;
        this.name=name;
        this.userName=userName;
    }

}
