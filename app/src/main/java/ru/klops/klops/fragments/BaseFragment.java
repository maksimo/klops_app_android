package ru.klops.klops.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.klops.klops.HomeActivity;
import ru.klops.klops.R;
import ru.klops.klops.SettingsActivity;
import ru.klops.klops.adapter.SlideAdapter;
import ru.klops.klops.application.KlopsApplication;

public class BaseFragment extends Fragment {
    final String LOG = "SearchFragment";
    View fragmentView;
    @BindView(R.id.news_pager)
    ViewPager viewPager;
    @BindView(R.id.news_tab)
    TabLayout layout;
    @BindView(R.id.settings_action)
    ImageView btnSettings;
    @BindView(R.id.search_action)
    ImageView btnSearch;
    @BindView(R.id.baseToolbar)
    Toolbar baseToolbar;
    SlideAdapter adapter;
    Animation alpha;
    Animation fadeIn;
    Animation fadeOut;
    Unbinder unbinder;
    KlopsApplication mApp;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mApp = KlopsApplication.getINSTANCE();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.base_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        ((HomeActivity)getActivity()).setSupportActionBar(baseToolbar);
        Log.d(LOG, "onCreateView");
        setUpAnim();
        setUpTab();
        return fragmentView;
    }

    private void setUpAnim() {
        Log.d(LOG, "setUpAnim");
        alpha = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
    }

    private void setUpTab() {
        Log.d(LOG, "setUpTab");
        layout.addTab(layout.newTab().setText(" Новое "));
        layout.addTab(layout.newTab().setText(" Популярное "));
        changeTabsFont();
        layout.setSelectedTabIndicatorHeight(0);
        adapter = new SlideAdapter(getFragmentManager(), 2);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(layout));
        layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                ((HomeActivity)getActivity()).scrollList();
            }
        });
        viewPager.startAnimation(fadeIn);
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) layout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                }
            }
        }
    }

    @OnClick(R.id.search_action)
    public void searchAction(){
        btnSearch.startAnimation(alpha);
        viewPager.startAnimation(fadeIn);
        ((HomeActivity)getActivity()).replaceFragmentFadeIn(new SearchFragment());
    }

    @OnClick(R.id.settings_action)
    public void setttingsAction(){
        btnSettings.startAnimation(alpha);
        startActivity(new Intent(getContext(), SettingsActivity.class));
    }

    @Override
    public void onStart() {
        Log.d(LOG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(LOG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(LOG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(LOG, "onStop");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.d(LOG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.d(LOG, "onDestroyView");
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }
}