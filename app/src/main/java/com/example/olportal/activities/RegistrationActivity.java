package com.example.olportal.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.example.olportal.ConnectionToServer;
import com.example.olportal.R;
import com.example.olportal.countrySpinner.Country;
import com.example.olportal.countrySpinner.CountryAdapter;
import com.example.olportal.databinding.ActivityRegistrationBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistrationActivity extends AppCompatActivity {
    private ArrayList<Country> countries = new ArrayList<Country>();
    private PhoneNumberFormattingTextWatcher textWatcher;
    private ActivityRegistrationBinding binding;
    private String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_registration);
        setSpinner();
        createToolbar();
        binding.numberButton.setOnClickListener(view ->
        {

            Map<String, String> map = new HashMap<>();
            number=createNumberWithIso(binding.numberEditText);
            map.put("phone",number);
            ConnectionToServer.getInstance().smsSend(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(v ->
                    {
                        Toast.makeText(RegistrationActivity.this, "success", Toast.LENGTH_SHORT).show();
                        binding.codeButton.setEnabled(true);
                        binding.codeEditText.setEnabled(true);
                    }, throwable ->
                    {
                        Log.d("TAG", "error " + throwable.getMessage());
                        binding.codeButton.setEnabled(false);
                        binding.codeEditText.setEnabled(false);
                    });
        });
        binding.codeButton.setOnClickListener(v -> {

            Map<String, String> codeMap = new HashMap<>();
            codeMap.put("phone",number);
            codeMap.put("code",binding.codeEditText.getText().toString());
            ConnectionToServer.getInstance().smsVerify(codeMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(v1 ->
                    {
                        Toast.makeText(RegistrationActivity.this, "code success", Toast.LENGTH_SHORT).show();
                    }, throwable -> Log.d("TAG", "error " + throwable.getMessage()));
        });
    }
    private void createToolbar()
    {
        Toolbar toolbar = binding.regToolbar;
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(v->onBackPressed());
    }
    private void setSpinner() {
        binding.countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country c = (Country) binding.countrySpinner.getItemAtPosition(position);
                binding.regionCode.setText(c.getCountryCodeStr());
                EditText numberEditText=binding.numberEditText;
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
        binding.countrySpinner.setAdapter(mAdapter);
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

    private String createNumberWithIso(EditText numberEditText) {
        String number = numberEditText.getText().toString();
        char[] digits = number.toCharArray();
        number = binding.regionCode.getText().subSequence(0, binding.regionCode.length()).toString();
        for (char digit : digits) {
            if (Character.isDigit(digit)) number += digit;
        }
        Log.d("TAG", "converted number " + number);
        return number;
    }
}
