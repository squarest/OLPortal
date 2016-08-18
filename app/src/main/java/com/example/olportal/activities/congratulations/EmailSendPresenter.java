package com.example.olportal.activities.congratulations;

import android.util.Log;

import com.example.olportal.connectionToServer.ConnectionToServer;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kravchenko on 10/08/16.
 */
public class EmailSendPresenter implements IEmailSendPresenter {
    IEmailSendView emailSendView;

    public EmailSendPresenter(IEmailSendView emailSendView) {
        this.emailSendView = emailSendView;
    }

    @Override
    public void sendButtonClicked(String email, String token) {
        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("email", email);
        ConnectionToServer.getInstance().sendEmail(emailMap, "Bearer " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> emailSendView.showLoading())
                .doOnTerminate(() -> emailSendView.hideLoading())
                .subscribe(v -> emailSendView.goToNextActivity(), throwable ->
                {
                    Log.d("TAG", throwable.getMessage());
                    emailSendView.showMessage("Ошибка \n Попробуйте снова");
                });

    }
}
