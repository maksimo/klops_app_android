package ru.klops.klops.adapter;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Spannable searchWord;
    Spannable searchInDescr;
    String keyword;

    public SearchRecyclerAdapter(SearchFragment context, ArrayList<News> models, String keyword) {
        this.models = models;
        this.context = context;
        this.keyword = keyword.substring(0,1).toUpperCase() + keyword.substring(1).toLowerCase();
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

    public void setData(ArrayList<News> data, String searchRequest) {
        models.clear();
        notifyDataSetChanged();
        models.addAll(data);
        this.keyword = searchRequest;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_card, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.BELOW, viewHolder.author.getId());
        viewHolder.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
        if (models.get(position).getSource().equals("") && !models.get(position).getAuthor().equals("")){
            viewHolder.author.setText(models.get(position).getAuthor());
        }else if (models.get(position).getAuthor().equals("") && !models.get(position).getSource().equals("")){
            viewHolder.author.setText(models.get(position).getSource());
        }else if (models.get(position).getSource().equals("") && models.get(position).getAuthor().equals("")){
            viewHolder.author.setVisibility(View.GONE);
        }else {
            viewHolder.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
        }
        viewHolder.date.setText(models.get(position).getDate());
        viewHolder.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        viewHolder.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        searchWord = new SpannableString(models.get(position).getTitle());
        if (keyword == null) {
            keyword = "";
        }
        if (Character.isUpperCase(keyword.charAt(0))){

        }
        Pattern word = Pattern.compile(keyword);
        Matcher matcher = word.matcher(searchWord);
        if (matcher.find()) {
            searchWord.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context.getContext(), R.color.colorAccent)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            searchWord.setSpan(new UnderlineSpan(), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.title.setText(searchWord);
        } else {
            viewHolder.title.setText(models.get(position).getTitle());
        }
        viewHolder.text.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
        searchInDescr = new SpannableString(models.get(position).getShortdecription());
        if (keyword == null) {
            keyword = "";
        }
        Pattern descrWord = Pattern.compile(keyword);
        Matcher descrMatch = descrWord.matcher(searchInDescr);
        if (descrMatch.find()) {
            searchInDescr.setSpan(new UnderlineSpan(), descrMatch.start(), descrMatch.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            searchInDescr.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context.getContext(), R.color.colorAccent)), descrMatch.start(), descrMatch.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.text.setText(searchInDescr);
        } else {
            viewHolder.text.setText(models.get(position).getShortdecription());
        }

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
                                articleIntent.putExtra(Constants.TYPE, models.get(position).getArticle_type());
                                context.startActivity(articleIntent);
                            }
                        });
            }
        });
    }

    public void clearData() {
        models.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}