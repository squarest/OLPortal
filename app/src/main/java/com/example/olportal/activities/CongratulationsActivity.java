package com.example.olportal.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.olportal.R;
import com.example.olportal.User;

public class CongratulationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);
        TextView olNumberTextView = (TextView) findViewById(R.id.olNumberTextView);
        TextView passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        if (getIntent() != null) {
            User user = (User) getIntent().getSerializableExtra("user");
            if (user.olNumber != null && user.password != null) {
                olNumberTextView.setText(user.olNumber);
                passwordTextView.setText(user.password);
            }
        }
    }
}
