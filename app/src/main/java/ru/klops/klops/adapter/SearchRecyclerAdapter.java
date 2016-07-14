package ru.klops.klops.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.fragments.SearchFragment;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.search.News;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    ArrayList<News> models;
    SearchFragment context;
    Animation alpha;
    String keyword;

    public SearchRecyclerAdapter(SearchFragment context, ArrayList<News> models, String keyword) {
        this.models = models;
        this.context = context;
        this.keyword = keyword;
        alpha = AnimationUtils.loadAnimation(context.getContext(), R.anim.alpha);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.articleAuthor)
        TextView author;
        @BindView(R.id.articleDate)
        TextView date;
        @BindView(R.id.articleTitle)
        TextView title;
        @BindView(R.id.articleText)
        TextView text;
        @BindView(R.id.resizedLayer)
        RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_card, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.author.setText(models.get(position).getAuthor());
        viewHolder.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        viewHolder.date.setText(models.get(position).getDate());
        viewHolder.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        viewHolder.title.setText(models.get(position).getTitle());
        viewHolder.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        viewHolder.text.setText(models.get(position).getShortdecription());
        viewHolder.text.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.layout.startAnimation(alpha);
                PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
                Observable<Article> call = api.getItemById(models.get(viewHolder.getAdapterPosition()).getId());
                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Article>() {
                            @Override
                            public void onCompleted() {
                                Log.d("SearchAdapter", "Loading search article complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("SearchAdapter", "Loading failed");
                            }

                            @Override
                            public void onNext(Article article) {
                                Intent articleIntent = new Intent(context.getContext(), ArticleActivity.class);
                                articleIntent.putExtra(Constants.ITEM, article.getItem());
                                context.startActivity(articleIntent);
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}