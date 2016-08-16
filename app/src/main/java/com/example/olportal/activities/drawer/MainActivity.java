package com.example.olportal.activities.drawer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olportal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private HashMap<String, List<Integer>> listImageId;
    private FloatingActionButton floatingButton;
    private TextView addButtonCollapsed;
    private TextView addButtonExpanded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingButton.hide();
        createDoubleDrawer();
        createListView();
        createExpandableListView();


    }

    private void createExpandableListView() {
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        View v = getLayoutInflater().inflate(R.layout.list_view_header, null);
        createToolbar(v);
        expListView.addHeaderView(v);
        prepareListData();
        ExpandableListAdapter listAdapter = new MyExpandableListAdapter(this, listDataHeader, listDataChild, listImageId);
        expListView.setAdapter(listAdapter);
        expListView.setGroupIndicator(null);
    }

    private void createListView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.socialList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        adapter = new SocialRecyclerViewAdapter(this, populateList());
        recyclerView.setAdapter(adapter);
    }

    private void createDoubleDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingLayout);
        slidingPaneLayout.setSliderFadeColor(getResources().getColor(R.color.front_drawer));
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset > 0.8f) {
                    floatingButton.show();
                } else {
                    floatingButton.hide();
                }
                float fade = 1 - slideOffset * 3;
                if (fade >= 0) addButtonCollapsed.setAlpha(fade);
                else addButtonCollapsed.setAlpha(0f);
                addButtonCollapsed.setRotation(slideOffset * 3 * 360);
                addButtonCollapsed.setTranslationX(slideOffset * drawerLayout.getWidth() / 2);
                addButtonExpanded.setAlpha(slideOffset);
            }

            @Override
            public void onPanelOpened(View panel) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);


            }

            @Override
            public void onPanelClosed(View panel) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            }
        });
    }

    private void createToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.drawer_toolbar);
        toolbar.setTitle("Мой профиль");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.drawer_menu);
        toolbar.setOnMenuItemClickListener(item ->
        {
            //убираем дровер влево
            drawerLayout.closeDrawer(GravityCompat.START);
            //разрешаем скролинг
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            return false;
        });

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listImageId = new HashMap<>();


        listDataHeader.add("Настройки");
        listDataHeader.add("Поддержка");

        // Adding child data
        List<String> settings = new ArrayList<>();
        List<Integer> settingsIcons = new ArrayList<>();
        settings.add("E-mail");
        settingsIcons.add(R.drawable.ic_email);
        settings.add("Номер телефона");
        settingsIcons.add(R.drawable.ic_phone);
        settings.add("Пароль");
        settingsIcons.add(R.drawable.ic_password);
        settings.add("Уведомления");
        settingsIcons.add(R.drawable.ic_notification);
        settings.add("Безопасность");
        settingsIcons.add(R.drawable.ic_security);
        settings.add("Конфиденциальность");
        settingsIcons.add(R.drawable.ic_confidentiality);

        List<String> support = new ArrayList<>();
        List<Integer> suppotIcons = new ArrayList<>();
        support.add("Подсказки");
        suppotIcons.add(R.drawable.ic_tips);
        support.add("Оставить отзыв");
        suppotIcons.add(R.drawable.ic_like);
        support.add("Условия и политика");
        suppotIcons.add(R.drawable.ic_book);
        support.add("О программе");
        suppotIcons.add(R.drawable.ic_question);

        listDataChild.put(listDataHeader.get(0), settings); // Header, Child data
        listDataChild.put(listDataHeader.get(1), support);

        listImageId.put(listDataHeader.get(0), settingsIcons);
        listImageId.put(listDataHeader.get(1), suppotIcons);
    }

    private List<SocialNetwork> populateList() {
        List<SocialNetwork> socialNetworks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SocialNetwork socialNetwork = new SocialNetwork();
            socialNetwork.icon = "https://cdn2.iconfinder.com/data/icons/social-flat-buttons-3/512/vkontakte-256.png";
            socialNetwork.name = "Вконтакте";
            socialNetwork.userName = "+79882457845";
            socialNetworks.add(socialNetwork);
        }
        return socialNetworks;
    }
}
