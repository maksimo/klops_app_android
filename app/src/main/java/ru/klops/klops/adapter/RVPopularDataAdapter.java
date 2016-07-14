package ru.klops.klops.adapter;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.fragments.PopularDataNewsFragment;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RVPopularDataAdapter extends RecyclerView.Adapter<RVPopularDataAdapter.PopularDataViewHolder> {
    PopularDataNewsFragment context;
    ArrayList<News> popular;
    Animation alpha;

    public RVPopularDataAdapter(PopularDataNewsFragment context, ArrayList<News> popular) {
        this.context = context;
        this.popular = popular;
    }

    public class PopularDataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nationalCardAuthor)
        TextView author;
        @BindView(R.id.nationalCardDate)
        TextView dateOne;
        @BindView(R.id.nationalCardTitle)
        TextView titleOne;
        @BindView(R.id.nationalCard)
        RelativeLayout nationalCard;
        public PopularDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public PopularDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_national_news, viewGroup, false);
        PopularDataViewHolder holder = new PopularDataViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PopularDataViewHolder holder, int position) {
        final PopularDataViewHolder holderNational = (PopularDataViewHolder) holder;
        holderNational.author.setText(popular.get(position).getAuthor());
        holderNational.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        holderNational.dateOne.setText(popular.get(position).getDate());
        holderNational.dateOne.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        holderNational.titleOne.setText(popular.get(position).getTitle());
        holderNational.titleOne.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
        holderNational.nationalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holderNational.nationalCard.startAnimation(alpha);
                loadArticle(popular.get(holderNational.getAdapterPosition()).getId());
            }
        });
    }

    public void loadArticle(Integer id) {
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Observable<Article> call = api.getItemById(id);
        call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Log.d("SearchAdapter", "Загружаю статью...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SearchAdapter", "Ошибка при открытии...");
                    }

                    @Override
                    public void onNext(Article article) {
                        Intent articleIntent = new Intent(context.getContext(), ArticleActivity.class);
                        articleIntent.putExtra(Constants.ITEM, article.getItem());
                        context.startActivity(articleIntent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return popular.size();
    }

}
