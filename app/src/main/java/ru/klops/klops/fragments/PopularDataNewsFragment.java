package ru.klops.klops.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.R;
import ru.klops.klops.adapter.ItemOffsetDecoration;
import ru.klops.klops.adapter.RVPopularDataAdapter;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.models.feed.Page;

public class PopularDataNewsFragment extends Fragment {
    final String LOG = "PopularDataFragment";
    View fragmentView;
    @BindView(R.id.recyclerPopular)
    RecyclerView newPopularRecycler;
    KlopsApplication mApp;
    RVPopularDataAdapter adapter;
    ArrayList<News> models;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(LOG, "onAttach");
        mApp = KlopsApplication.getINSTANCE();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.popular_data_fragment, container, false);
        unbinder = ButterKnife.bind(fragmentView);
//        initRecyclerView();
        Log.d(LOG, "onCreateView");
        return fragmentView;
    }

    private void initRecyclerView() {
        Log.d(LOG, "initRecyclerView");
        Page loadedFirstPage = mApp.getFirstPage();
        models = new ArrayList<>();
        models.addAll(loadedFirstPage.getNews());
        ArrayList<News> copy = new ArrayList<>(models);
        adapter = new RVPopularDataAdapter(PopularDataNewsFragment.this, copy);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        ItemOffsetDecoration decoration = new ItemOffsetDecoration(getContext(), R.dimen.popular);
        newPopularRecycler.addItemDecoration(decoration);
        newPopularRecycler.setLayoutManager(manager);
        newPopularRecycler.setAdapter(adapter);
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
