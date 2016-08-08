package com.example.olportal.activities.registration;

/**
 * Created by kravchenko on 08/08/16.
 */
public interface IRegistrationPresenter {
    void sendSms(String number);
    void verifyNumber(String number, String code);
}

