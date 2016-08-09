package com.example.olportal.activities.password;

import com.example.olportal.User;

/**
 * Created by kravchenko on 09/08/16.
 */
public interface IPasswordView {
    void showLoading();
    void hideLoading();
    void goToNextActivity(User user);
}
