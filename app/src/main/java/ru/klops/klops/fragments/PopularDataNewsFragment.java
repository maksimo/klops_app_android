package ru.klops.klops.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.klops.klops.R;
import ru.klops.klops.adapter.ItemOffsetDecoration;
import ru.klops.klops.adapter.RVPopularDataAdapter;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.models.popular.News;
import ru.klops.klops.models.popular.Popular;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PopularDataNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    final String LOG = "PopularDataFragment";
    View fragmentView;
    @BindView(R.id.recyclerPopular)
    RecyclerView newPopularRecycler;
    @BindView(R.id.popularError)
    TextView popularError;
    @BindView(R.id.swipeLayoutPopular)
    SwipeRefreshLayout refreshLayout;
    KlopsApplication mApp;
    RVPopularDataAdapter adapter;
    GridLayoutManager popularManager;
    ArrayList<News> models;
    ArrayList<Integer> dataTypes;
    ArrayList<News> copy;
    Unbinder unbinder;
    private Tracker mTracker;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(LOG, "onAttach");
        mApp = KlopsApplication.getINSTANCE();
        mTracker = mApp.getDefaultTracker();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.popular_data_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        initRecyclerView();
        refreshLayout.setOnRefreshListener(this);
        Log.d(LOG, "onCreateView");
        return fragmentView;
    }

    @Override
    public void onRefresh() {
        adapter.clearPopularFeed();
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Observable<Popular> refreshPage = api.getPopularNews();
        refreshPage.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Popular>() {
                    @Override
                    public void onCompleted() {
                        refreshLayout.setRefreshing(false);
                        Log.d(LOG, "Refresh main feed complete...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG, "Access denied" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Popular popular) {
                        mApp.setPopularPage(popular);
                        adapter.addData(new ArrayList<News>(popular.getNews()), addData(new ArrayList<News>(popular.getNews())));
                        adapter.notifyDataSetChanged();
                    }
                });
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Popular Feed Action")
                .setAction("Refresh Popular Feed")
                .build());
    }

    private void initRecyclerView() {
        Log.d(LOG, "initRecyclerView");
        models = new ArrayList<>();
        if (mApp.getPopularPage() != null) {
            models.addAll(mApp.getPopularPage().getNews());
            copy = new ArrayList<>(models);
            dataTypes = new ArrayList<>();
            dataTypes.addAll(addData(copy));
            ItemOffsetDecoration decoration = new ItemOffsetDecoration(getContext(), R.dimen.top_bottom);
            newPopularRecycler.addItemDecoration(decoration);
            adapter = new RVPopularDataAdapter(PopularDataNewsFragment.this, copy, dataTypes);
            popularManager = new GridLayoutManager(getContext(), 2);
            popularManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
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
            newPopularRecycler.setLayoutManager(popularManager);
            newPopularRecycler.setAdapter(adapter);
        } else {
            newPopularRecycler.setVisibility(View.GONE);
            if (!Constants.BASE_API_URL.equals("https://klops.ru/api/")) {
                popularError.setText("Настройки приложения были изменены");
            }
            popularError.setVisibility(View.VISIBLE);
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

    public void scrollPopularToTop() {
        popularManager.scrollToPosition(0);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Popular Feed Action")
                .setAction("Scroll To Top Of Popular Feed")
                .build());
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
