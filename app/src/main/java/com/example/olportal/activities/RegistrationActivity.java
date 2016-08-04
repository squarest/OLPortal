package com.example.olportal.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olportal.R;
import com.example.olportal.countrySpinner.Country;
import com.example.olportal.countrySpinner.CountryAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {
    private ArrayList<Country> countries = new ArrayList<Country>();
    private Spinner spinner;
    private EditText numberEditText;
    private PhoneNumberFormattingTextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        numberEditText = (EditText) findViewById(R.id.number_edit_text);
        Button sendNumberButton = (Button) findViewById(R.id.send_number_button);
        sendNumberButton.setOnClickListener(view -> {
            createNumberWithIso(numberEditText);
        });
        setSpinner();


    }

    private void setSpinner() {
        spinner = (Spinner) findViewById(R.id.country_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country c = (Country) spinner.getItemAtPosition(position);
                TextView countryCodeTextView = (TextView) findViewById(R.id.region_code);
                countryCodeTextView.setText(c.getCountryCodeStr());
                numberEditText.removeTextChangedListener(textWatcher);
                final String txt = numberEditText.getText().toString().replaceAll("\\D+", "");
                numberEditText.getText().clear();
                textWatcher = new PhoneNumberFormattingTextWatcher(c.getCountryISO());
                numberEditText.addTextChangedListener(textWatcher);
                numberEditText.post(() -> numberEditText.setText(txt));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CountryAdapter mAdapter = new CountryAdapter(this);
        loadCountries();
        mAdapter.addAll(countries);
        spinner.setAdapter(mAdapter);
    }

    private void loadCountries() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("countries.dat"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                //process line
                Country c = new Country(this, line);
                countries.add(c);
                i++;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    private void createNumberWithIso(EditText numberEditText) {
        String number = numberEditText.getText().toString();
        char[] digits = number.toCharArray();
        TextView regionCode = (TextView) findViewById(R.id.region_code);
        number = regionCode.getText().subSequence(1, regionCode.length()).toString();
        for (char digit : digits) {
            if (Character.isDigit(digit)) number += digit;
        }
        Toast.makeText(RegistrationActivity.this, number, Toast.LENGTH_SHORT).show();
    }
}
