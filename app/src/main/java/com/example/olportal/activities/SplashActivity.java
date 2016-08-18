package com.example.olportal.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import com.example.olportal.R;
import com.example.olportal.activities.registration.RegistrationActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView textView = (TextView)findViewById(R.id.company_name_button);
        SpannableString string = new SpannableString("OLPortal");
        string.setSpan(new ForegroundColorSpan(Color.rgb(26,163,173)),0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        string.setSpan(new ForegroundColorSpan(Color.rgb(62,62,62)),2,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(string);
        TextView notification = (TextView) findViewById(R.id.notification);
        notification.setMovementMethod(LinkMovementMethod.getInstance());
        Button signButton = (Button)findViewById(R.id.sign_button);
        signButton.setOnClickListener(view->{
            Intent intent = new Intent(SplashActivity.this,RegistrationActivity.class);
            startActivity(intent);
        });
    }
}
