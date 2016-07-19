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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.api.PageApi;
import ru.klops.klops.custom.CircleImageView;
import ru.klops.klops.fragments.PopularDataNewsFragment;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.popular.News;
import ru.klops.klops.services.RetrofitServiceGenerator;
import ru.klops.klops.utils.Constants;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RVPopularDataAdapter extends RecyclerView.Adapter<RVPopularDataAdapter.ViewHolder> {
    PopularDataNewsFragment context;
    ArrayList<News> models;
    ArrayList<Integer> dataTypes;
    Animation alpha;

    public RVPopularDataAdapter(PopularDataNewsFragment context, ArrayList<News> models, ArrayList<Integer> dataTypes) {
        this.context = context;
        this.models = models;
        this.dataTypes = dataTypes;
        alpha = AnimationUtils.loadAnimation(context.getContext(), R.anim.alpha);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class SimpleNewsHolder extends ViewHolder {
        @BindView(R.id.simpleImageCardPhoto)
        ImageView image;
        @BindView(R.id.simpleImageCardDate)
        TextView date;
        @BindView(R.id.simpleImageCardText)
        TextView title;
        @BindView(R.id.simpleNewsLayer)
        RelativeLayout simpleImageCard;
        @BindView(R.id.simpleImageCardLoading)
        ProgressBar bar;

        public SimpleNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class SimpleTextNewsHolder extends ViewHolder {

        @BindView(R.id.simpleCardDate)
        TextView date;
        @BindView(R.id.simpleCardText)
        TextView title;
        @BindView(R.id.simpleTextLayer)
        RelativeLayout simpleCard;

        public SimpleTextNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class LongNewsHolder extends ViewHolder {
        @BindView(R.id.longCardAuthor)
        TextView author;
        @BindView(R.id.longCardDate)
        TextView date;
        @BindView(R.id.longCardTitle)
        TextView title;
        @BindView(R.id.longCardText)
        TextView content;
        @BindView(R.id.longNewsLayer)
        RelativeLayout longCard;

        public LongNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class InterviewNewsHolder extends ViewHolder {

        @BindView(R.id.interviewCardDate)
        TextView date;
        @BindView(R.id.interviewCardTitle)
        TextView title;
        @BindView(R.id.interviewCardText)
        TextView content;
        @BindView(R.id.interviewCardPhoto)
        CircleImageView image;
        @BindView(R.id.interviewNewsLayer)
        RelativeLayout interviewCard;
        @BindView(R.id.interviewCardLoading)
        ProgressBar bar;

        public InterviewNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class AuthorColumnsNewsHolder extends ViewHolder {

        @BindView(R.id.authorCardDate)
        TextView date;
        @BindView(R.id.authorCardTitle)
        TextView title;
        @BindView(R.id.authorCardText)
        TextView content;
        @BindView(R.id.authorCardPhoto)
        CircleImageView image;
        @BindView(R.id.authorCardName)
        TextView name;
        @BindView(R.id.authorCardSubName)
        TextView contentAuthor;
        @BindView(R.id.authorNewsLayer)
        RelativeLayout authorsCard;
        @BindView(R.id.authorCardLoading)
        ProgressBar bar;

        public AuthorColumnsNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class NationalNewsHolder extends ViewHolder {
        @BindView(R.id.nationalCardAuthor)
        TextView author;
        @BindView(R.id.nationalCardDate)
        TextView dateOne;
        @BindView(R.id.nationalCardTitle)
        TextView titleOne;
        @BindView(R.id.nationalCard)
        RelativeLayout nationalCard;

        public NationalNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class ImportantNewsWithPhotoHolder extends ViewHolder {

        @BindView(R.id.importantCardPhoto)
        ImageView image;
        @BindView(R.id.importantCardLoading)
        ProgressBar bar;
        @BindView(R.id.importantCardDate)
        TextView date;
        @BindView(R.id.importantCardStatus)
        TextView status;
        @BindView(R.id.importantCardTitle)
        TextView title;
        @BindView(R.id.importantCardText)
        TextView content;
        @BindView(R.id.importantNewsLayer)
        RelativeLayout important;

        public ImportantNewsWithPhotoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class GalleryNewsOneHolder extends ViewHolder {
        @BindView(R.id.galleryCardOneDate)
        TextView date;
        @BindView(R.id.galleryCardOneTitle)
        TextView title;
        @BindView(R.id.galleryCardOneLoading)
        ProgressBar bar;
        @BindView(R.id.galleryCardOnePhoto)
        ImageView image;
        @BindView(R.id.galleryOneLayer)
        RelativeLayout galleryCard;

        public GalleryNewsOneHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class GalleryNewsTwoHolder extends ViewHolder {

        @BindView(R.id.galleryCardTwoDate)
        TextView date;
        @BindView(R.id.galleryCardTwoTitle)
        TextView title;
        @BindView(R.id.galleryCardTwoLoadingFirst)
        ProgressBar barFirst;
        @BindView(R.id.galleryCardTwoLoadingSecond)
        ProgressBar barSecond;
        @BindView(R.id.galleryCardTwoLoadingBig)
        ProgressBar barOneBig;
        @BindView(R.id.galleryCardTwoPhotoFirst)
        ImageView imageOne;
        @BindView(R.id.galleryCardTwoPhotoSecond)
        ImageView imageTwo;
        @BindView(R.id.galleryCardTwoOneInTwo)
        ImageView imageOnlyOne;
        @BindView(R.id.galleryTwoLayer)
        RelativeLayout galleryCard;

        public GalleryNewsTwoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class AdvertiseHolder extends ViewHolder {

        @BindView(R.id.advertiseCardPhoto)
        ImageView image;
        @BindView(R.id.advertiseCardLoading)
        ProgressBar bar;
        @BindView(R.id.advertiseCard)
        RelativeLayout adsCard;
        @BindView(R.id.closableLayer)
        RelativeLayout closableLayout;
        @BindView(R.id.adsClose)
        TextView adsClose;
        @BindView(R.id.adsTitle)
        TextView adsTitle;

        public AdvertiseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SimpleWideHolder extends ViewHolder {
        @BindView(R.id.simpleWideCardDate)
        TextView date;
        @BindView(R.id.simpleWideCardTitle)
        TextView title;
        @BindView(R.id.simpleWideCardText)
        TextView content;
        @BindView(R.id.simpleWideLayer)
        RelativeLayout simpleWideCard;
        @BindView(R.id.simpleWideCardAuthor)
        TextView author;

        public SimpleWideHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class PopularMarkerViewHolder extends ViewHolder {
        @BindView(R.id.popularMarkerTitle)
        TextView popularMarkerTitle;

        public PopularMarkerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class SeparatorViewHolder extends ViewHolder {
        public SeparatorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    public class ExchangeViewHolder extends ViewHolder {
        @BindView(R.id.dollar)
        TextView dollar;
        @BindView(R.id.dollarPrice)
        TextView dollarPrice;
        @BindView(R.id.euro)
        TextView euro;
        @BindView(R.id.euroPrice)
        TextView euroPrice;
        @BindView(R.id.zlotiy)
        TextView zlotiy;
        @BindView(R.id.zlotiyPrice)
        TextView zlotiyPrice;

        public ExchangeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == Constants.SIMPLE_WITH_IMG) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_simple_news, viewGroup, false);
            return new SimpleNewsHolder(view);
        } else if (viewType == Constants.SIMPLE_TEXT_NEWS) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_simple_text, viewGroup, false);
            return new SimpleTextNewsHolder(view);
        } else if (viewType == Constants.LONG) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_long_texts, viewGroup, false);
            return new LongNewsHolder(view);
        } else if (viewType == Constants.INTERVIEW) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_interview, viewGroup, false);
            return new InterviewNewsHolder(view);
        } else if (viewType == Constants.AUTHORS) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_author_columns, viewGroup, false);
            return new AuthorColumnsNewsHolder(view);
        } else if (viewType == Constants.NATIONAL) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_national_news, viewGroup, false);
            return new NationalNewsHolder(view);
        } else if (viewType == Constants.IMPORTANT) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_important, viewGroup, false);
            return new ImportantNewsWithPhotoHolder(view);
        } else if (viewType == Constants.GALLERY_ONE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_gallery_one, viewGroup, false);
            return new GalleryNewsOneHolder(view);
        } else if (viewType == Constants.GALLERY_TWO) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_gallery_two, viewGroup, false);
            return new GalleryNewsTwoHolder(view);
        } else if (viewType == Constants.ADVERTISE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_advertise, viewGroup, false);
            return new AdvertiseHolder(view);
        } else if (viewType == Constants.SIMPLE_WIDE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_simple_wide, viewGroup, false);
            return new SimpleWideHolder(view);
        } else if (viewType == Constants.POPULAR_MARKER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_popular_marker, viewGroup, false);
            return new PopularMarkerViewHolder(view);
        } else if (viewType == Constants.SEPARATOR) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_separator, viewGroup, false);
            return new SeparatorViewHolder(view);
        } else if (viewType == Constants.EXCHANGE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_exchange, viewGroup, false);
            return new ExchangeViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case Constants.SIMPLE_WITH_IMG:
                final SimpleNewsHolder holder = (SimpleNewsHolder) viewHolder;
                loadPhoto(models.get(position).getImage(), holder.image, holder.bar);
                holder.date.setText(models.get(position).getDate());
                holder.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holder.title.setText(models.get(position).getTitle());
                holder.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holder.simpleImageCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.simpleImageCard.startAnimation(alpha);
                        loadArticle(models.get(holder.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.SIMPLE_TEXT_NEWS:
                final SimpleTextNewsHolder holderSimple = (SimpleTextNewsHolder) viewHolder;
                holderSimple.date.setText(models.get(position).getDate());
                holderSimple.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderSimple.title.setText(models.get(position).getTitle());
                holderSimple.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderSimple.simpleCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderSimple.simpleCard.startAnimation(alpha);
                        loadArticle(models.get(holderSimple.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.LONG:
                final LongNewsHolder holderLong = (LongNewsHolder) viewHolder;
                holderLong.date.setText(models.get(position).getDate());
                holderLong.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderLong.author.setText(models.get(position).getAuthor());
                holderLong.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                holderLong.title.setText(models.get(position).getTitle());
                holderLong.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderLong.content.setText(models.get(position).getShortdecription());
                holderLong.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderLong.longCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderLong.longCard.startAnimation(alpha);
                        loadArticle(models.get(holderLong.getAdapterPosition()).getId());

                    }
                });
                break;
            case Constants.INTERVIEW:
                final InterviewNewsHolder holderInterview = (InterviewNewsHolder) viewHolder;
                holderInterview.date.setText(models.get(position).getDate());
                holderInterview.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderInterview.title.setText(models.get(position).getTitle());
                holderInterview.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderInterview.content.setText(models.get(position).getShortdecription());
                holderInterview.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                loadPhoto(models.get(position).getImage(), holderInterview.image, holderInterview.bar);
                holderInterview.interviewCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderInterview.interviewCard.startAnimation(alpha);
                        loadArticle(models.get(holderInterview.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.AUTHORS:
                final AuthorColumnsNewsHolder holderAuthors = (AuthorColumnsNewsHolder) viewHolder;
                holderAuthors.date.setText(models.get(position).getDate());
                holderAuthors.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderAuthors.title.setText(models.get(position).getTitle());
                holderAuthors.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderAuthors.content.setText(models.get(position).getShortdecription());
                holderAuthors.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                loadPhoto(models.get(position).getImage(), holderAuthors.image, holderAuthors.bar);
                holderAuthors.contentAuthor.setText(models.get(position).getAuthor());
                holderAuthors.contentAuthor.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                holderAuthors.authorsCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderAuthors.authorsCard.startAnimation(alpha);
                        loadArticle(models.get(holderAuthors.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.NATIONAL:
                final NationalNewsHolder holderNational = (NationalNewsHolder) viewHolder;
                holderNational.author.setText(models.get(position).getAuthor());
                holderNational.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                holderNational.dateOne.setText(models.get(position).getDate());
                holderNational.dateOne.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderNational.titleOne.setText(models.get(position).getTitle());
                holderNational.titleOne.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderNational.nationalCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderNational.nationalCard.startAnimation(alpha);
                        loadArticle(models.get(holderNational.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.IMPORTANT:
                final ImportantNewsWithPhotoHolder holderImportant = (ImportantNewsWithPhotoHolder) viewHolder;
                if (models.get(position).getImage() == null) {
                    holderImportant.image.setVisibility(View.GONE);
                } else {
                    loadPhoto(models.get(position).getImage(), holderImportant.image, holderImportant.bar);
                }
                holderImportant.date.setText(models.get(position).getDate());
                holderImportant.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderImportant.status.setText(models.get(position).getUpdate_status());
                holderImportant.status.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderImportant.title.setText(models.get(position).getTitle());
                holderImportant.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                holderImportant.content.setText(models.get(position).getShortdecription());
                holderImportant.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderImportant.important.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderImportant.important.startAnimation(alpha);
                        loadArticle(models.get(holderImportant.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.GALLERY_ONE:
                final GalleryNewsOneHolder holderGallerySmall = (GalleryNewsOneHolder) viewHolder;
                holderGallerySmall.date.setText(models.get(position).getDate());
                holderGallerySmall.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderGallerySmall.title.setText(String.format("%1$" + 5 + "s", models.get(position).getTitle()));
                holderGallerySmall.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                loadPhoto(models.get(position).getImage(), holderGallerySmall.image, holderGallerySmall.bar);
                holderGallerySmall.galleryCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderGallerySmall.galleryCard.startAnimation(alpha);
                        loadArticle(models.get(holderGallerySmall.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.GALLERY_TWO:
                final GalleryNewsTwoHolder holderGalleryBig = (GalleryNewsTwoHolder) viewHolder;
                holderGalleryBig.date.setText(models.get(position).getDate());
                holderGalleryBig.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderGalleryBig.title.setText(String.format("%1$" + 5 + "s", models.get(position).getTitle()));
                holderGalleryBig.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                if (models.get(position).getPhotos().size() == 0) {
                    holderGalleryBig.imageOnlyOne.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageOne.setVisibility(View.GONE);
                    holderGalleryBig.imageTwo.setVisibility(View.GONE);
                    holderGalleryBig.barFirst.setVisibility(View.GONE);
                    holderGalleryBig.barSecond.setVisibility(View.GONE);
                    holderGalleryBig.barOneBig.setVisibility(View.VISIBLE);
                    if (models.get(position).getImage() != null) {
                        loadPhoto(models.get(position).getImage(), holderGalleryBig.imageOnlyOne, holderGalleryBig.barOneBig);
                    }
                } else {
                    holderGalleryBig.imageOne.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageTwo.setVisibility(View.VISIBLE);
                    holderGalleryBig.barFirst.setVisibility(View.VISIBLE);
                    holderGalleryBig.barSecond.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageOnlyOne.setVisibility(View.GONE);
                    holderGalleryBig.barOneBig.setVisibility(View.GONE);
                    loadPhoto(models.get(position).getPhotos().get(0), holderGalleryBig.imageOne, holderGalleryBig.barFirst);
                    loadPhoto(models.get(position).getPhotos().get(1), holderGalleryBig.imageTwo, holderGalleryBig.barSecond);
                }
                holderGalleryBig.galleryCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderGalleryBig.galleryCard.startAnimation(alpha);
                        loadArticle(models.get(holderGalleryBig.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.ADVERTISE:
                final AdvertiseHolder holderAdvertise = (AdvertiseHolder) viewHolder;
                if (!models.get(position).getImage().equals("")) {
                    loadPhoto(models.get(position).getImage(), holderAdvertise.image, holderAdvertise.bar);
                }
                holderAdvertise.adsClose.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderAdvertise.adsTitle.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderAdvertise.closableLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderAdvertise.closableLayout.startAnimation(alpha);
                        remove(holderAdvertise.getAdapterPosition());
                    }
                });
                break;
            case Constants.SIMPLE_WIDE:
                final SimpleWideHolder holderSimpleWide = (SimpleWideHolder) viewHolder;
                holderSimpleWide.author.setText(models.get(position).getAuthor());
                holderSimpleWide.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                holderSimpleWide.date.setText(models.get(position).getDate());
                holderSimpleWide.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderSimpleWide.title.setText(models.get(position).getTitle());
                holderSimpleWide.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderSimpleWide.content.setText(models.get(position).getShortdecription());
                holderSimpleWide.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderSimpleWide.simpleWideCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderSimpleWide.simpleWideCard.startAnimation(alpha);
                        loadArticle(models.get(holderSimpleWide.getAdapterPosition()).getId());
                    }
                });
                break;
            case Constants.POPULAR_MARKER:
                final PopularMarkerViewHolder holderPopular = (PopularMarkerViewHolder) viewHolder;
                holderPopular.popularMarkerTitle.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                break;
            case Constants.EXCHANGE:
                final ExchangeViewHolder holderExchange = (ExchangeViewHolder) viewHolder;
                holderExchange.dollar.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderExchange.euro.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderExchange.zlotiy.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderExchange.dollarPrice.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderExchange.dollarPrice.setText(models.get(position).getDate());
                holderExchange.euroPrice.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderExchange.euroPrice.setText(models.get(position).getTitle());
                holderExchange.zlotiyPrice.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderExchange.zlotiyPrice.setText(models.get(position).getShortdecription());
                break;
        }
    }

    public void loadPhoto(String input, ImageView output, final ProgressBar loader) {
        Picasso.with(context.getActivity()).load(input).into(output, new Callback() {
            @Override
            public void onSuccess() {
                loader.setVisibility(View.GONE);

            }

            @Override
            public void onError() {
                loader.setVisibility(View.VISIBLE);
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

    public void remove(int position) {
        models.remove(position);
        dataTypes.remove(position);
        notifyItemRemoved(position);
    }

    public void insert(int position, News news) {
        models.add(position, news);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataTypes.get(position);
    }

    public void updateData(ArrayList<News> updatedModel) {
        models.clear();
        models.addAll(updatedModel);
        notifyDataSetChanged();
    }
}
