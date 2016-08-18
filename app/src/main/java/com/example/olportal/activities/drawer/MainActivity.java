package com.example.olportal.activities.drawer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.olportal.ConnectionToServer;
import com.example.olportal.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private HashMap<String, List<Integer>> listImageId;
    private FloatingActionButton floatingButton;
    private SocialRecyclerViewAdapter adapter;
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDoubleDrawer();
        createListView();
        createExpandableListView();

        FacebookSdk.sdkInitialize(this);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("tag", "facebookSuccess");
                AccessToken token = AccessToken.getCurrentAccessToken();
                FacebookRequest request = new FacebookRequest();
                request.accessToken = token.getToken();
                request.expires = String.valueOf(token.getExpires().getTime());
                request.permissions = token.getPermissions();
                request.userSocialId = token.getUserId();
                ConnectionToServer.getInstance().addFacebook(request, "Bearer " + getIntent().getStringExtra("token"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(a ->
                        {
                            Log.d("TAG", "success");
                        }, throwable -> Log.d("error ", throwable.getMessage()));


            }

            @Override
            public void onCancel() {
                Log.d("TAG", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "error");
            }
        });


    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        adapter = new SocialRecyclerViewAdapter(this, this, populateList());
        recyclerView.setAdapter(adapter);
        createFloatingButton();

    }

    private void createFloatingButton() {

        floatingButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingButton.hide();
        floatingButton.setOnClickListener(v ->
        {
            if (!adapter.editFlag) {
                adapter.editFlag = true;
                floatingButton.setAlpha(0.3f);
                floatingButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.background)));
                floatingButton.setImageDrawable(getResources().getDrawable(R.drawable.ok_ic));
                floatingButton.animate().alpha(1f);
            } else {
                adapter.editFlag = false;
                floatingButton.setAlpha(0.3f);
                floatingButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                floatingButton.setImageDrawable(getResources().getDrawable(R.drawable.edit_ic));
                floatingButton.animate().alpha(1f);
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void createDoubleDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingLayout);
        slidingPaneLayout.setSliderFadeColor(getResources().getColor(R.color.front_drawer));
        FloatingActionButton closePanelButton = (FloatingActionButton) findViewById(R.id.showSlidePanel);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset > 0.8f) {
                    floatingButton.show();
                } else {
                    floatingButton.hide();
                }
                if (slideOffset < 1) closePanelButton.hide();
                adapter.slideButton(slideOffset, drawerLayout.getWidth() / 2);
            }

            @Override
            public void onPanelOpened(View panel) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                closePanelButton.show();
                closePanelButton.animate().alpha(1f);
            }

            @Override
            public void onPanelClosed(View panel) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                adapter.editFlag = !adapter.editFlag;
//                adapter.notifyDataSetChanged();


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
            socialNetwork.icon = getResources().getDrawable(R.drawable.vk_ic);
            socialNetwork.name = "Вконтакте " + i;
            socialNetwork.userName = "+79882457845";
            socialNetworks.add(socialNetwork);
        }
        return socialNetworks;
    }
}
