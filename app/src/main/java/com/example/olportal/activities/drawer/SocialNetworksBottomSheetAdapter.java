package com.example.olportal.activities.drawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.olportal.R;

import java.util.List;

/**
 * Created by kravchenko on 17/08/16.
 */
public class SocialNetworksBottomSheetAdapter extends RecyclerView.Adapter<SocialNetworksBottomSheetAdapter.ViewHolder> {
    private List<SocialNetwork> socialNetworks;

    public SocialNetworksBottomSheetAdapter(List<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.sn_bottom_sheet_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SocialNetwork socialNetwork = socialNetworks.get(position);
        holder.setItem(socialNetwork);

    }

    @Override
    public int getItemCount() {
        return socialNetworks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.social_network_icon);
        }

        public void setItem(SocialNetwork socialNetwork) {
            icon.setImageDrawable(socialNetwork.icon);

        }
    }
}
