package ru.klops.klops.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
    Shader textShader;
    Shader first;
    Shader second;
    HomeActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mApp = KlopsApplication.getINSTANCE();
        activity = (HomeActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.base_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        activity.setSupportActionBar(baseToolbar);
        Log.d(LOG, "onCreateView");
        setUpAnim();
        setUpGradients();
        setUpTab();
        return fragmentView;
    }

    private void setUpGradients() {
        textShader = new LinearGradient(0, 0, 0, 0,
                new int[]{Color.BLACK, Color.WHITE},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        first = new LinearGradient(0, 150, 10, 0,
                new int[]{Color.WHITE, Color.BLACK},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        second = new LinearGradient(0, 0, 150, 10,
                new int[]{Color.BLACK, Color.WHITE},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
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
                changeTabsFont();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (activity.getRefreshStatus() == false) {
                    activity.scrollList();
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                layout.getTabAt(position);
                if (position == 0){
                    changeTabsFont();
                    layout.getTabAt(0).isSelected();
                }else {
                    changeTabsFont();
                    layout.getTabAt(1).isSelected();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
                    if (vgTab.getChildAt(i).isSelected()) {
                        ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                        ((TextView) tabViewChild).getPaint().setShader(textShader);
                    } else if (!vgTab.getChildAt(i).isSelected()) {
                        ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                        ((TextView) tabViewChild).getPaint().setShader(second);
                    } else if (viewPager.getCurrentItem() == 1) {
                        ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                        ((TextView) tabViewChild).getPaint().setShader(first);
                    }

                }
            }
        }
    }

    @OnClick(R.id.search_action)
    public void searchAction() {
        btnSearch.startAnimation(alpha);
        viewPager.startAnimation(fadeIn);
        activity.replaceFragmentFadeIn(new SearchFragment());
    }

    @OnClick(R.id.settings_action)
    public void settingsAction() {
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