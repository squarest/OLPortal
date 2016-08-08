package com.example.olportal.activities.registration;

/**
 * Created by kravchenko on 08/08/16.
 */
public interface IRegistrationView {
    void showMessage(String message);
    void numberIsValid(boolean enabled);
    void showProgress();
    void hideProgress();
    void setNumberError();
    void setCodeError();
    void dismissNumberError();
    void dismissCodeError();
}
