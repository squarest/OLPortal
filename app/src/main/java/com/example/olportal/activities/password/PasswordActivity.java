package com.example.olportal.activities.password;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.olportal.R;
import com.example.olportal.model.User;
import com.example.olportal.activities.congratulations.CongratulationsActivity;
import com.example.olportal.activities.registration.RegistrationActivity;
import com.example.olportal.databinding.ActivityPasswordBinding;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Subscription;

public class PasswordActivity extends AppCompatActivity implements IPasswordView {
    private ActivityPasswordBinding binding;
    private Subscription subscription;
    private User user = new User();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password);
        createToolbar();
        createProgressDialog();
        PasswordPresenter presenter = new PasswordPresenter(this);
        if (getIntent() != null) {
            user = (User) getIntent().getSerializableExtra(RegistrationActivity.USER_EXTRA);
            if (user.phone != null && user.code != null) {
                Log.d("TAG", "number " + user.phone + " verifycode " + user.code);
            }
        }
        passwordValidation();
        binding.sendPasswordButton.setOnClickListener(v ->
        {
            user.password = binding.passwordEditText.getText().toString();
            presenter.sendButtonClicked(user);
        });
    }

    private void createToolbar() {
        binding.passwordToolbar.setTitle("Регистрация");
        binding.passwordToolbar.setTitleTextColor(Color.WHITE);
    }

    private void passwordValidation() {
        subscription = RxTextView.textChanges(binding.confirmEditText)
                .map(CharSequence::toString)
                .map(s -> s.length() != 0 && s.equals(binding.passwordEditText.getText().toString()))
                .subscribe(b ->
                {
                    // TODO: data binding
                    if (!b) {
                        binding.confirmTextLayout.setError("Пароли не совпадают");
                        binding.sendPasswordButton.setEnabled(false);
                    } else {
                        binding.confirmTextLayout.setError("");
                        binding.sendPasswordButton.setEnabled(true);

                    }
                });
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Идет отправка");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) subscription.unsubscribe();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void goToNextActivity(User user) {
        Intent intent = new Intent(PasswordActivity.this, CongratulationsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}

