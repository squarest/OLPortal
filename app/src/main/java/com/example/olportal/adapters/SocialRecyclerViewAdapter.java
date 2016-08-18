package com.example.olportal.adapters;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olportal.R;
import com.example.olportal.activities.drawer.MainActivity;
import com.example.olportal.model.SocialNetwork;
import com.example.olportal.activities.drawer.SocialNetworksBottomSheetFragment;
import com.example.olportal.databinding.BackDrawerListItemBinding;

import java.util.List;

public class SocialRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SocialNetwork> socialNetworks;
    private static final int FOOTER_VIEW = 1;
    private TextView addButtonCollapsed;
    private TextView addButtonExpanded;
    public boolean editFlag = false;
    private Context context;
    private Activity activity;

    public SocialRecyclerViewAdapter(Activity activity, Context context, List<SocialNetwork> socialNetworks) {
        this.activity = activity;
        this.context = context;
        this.socialNetworks = socialNetworks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        if (i == FOOTER_VIEW) {
            View footerView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.social_list_footer, viewGroup, false);
            addButtonCollapsed = (TextView) footerView.findViewById(R.id.add_button_collapsed);
            addButtonExpanded = (TextView) footerView.findViewById(R.id.add_button_expanded);
            return new FooterViewHolder(footerView);
        }

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        BackDrawerListItemBinding binding = BackDrawerListItemBinding.inflate(inflater, viewGroup, false);

        return new NormalViewHolder(binding.getRoot());
    }

    public void slideButton(float slideOffset, int width) {
        float fade = 1 - slideOffset * 3;
        if (fade >= 0) addButtonCollapsed.setAlpha(fade);
        else addButtonCollapsed.setAlpha(0f);
        addButtonCollapsed.setRotation(slideOffset * 3 * 360);
        addButtonCollapsed.setTranslationX(slideOffset * width);
        addButtonExpanded.setAlpha(slideOffset);

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {

            super(itemView);
            TextView addButtonExpanded = (TextView) itemView.findViewById(R.id.add_button_expanded);
            TextView addButtonCollapsed = (TextView) itemView.findViewById(R.id.add_button_collapsed);
            addButtonExpanded.setAlpha(0f);
            TextView.OnClickListener addButtonClickListener = v -> {
                BottomSheetDialogFragment bottomSheetDialogFragment = new SocialNetworksBottomSheetFragment();
                bottomSheetDialogFragment.show(((MainActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            };
            addButtonCollapsed.setOnClickListener(addButtonClickListener);
            addButtonExpanded.setOnClickListener(addButtonClickListener);

        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        private BackDrawerListItemBinding binding;

        public NormalViewHolder(View v) {
            super(v);
            this.binding = DataBindingUtil.bind(v);
        }

        public void showMode(SocialNetwork socialNetwork) {

            binding.deleteSocialNetwork.setClickable(editFlag);
            binding.deleteSocialNetwork.animate().alpha(0f);
            binding.socialIcon.setAlpha(0.3f);
            binding.socialIcon.setImageDrawable(socialNetwork.icon);
            binding.socialIcon.animate().alpha(1f);
            binding.setSNetwork(socialNetwork);
        }

        public void editMode(SocialNetwork socialNetwork) {
            binding.deleteSocialNetwork.setClickable(editFlag);
            binding.deleteSocialNetwork.animate().alpha(1f);
            binding.deleteSocialNetwork.setOnClickListener(v->
            {
                socialNetworks.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            });
            binding.socialIcon.setAlpha(0.3f);
            binding.socialIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.edit_button));
            binding.socialIcon.animate().alpha(1f);
            binding.setSNetwork(socialNetwork);

        }
    }


    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof NormalViewHolder) {
            SocialNetwork socialNetwork = socialNetworks.get(i);
            if (!editFlag) {
                ((NormalViewHolder) viewHolder).showMode(socialNetwork);
            } else {
                ((NormalViewHolder) viewHolder).editMode(socialNetwork);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (socialNetworks == null) {
            return 0;
        }
        if (socialNetworks.size() == 0) {
            return 1;
        }
        return socialNetworks.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == socialNetworks.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }
}
