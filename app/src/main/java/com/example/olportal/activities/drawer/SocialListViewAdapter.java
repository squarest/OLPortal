package com.example.olportal.activities.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.olportal.databinding.BackDrawerListItemBinding;

import java.util.List;

public class SocialListViewAdapter extends BaseAdapter {


    private List<SocialNetwork> socialNetworks;
    private Context context;

    public SocialListViewAdapter(Context context, List<SocialNetwork> socialNetworks) {
        this.context=context;
        this.socialNetworks = socialNetworks;
    }


    @Override
    public int getCount() {
        return socialNetworks.size();
    }

    @Override
    public Object getItem(int position) {
        return socialNetworks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            BackDrawerListItemBinding binding = BackDrawerListItemBinding.inflate(inflater, parent, false);
            binding.setSNetwork(socialNetworks.get(position));
        return binding.getRoot();
    }

}