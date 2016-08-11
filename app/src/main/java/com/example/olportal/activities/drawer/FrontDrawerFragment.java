package com.example.olportal.activities.drawer;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olportal.R;

/**
 * Created by Chudofom on 10.08.16.
 */
public class FrontDrawerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.front_drawer, container, false);
        MainActivity mainActivity = ((MainActivity)getActivity());
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.drawer_toolbar);
        toolbar.setTitle("Мой профиль");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.drawer_menu);
        toolbar.setOnMenuItemClickListener(item ->
        {
            mainActivity.drawerLayout.closeDrawer(Gravity.START);
            mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            return false;
        });
        return view;
    }

}
