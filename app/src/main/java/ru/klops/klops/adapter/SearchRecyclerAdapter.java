package ru.klops.klops.adapter;

import android.app.ProgressDialog;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.fragments.SearchFragment;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.search.News;
import ru.klops.klops.models.search.Search;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.services.ServiceArticleLoader;
import ru.klops.klops.utils.Constants;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    ArrayList<News> models;
    SearchFragment context;
    Animation alpha;
    ProgressDialog mProgressDialog;
    Item article;

    public SearchRecyclerAdapter(SearchFragment context, ArrayList<News> models) {
        this.models = models;
        this.context = context;
        alpha = AnimationUtils.loadAnimation(context.getContext(), R.anim.alpha);
        initProgressDialog();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.articleDate)
        TextView date;
        @BindView(R.id.articleTitle)
        TextView title;
        @BindView(R.id.articleText)
        TextView text;
        @BindView(R.id.search_card)
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
        initProgressDialog();
        return holder;
    }

    public void initProgressDialog() {
        mProgressDialog = new ProgressDialog(context.getContext(), R.style.MyDialog);
        mProgressDialog.setIcon(R.drawable.logo_int_settings);
        mProgressDialog.setMessage("Ожадание отклика сервера...");
        mProgressDialog.setTitle("Открываю статью");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.date.setText(models.get(position).getCreated_at());
        viewHolder.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        viewHolder.title.setText(models.get(position).getTitle());
        viewHolder.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        viewHolder.text.setText(models.get(position).getText());
        viewHolder.text.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        final Integer id = models.get(viewHolder.getAdapterPosition()).getId();
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArticle(id);
                Intent articleBroadcast = new Intent(context.getContext(), ArticleActivity.class);
                articleBroadcast.putExtra(Constants.ITEM, article);
                context.startActivity(articleBroadcast);
            }
        });

    }

    private void openArticle(final Integer id) {
        mProgressDialog.show();
        PageApi api = RetrofitServiceGenerator.createService(PageApi.class);
        Call<Article> call = api.getItemById(id);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                mProgressDialog.dismiss();
                article = response.body().getItem();
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.d("SearchAdapter", "Ошибка подключения...", t.getCause());
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}