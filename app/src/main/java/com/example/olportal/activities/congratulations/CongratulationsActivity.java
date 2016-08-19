package com.example.olportal.activities.congratulations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olportal.SharedPreferencesHelper;
import com.example.olportal.activities.drawer.MainActivity;
import com.example.olportal.R;
import com.example.olportal.model.User;
import com.jakewharton.rxbinding.widget.RxTextView;

public class CongratulationsActivity extends AppCompatActivity implements IEmailSendView {
    private ProgressDialog progressDialog;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);
        createProgressDialog();

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.email_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        AlertDialog b = dialogBuilder.create();
        setEmailDialogListeners(dialogView, b);

        TextView olNumberTextView = (TextView) findViewById(R.id.olNumberTextView);
        TextView passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        if (getIntent() != null) {
            user = (User) getIntent().getSerializableExtra("user");
            if (user.olNumber != null && user.password != null) {
                olNumberTextView.setText(user.olNumber);
                passwordTextView.setText(user.password);
            }
        }
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> b.show());
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Идет отправка");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
    }

    private void setEmailDialogListeners(View dialogView, AlertDialog dialog) {
        EmailSendPresenter presenter = new EmailSendPresenter(this);
        final EditText edt = (EditText) dialogView.findViewById(R.id.emailEditText);
        final Button okButton = (Button) dialogView.findViewById(R.id.sendEmail);
        final Button cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);
        RxTextView.textChanges(edt)
                .map(CharSequence::toString)
                .map(s -> s.matches("^.+@.+$"))
                .subscribe(b ->
                {
                    okButton.setEnabled(b);
                    if (b) edt.setTextColor(Color.BLACK);
                    else edt.setTextColor(Color.RED);
                });
        okButton.setOnClickListener(v -> {
            dialog.dismiss();
            presenter.sendButtonClicked(edt.getText().toString(), user.accessToken);
        });
        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
            goToNextActivity();
        });
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
    public void goToNextActivity() {
        Intent intent = new Intent(CongratulationsActivity.this, MainActivity.class);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(this.getSharedPreferences(SharedPreferencesHelper.USER_PREFERENCES, MODE_PRIVATE));
        sharedPreferencesHelper.putString(SharedPreferencesHelper.ACCESS_TOKEN, user.accessToken);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(CongratulationsActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
