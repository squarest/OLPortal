package com.example.olportal.activities.congratulations;

/**
 * Created by kravchenko on 10/08/16.
 */
public interface IEmailSendView {
    void showLoading();
    void hideLoading();
    void goToNextActivity();
    void showMessage(String Message);
}
