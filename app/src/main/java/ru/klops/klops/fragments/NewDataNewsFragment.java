package ru.klops.klops.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.R;
import ru.klops.klops.adapter.ItemOffsetDecoration;
import ru.klops.klops.adapter.RVNewDataAdapter;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.feed.Currency;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.utils.Constants;

public class NewDataNewsFragment extends Fragment {
    final String LOG = "NewDataFragment";
    View fragmentView;
    @BindView(R.id.recycler_new)
    RecyclerView newDataRecycler;
    @BindView(R.id.newError)
    TextView newError;
    Unbinder unbinder;
    KlopsApplication mApp;
    RVNewDataAdapter adapter;
    GridLayoutManager newManager;
    ArrayList<News> models;
    ArrayList<News> copy;
    ArrayList<Integer> typesAdapter;
    Currency currency;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(LOG, "onAttach");
        mApp = KlopsApplication.getINSTANCE();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.new_data_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        setUpRecycler();
        Log.d(LOG, "onCreateView");
        return fragmentView;
    }

    private void setUpRecycler() {
        Log.d(LOG, "setUpRecycler");
        if (mApp.getFirstPage() != null) {
            Page loadedFirstPage = mApp.getFirstPage();
            models = new ArrayList<>();
            models.addAll(loadedFirstPage.getNews());
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setRemoveDuration(1500);
            currency = new Currency();
            currency.setEur(loadedFirstPage.getCurrency().getEur());
            currency.setUsd(loadedFirstPage.getCurrency().getUsd());
            currency.setPln(loadedFirstPage.getCurrency().getPln());
            copy = new ArrayList<>();
            copy.addAll(models);
            typesAdapter = new ArrayList<>();
            typesAdapter.addAll(addData(copy));
            adapter = new RVNewDataAdapter(NewDataNewsFragment.this, copy, typesAdapter, currency);
            ItemOffsetDecoration decoration = new ItemOffsetDecoration(getContext(), R.dimen.top_bottom);
            newDataRecycler.addItemDecoration(decoration);
            newDataRecycler.setItemAnimator(itemAnimator);
            newManager = new GridLayoutManager(getContext(), 2);
            newManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (adapter.getItemViewType(position)) {
                        case Constants.SIMPLE_WITH_IMG:
                            return 1;
                        case Constants.SIMPLE_TEXT_NEWS:
                            return 1;
                        case Constants.LONG:
                            return 2;
                        case Constants.INTERVIEW:
                            return 2;
                        case Constants.AUTHORS:
                            return 2;
                        case Constants.NATIONAL:
                            return 2;
                        case Constants.IMPORTANT:
                            return 2;
                        case Constants.GALLERY_ONE:
                            return 2;
                        case Constants.GALLERY_TWO:
                            return 2;
                        case Constants.ADVERTISE:
                            return 2;
                        case Constants.SIMPLE_WIDE:
                            return 2;
                        case Constants.POPULAR_MARKER:
                            return 2;
                        case Constants.SEPARATOR:
                            return 2;
                        case Constants.EXCHANGE:
                            return 2;
                        case Constants.URGENT:
                            return 2;
                        default:
                            return 0;
                    }
                }
            });
            newDataRecycler.setLayoutManager(newManager);
            newDataRecycler.setAdapter(adapter);
        } else {
            newDataRecycler.setVisibility(View.GONE);
            if (!Constants.BASE_API_URL.equals("https://klops.ru/api/")) {
                newError.setText("Настройки приложения были изменены");
            }
            newError.setVisibility(View.VISIBLE);
        }
    }

    private ArrayList<Integer> addData(ArrayList<News> copy) {
        ArrayList<Integer> types = new ArrayList<>();
        for (int i = 0; i < copy.size(); i++) {
            String article = copy.get(i).getArticle_type();
            switch (article) {
                case Constants.SIMPLE_IMAGE_TEXT:
                    types.add(Constants.SIMPLE_WITH_IMG);
                    break;
                case Constants.SIMPLE_TEXT:
                    types.add(Constants.SIMPLE_TEXT_NEWS);
                    break;
                case Constants.LONG_TEXT:
                    types.add(Constants.LONG);
                    break;
                case Constants.INTERVIEW_TEXT:
                    types.add(Constants.INTERVIEW);
                    break;
                case Constants.AUTHORS_TEXT:
                    types.add(Constants.AUTHORS);
                    break;
                case Constants.NATIONAL_TEXT:
                    types.add(Constants.NATIONAL);
                    break;
                case Constants.IMPORTANT_TEXT:
                    types.add(Constants.IMPORTANT);
                    break;
                case Constants.GALLERY_FIRST_TEXT:
                    types.add(Constants.GALLERY_ONE);
                    break;
                case Constants.GALLERY_SECOND_TEXT:
                    types.add(Constants.GALLERY_TWO);
                    break;
                case Constants.ADS_TEXT:
                    types.add(Constants.ADVERTISE);
                    break;
                case Constants.SIMPLE_WIDE_TEXT:
                    types.add(Constants.SIMPLE_WIDE);
                    break;
                case Constants.POPULAR_MARKER_TEXT:
                    types.add(Constants.POPULAR_MARKER);
                    break;
                case Constants.SEPARATOR_TEXT:
                    types.add(Constants.SEPARATOR);
                    break;
                case Constants.EXCHANGE_TEXT:
                    types.add(Constants.EXCHANGE);
                    break;
                case Constants.URGENT_TEXT:
                    types.add(Constants.URGENT);
                    break;
            }
        }
        return types;
    }

    public void scrollNewToTop() {
        newManager.scrollToPosition(0);
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
