package com.example.olportal.activities.password;

import android.util.Log;

import com.example.olportal.connectionToServer.ConnectionToServer;
import com.example.olportal.model.User;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kravchenko on 09/08/16.
 */
public class PasswordPresenter implements IPasswordPresenter {
    IPasswordView passwordView;

    public PasswordPresenter(IPasswordView passwordView) {
        this.passwordView = passwordView;
    }

    @Override
    public void sendButtonClicked(User user) {
        ConnectionToServer.getInstance().register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> passwordView.showLoading())
                .doOnTerminate(() -> passwordView.hideLoading())
                .subscribe(u ->
                {
                    user.accessToken = u.accessToken;
                    user.olNumber = u.olNumber;
                    Log.d("TAG", "token = " + user.accessToken + " olnumber = " + user.olNumber);
                    passwordView.goToNextActivity(user);
                }, throwable ->
                {
                    Log.d("TAG", "error " + throwable.getMessage());
                    passwordView.hideLoading();
                });
    }
}
