package com.example.olportal.activities.drawer;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.olportal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kravchenko on 17/08/16.
 */
public class SocialNetworksBottomSheetFragment extends BottomSheetDialogFragment {
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.social_network_bottom_sheet_fragment, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sn_bottom_sheet_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(new SocialNetworksBottomSheetAdapter(populateBottomSheet()));
        dialog.setContentView(view);

    }
    private List<SocialNetwork> populateBottomSheet()
    {
        List<SocialNetwork> socialNetworks = new ArrayList<>();
        socialNetworks.add(new SocialNetwork(getActivity().getResources().getDrawable(R.drawable.vk_ic)));
        socialNetworks.add(new SocialNetwork(getActivity().getResources().getDrawable(R.drawable.fb_ic)));
        socialNetworks.add(new SocialNetwork(getActivity().getResources().getDrawable(R.drawable.instagram_ic)));
        socialNetworks.add(new SocialNetwork(getActivity().getResources().getDrawable(R.drawable.twitter_ic)));
        socialNetworks.add(new SocialNetwork(getActivity().getResources().getDrawable(R.drawable.skype_ic)));
        socialNetworks.add(new SocialNetwork(getActivity().getResources().getDrawable(R.drawable.telegram_ic)));
        socialNetworks.add(new SocialNetwork(getActivity().getResources().getDrawable(R.drawable.odnoklassniki_ic)));
        return  socialNetworks;
    }
}
