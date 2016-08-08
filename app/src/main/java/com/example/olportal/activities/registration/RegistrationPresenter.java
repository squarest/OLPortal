package com.example.olportal.activities.registration;

import android.util.Log;

import com.example.olportal.ConnectionToServer;

import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kravchenko on 08/08/16.
 */
public class RegistrationPresenter implements IRegistrationPresenter {
    private IRegistrationView registrationView;

    public RegistrationPresenter(IRegistrationView view) {
        this.registrationView = view;
    }

    @Override
    public void sendSms(String number) {
        registrationView.showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("phone", number);
        ConnectionToServer.getInstance().smsSend(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> registrationView.hideProgress())
                .subscribe(
                        v -> {
                            registrationView.showMessage("success");
                            registrationView.numberIsValid(true);
                            registrationView.dismissNumberError();
                        },
                        throwable -> {
                            HttpException httpException = (HttpException)throwable;
                            if(httpException.code()==409) {
                                registrationView.setNumberError();
                            }
                            registrationView.showMessage("error");
                            registrationView.numberIsValid(false);
                            registrationView.hideProgress();
                        });
    }

    @Override
    public void verifyNumber(String number, String code) {
        registrationView.showProgress();
        Map<String, String> codeMap = new HashMap<>();
        codeMap.put("phone", number);
        codeMap.put("code", code);
        ConnectionToServer.getInstance().smsVerify(codeMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(registrationView::hideProgress)
                .subscribe(
                        v1 -> {
                            registrationView.showMessage("success");
                            registrationView.dismissCodeError();
                        },
                        throwable -> {
                            HttpException httpException = (HttpException)throwable;
                            if(httpException.code()==404) {
                                registrationView.setCodeError();
                            }
                            Log.d("TAG", "error " + throwable.getMessage());
                            registrationView.showMessage("error");
                            registrationView.hideProgress();
                        });
    }
}
