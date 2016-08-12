package com.example.olportal.activities.drawer;

/**
 * Created by kravchenko on 11/08/16.
 */

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MyBindingAdapter {

    //лого соцсети
    @BindingAdapter("avatarUrl")
    public static void setAvatarUrl(ImageView avatar, String avatarUrl) {
        Picasso.with(avatar.getContext())
                .load(avatarUrl)
                .into(avatar);

    }
}
