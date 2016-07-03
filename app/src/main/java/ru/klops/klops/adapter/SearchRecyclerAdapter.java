package ru.klops.klops.adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    String keyword;
    Spannable searchWord;
    Spannable searchInDescr;
    private boolean zoomOut = true;

    public SearchRecyclerAdapter(SearchFragment context, ArrayList<News> models, String keyword) {
        this.models = models;
        this.context = context;
        this.keyword = keyword;
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
        @BindView(R.id.resizedLayer)
        RelativeLayout resizedLayer;

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
        String data = models.get(position).getCreated_at().substring(0, 9);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(date).replace("-", "/");
        viewHolder.date.setText(dateString);
        viewHolder.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
        viewHolder.title.setText(models.get(position).getTitle());
        viewHolder.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
        viewHolder.text.setText(models.get(position).getAnnotation());
        viewHolder.text.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
        String text = models.get(position).getTitle();
        Pattern word = Pattern.compile(keyword);
        Matcher matcher = word.matcher(text);
        searchWord = new SpannableString(text);
        while (matcher.find()) {
            searchWord.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context.getContext(), R.color.colorAccent)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            searchWord.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context.getContext(), R.color.colorPrimary)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.title.setText(searchWord);
        }
        String description = models.get(position).getAnnotation();
        Pattern descrWord = Pattern.compile(keyword);
        Matcher descrMatch = descrWord.matcher(description);
        searchInDescr = new SpannableString(description);
        while (descrMatch.find()) {
            searchInDescr.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context.getContext(), R.color.colorAccent)), descrMatch.start(), descrMatch.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            searchInDescr.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context.getContext(), R.color.colorPrimary)), descrMatch.start(), descrMatch.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.text.setText(searchInDescr);
        }
        final Integer id = models.get(viewHolder.getAdapterPosition()).getId();
//        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openArticle(id);
//                Intent articleBroadcast = new Intent(context.getContext(), ArticleActivity.class);
//                articleBroadcast.putExtra(Constants.ITEM, article);
//                context.startActivity(articleBroadcast);
//            }
//        });
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