package ru.klops.klops.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.klops.klops.HomeActivity;
import ru.klops.klops.R;
import ru.klops.klops.adapter.ItemOffsetDecoration;
import ru.klops.klops.adapter.SearchRecyclerAdapter;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.application.KlopsApplication;
import ru.klops.klops.models.search.News;
import ru.klops.klops.models.search.Search;
import ru.klops.klops.services.RetrofitServiceGenerator;

public class SearchFragment extends Fragment {
    final String LOG = "SearchFragment";
    private ProgressDialog mProgressDialog;
    View fragmentView;
    @BindView(R.id.search_list)
    RecyclerView viewSearch;
    @BindView(R.id.search_text_visible_one)
    TextView searchOne;
    @BindView(R.id.search_text_visible_two)
    TextView searchTwo;
    @BindView(R.id.toolbar_search)
    Toolbar toolbar;
    @BindView(R.id.search)
    ImageView btnSearch;
    @BindView(R.id.cancel_button)
    ImageView btnCancel;
    @BindView(R.id.search_title)
    EditText searchField;
    Animation alpha;
    Unbinder unbinder;
    HomeActivity activity;
    SearchRecyclerAdapter adapter;
    KlopsApplication mApp;
    List<News> news;
    String requestedWord;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mApp = KlopsApplication.getINSTANCE();
        activity = (HomeActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.search_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        initFonts();
        initProgressDialog();
        alpha = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        Log.d(LOG, "onCreateView");
        return fragmentView;
    }

    private void initFonts() {
        searchOne.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        searchTwo.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        searchField.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity(), R.style.MyDialog);
        mProgressDialog.setIcon(R.drawable.logo_int_settings);
        mProgressDialog.setMessage("Ожадание отклика сервера...");
        mProgressDialog.setTitle("Поиск статей");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
    }


    @OnFocusChange(R.id.search_title)
    public void hideHints() {
        searchField.setHintTextColor(Color.TRANSPARENT);
        searchOne.setVisibility(View.GONE);
        searchTwo.setVisibility(View.GONE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @OnClick(R.id.search)
    public void search() {
        btnSearch.startAnimation(alpha);
        searchOne.setVisibility(View.GONE);
        searchTwo.setVisibility(View.GONE);
        requestedWord = searchField.getText().toString();
        if (requestedWord.equals("") && requestedWord == (null)) {
            searchField.setError("Введите ключевое слово");
        } else {
            InputMethodManager imm = (InputMethodManager) fragmentView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(fragmentView.getWindowToken(), 0);
            searchOne.setVisibility(View.GONE);
            searchTwo.setVisibility(View.GONE);
            startSearch(requestedWord);
        }
    }

    public void startSearch(final String requestedWord) {
        ((HomeActivity) getActivity()).hideKeyboard();
        mProgressDialog.show();
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Call<Search> call = api.getSearchResult(requestedWord, 1);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                mProgressDialog.dismiss();
                news = response.body().getNews();
                setUpRecycler(news);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.d(LOG, "Ошибка доступа к данным...");
            }
        });
    }

    public void setUpRecycler(List<News> news) {
        ArrayList<News> copy = new ArrayList<>();
        copy.addAll(news);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        viewSearch.setLayoutManager(manager);
        ItemOffsetDecoration decoration = new ItemOffsetDecoration(getContext(), R.dimen.top_bottom);
        viewSearch.addItemDecoration(decoration);
        adapter = new SearchRecyclerAdapter(SearchFragment.this, copy, requestedWord);
        viewSearch.setAdapter(adapter);
        viewSearch.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.cancel_button)
    public void cancelAction() {
        btnCancel.startAnimation(alpha);
        InputMethodManager imm = (InputMethodManager) fragmentView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fragmentView.getWindowToken(), 0);
        ((HomeActivity) getActivity()).popBackWithFadeOut(new BaseFragment());
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