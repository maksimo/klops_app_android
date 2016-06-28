package ru.klops.klops.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.klops.klops.R;
import ru.klops.klops.models.feed.News;

public class PopularDataNewsFragment extends Fragment {
    final String LOG = "PopularDataFragment";
    View fragmentView;
    RecyclerView newPopularRecycler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.popular_data_fragment, container, false);
        initViews();
        Log.d(LOG, "onCreateView");
        return fragmentView;
    }

    private void initViews() {
        Log.d(LOG, "initialize layers");
//        newPopularRecycler = (RecyclerView) fragmentView.findViewById(R.id.recycler_new);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        newPopularRecycler.setLayoutManager(manager);

//        ArrayList<News> popular;
//        RVPopularDataAdapter adapter = new RVPopularDataAdapter(PopularDataNewsFragment.this, popular);
//        newPopularRecycler.setAdapter(adapter);
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
    public void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }
}
