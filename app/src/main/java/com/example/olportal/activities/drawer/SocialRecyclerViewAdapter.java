package com.example.olportal.activities.drawer;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olportal.databinding.BackDrawerListItemBinding;

import java.util.List;

public class SocialRecyclerViewAdapter extends RecyclerView.Adapter<SocialRecyclerViewAdapter.ViewHolder> {


    private List<SocialNetwork> socialNetworks;

    public SocialRecyclerViewAdapter(List<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        BackDrawerListItemBinding binding = BackDrawerListItemBinding.inflate(inflater, viewGroup, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        SocialNetwork socialNetwork = socialNetworks.get(i);
        viewHolder.setItem(socialNetwork);
    }

    @Override
    public int getItemCount() {
        return socialNetworks.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private BackDrawerListItemBinding binding;

        public ViewHolder(View v) {
            super(v);
            this.binding = DataBindingUtil.bind(v);
        }

        public void setItem(SocialNetwork socialNetwork) {
            binding.setSNetwork(socialNetwork);
        }
    }
}