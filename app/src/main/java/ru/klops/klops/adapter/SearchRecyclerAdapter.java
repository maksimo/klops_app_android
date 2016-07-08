package ru.klops.klops.adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
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
import ru.klops.klops.fragments.SearchFragment;
import ru.klops.klops.models.article.Item;
import ru.klops.klops.models.search.News;
import ru.klops.klops.services.ServiceArticleLoader;
import ru.klops.klops.utils.Constants;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    ArrayList<News> models;
    SearchFragment context;
    Animation alpha;
    ProgressDialog mProgressDialog;
    String keyword;

    public SearchRecyclerAdapter(SearchFragment context, ArrayList<News> models, String keyword) {
        this.models = models;
        this.context = context;
        this.keyword = keyword;
        alpha = AnimationUtils.loadAnimation(context.getContext(), R.anim.alpha);
        initProgressDialog();
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
        @BindView(R.id.searchCard)
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
                context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(viewHolder.getAdapterPosition()).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}