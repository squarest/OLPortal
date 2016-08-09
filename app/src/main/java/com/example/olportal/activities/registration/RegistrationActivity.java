package com.example.olportal.activities.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olportal.R;
import com.example.olportal.User;
import com.example.olportal.activities.password.PasswordActivity;
import com.example.olportal.countrySpinner.Country;
import com.example.olportal.countrySpinner.CountryAdapter;
import com.example.olportal.databinding.ActivityRegistrationBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity implements IRegistrationView {
    private PhoneNumberFormattingTextWatcher textWatcher;
    private ActivityRegistrationBinding binding;
    private String number;
    private ProgressDialog progressDialog;
    public final static String USER_EXTRA="user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        binding.setEnable(false);
        RegistrationPresenter presenter = new RegistrationPresenter(this);
        setSpinner();
        createToolbar();
        binding.numberButton.setOnClickListener(view -> {
            number = createNumberWithIso(binding.numberEditText);
            presenter.sendSms(number);

        });
        binding.codeButton.setOnClickListener(v -> {
            presenter.verifyNumber(number, binding.codeEditText.getText().toString());
        });
    }

    private void createToolbar() {
        Toolbar toolbar = binding.regToolbar;
        toolbar.setTitle("Регистрация");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setSpinner() {
        binding.countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country c = (Country) binding.countrySpinner.getItemAtPosition(position);
                binding.regionCode.setText(c.getCountryCodeStr());
                EditText numberEditText = binding.numberEditText;
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
        mAdapter.addAll(loadCountries());
        binding.countrySpinner.setAdapter(mAdapter);
    }

    private ArrayList<Country> loadCountries() {
        ArrayList<Country> countries = new ArrayList<Country>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.getApplicationContext().getAssets().open("countries.dat"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                Country c = new Country(this, line);
                countries.add(c);
                i++;
            }
        } catch (IOException e) {
            Log.d("TAG", e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d("TAG", e.getMessage());
                }
            }
        }
        return countries;
    }

    private String createNumberWithIso(EditText numberEditText) {
        String number = binding.regionCode.getText().toString();
        number += numberEditText.getText().toString().replaceAll("\\D", "");
        return number;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void numberIsValid(boolean enabled) {
        binding.setEnable(enabled);
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "Идет отправка", "Пожалуйста подождите", false, false);
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void setNumberError() {
        binding.numberTextLayout.setError("Номер уже занят");
    }

    @Override
    public void dismissNumberError() {
        binding.numberTextLayout.setError("");
    }

    @Override
    public void setCodeError() {
        binding.codeTextLayout.setError("Неверный код");
    }

    @Override
    public void dismissCodeError() {
        binding.codeTextLayout.setError("");
    }

    @Override
    public void goToNextActivity(User user) {
        Intent intent = new Intent(RegistrationActivity.this, PasswordActivity.class);
        intent.putExtra(USER_EXTRA,user);
        startActivity(intent);

    }

}
