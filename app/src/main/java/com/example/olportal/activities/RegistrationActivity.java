package com.example.olportal.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olportal.R;
import com.example.olportal.mask.MaskedWatcher;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EditText numberEditText = (EditText) findViewById(R.id.number_edit_text);
        numberEditText.addTextChangedListener(new MaskedWatcher("(###) ###-##-##"));
        Button sendNumberButton = (Button) findViewById(R.id.send_number_button);
        sendNumberButton.setOnClickListener(view -> {
            createNumber(numberEditText);
        });

    }

    private void createNumber(EditText numberEditText) {
        String number = numberEditText.getText().toString();
        char[] digits = number.toCharArray();
        TextView regionCode = (TextView)findViewById(R.id.region_code);
        number=regionCode.getText().subSequence(1,regionCode.length()).toString();
        for (char digit : digits) {
            if(Character.isDigit(digit))number += digit;
        }
        Toast.makeText(RegistrationActivity.this, number, Toast.LENGTH_SHORT).show();
    }
}
