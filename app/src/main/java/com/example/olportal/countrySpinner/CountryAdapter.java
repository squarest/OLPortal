/*
 * Copyright (c) 2014-2015 Amberfog.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.olportal.countrySpinner;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olportal.R;
import com.example.olportal.databinding.ActivityRegistrationBinding;
import com.example.olportal.databinding.ItemCountryDropBinding;

public class CountryAdapter extends ArrayAdapter<Country> {
    private LayoutInflater mLayoutInflater;

    public CountryAdapter(Context context) {
        super(context, 0);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ItemCountryDropBinding binding =
                DataBindingUtil.inflate(mLayoutInflater, R.layout.item_country_drop, parent, false);
        Country country = getItem(position);
        if (country != null) {
            binding.countryName.setText(country.getName());
            binding.countryCode.setText(country.getCountryCodeStr());
            binding.image.setImageResource(country.getResId());
        }
        return binding.getRoot();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Country country = getItem(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_country, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        imageView.setImageResource(country.getResId());
        return convertView;
    }
}
