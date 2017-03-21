package ru.klops.klops.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.klops.klops.HomeActivity;
import ru.klops.klops.R;
import ru.klops.klops.custom.CircleImageView;
import ru.klops.klops.fragments.NewDataNewsFragment;
import ru.klops.klops.models.feed.Currency;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.utils.Constants;

public class RVNewDataAdapter extends RecyclerView.Adapter<RVNewDataAdapter.ViewHolder> {
    NewDataNewsFragment context;
    ArrayList<News> models;
    ArrayList<Integer> dynamicTypes;
    Currency currency;
    Animation alpha;
    HomeActivity activity;


    public RVNewDataAdapter(NewDataNewsFragment context, ArrayList<News> models, ArrayList<Integer> dynamicTypes, Currency currency) {
        this.models = models;
        this.context = context;
        this.dynamicTypes = dynamicTypes;
        this.currency = currency;
        alpha = AnimationUtils.loadAnimation(context.getContext(), R.anim.alpha);
        activity = (HomeActivity) context.getActivity();
    }

    public RVNewDataAdapter(NewDataNewsFragment context, ArrayList<News> models, ArrayList<Integer> dynamicTypes) {
        this.models = models;
        this.context = context;
        this.dynamicTypes = dynamicTypes;
        alpha = AnimationUtils.loadAnimation(context.getContext(), R.anim.alpha);
        activity = (HomeActivity) context.getActivity();
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
        @BindView(R.id.simpleImagePromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.simpleImageCardAuthor)
        TextView author;
        @BindView(R.id.simpleImageCardAuthors)
        RelativeLayout simpleImageCardAuthors;
        @BindView(R.id.separatorSimpleImage)
        View separator;

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
        @BindView(R.id.simpleCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.simpleCardAuthor)
        TextView author;
        @BindView(R.id.simpleCardAuthors)
        RelativeLayout simpleCardAuthors;
        @BindView(R.id.separatorSimple)
        View separator;

        public SimpleTextNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class LongNewsHolder extends ViewHolder {
        @BindView(R.id.longCardDate)
        TextView date;
        @BindView(R.id.longCardTitle)
        TextView title;
        @BindView(R.id.longCardText)
        TextView content;
        @BindView(R.id.longNewsLayer)
        RelativeLayout longCard;
        @BindView(R.id.longCardStatusIcon)
        ImageView longCardStatusIcon;
        @BindView(R.id.longCardStatus)
        TextView longCardStatus;
        @BindView(R.id.longCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.longCardAuthor)
        TextView author;
        @BindView(R.id.longCardAuthors)
        RelativeLayout longCardAuthors;
        @BindView(R.id.separatorLong)
        View separator;

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
        ImageView image;
        @BindView(R.id.interviewNewsLayer)
        RelativeLayout interviewCard;
        @BindView(R.id.interviewCardLoading)
        ProgressBar bar;
        @BindView(R.id.interviewCardStatusIcon)
        ImageView interviewCardStatusIcon;
        @BindView(R.id.interviewCardStatus)
        TextView interviewCardStatus;
        @BindView(R.id.interviewCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.interviewCardAuthor)
        TextView author;
        @BindView(R.id.interviewCardAuthors)
        RelativeLayout interviewCardAuthors;
        @BindView(R.id.separatorInterview)
        View separator;

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
        ImageView image;
        @BindView(R.id.authorNewsLayer)
        RelativeLayout authorsCard;
        @BindView(R.id.authorCardLoading)
        ProgressBar bar;
        @BindView(R.id.authorCardStatusIcon)
        ImageView authorCardStatusIcon;
        @BindView(R.id.authorCardStatus)
        TextView authorCardStatus;
        @BindView(R.id.authorCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.authorCardName)
        TextView authorName;
        @BindView(R.id.authorCardSubName)
        TextView authorCardSubName;
        @BindView(R.id.separatorAuthor)
        View separator;

        public AuthorColumnsNewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class NationalNewsHolder extends ViewHolder {
        @BindView(R.id.nationalCardDate)
        TextView dateOne;
        @BindView(R.id.nationalCardTitle)
        TextView titleOne;
        @BindView(R.id.nationalCard)
        RelativeLayout nationalCard;
        @BindView(R.id.nationalCardStatusIcon)
        ImageView nationalCardStatusIcon;
        @BindView(R.id.nationalCardStatus)
        TextView nationalCardStatus;
        @BindView(R.id.nationalCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.nationalCardAuthor)
        TextView author;
        @BindView(R.id.nationalCardAuthors)
        RelativeLayout nationalCardAuthors;
        @BindView(R.id.separatorNational)
        View separator;

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
        @BindView(R.id.importantCardTitle)
        TextView title;
        @BindView(R.id.importantCardText)
        TextView content;
        @BindView(R.id.importantNewsLayer)
        RelativeLayout important;
        @BindView(R.id.importantCardStatusIcon)
        ImageView importantCardStatusIcon;
        @BindView(R.id.importantCardStatus)
        TextView status;
        @BindView(R.id.importantCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.importantCardAuthor)
        TextView author;
        @BindView(R.id.importantCardAuthors)
        RelativeLayout importantCardAuthors;
        @BindView(R.id.separatorImportant)
        View separator;

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
        @BindView(R.id.galleryCardStatusIcon)
        ImageView galleryCardStatusIcon;
        @BindView(R.id.galleryCardStatus)
        TextView galleryCardStatus;
        @BindView(R.id.galleryCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.galleryCardAuthor)
        TextView author;
        @BindView(R.id.galleryCardAuthors)
        RelativeLayout galleryCardAuthors;
        @BindView(R.id.separatorGallery)
        View separator;

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
        @BindView(R.id.galleryCardTwoStatusIcon)
        ImageView galleryCardTwoStatusIcon;
        @BindView(R.id.galleryCardTwoStatus)
        TextView galleryCardTwoStatus;
        @BindView(R.id.galleryCardTwoPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.galleryCardTwoAuthor)
        TextView author;
        @BindView(R.id.galleryCardTwoAuthors)
        RelativeLayout galleryCardTwoAuthors;
        @BindView(R.id.separatorGalleryTwo)
        View separator;

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
        @BindView(R.id.advertiseImageLayer)
        RelativeLayout advertiseLayer;
        @BindView(R.id.separatorAds)
        View separator;

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
        @BindView(R.id.simpleWideCardStatusIcon)
        ImageView simpleWideCardStatusIcon;
        @BindView(R.id.simpleWideCardStatus)
        TextView simpleWideCardStatus;
        @BindView(R.id.simpleWideCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.simpleWideCardAuthor)
        TextView author;
        @BindView(R.id.simpleWideCardAuthors)
        RelativeLayout simpleWideCardAuthors;
        @BindView(R.id.separatorSimpleWide)
        View separator;

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
        @BindView(R.id.separatorCard)
        RelativeLayout separator;
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
        @BindView(R.id.separatorExchange)
        View separator;

        public ExchangeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class UrgentNewsViewHolder extends ViewHolder {
        @BindView(R.id.urgentCardDate)
        TextView date;
        @BindView(R.id.urgentCardStatus)
        TextView status;
        @BindView(R.id.urgentCardStatusIcon)
        ImageView statusIcon;
        @BindView(R.id.urgentCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.urgentCardTitle)
        TextView title;
        @BindView(R.id.urgentCardLayer)
        RelativeLayout urgentCardLayer;
        @BindView(R.id.urgentCardAuthor)
        TextView author;
        @BindView(R.id.urgentCardAuthors)
        RelativeLayout urgentCardAuthors;
        @BindView(R.id.separatorUrgent)
        View separator;

        public UrgentNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MainShortViewHolder extends ViewHolder {

        @BindView(R.id.mainShortCardPhoto)
        ImageView image;
        @BindView(R.id.mainShortCardLoading)
        ProgressBar bar;
        @BindView(R.id.mainShortCardDate)
        TextView date;
        @BindView(R.id.mainShortCardTitle)
        TextView title;
        @BindView(R.id.mainShortNewsLayer)
        RelativeLayout mainShort;
        @BindView(R.id.mainShortCardStatusIcon)
        ImageView mainShortCardStatusIcon;
        @BindView(R.id.mainShortCardStatus)
        TextView status;
        @BindView(R.id.mainShortCardPromotionLayer)
        RelativeLayout promoted;
        @BindView(R.id.mainShortCardAuthor)
        TextView author;
        @BindView(R.id.mainShortCardAuthors)
        RelativeLayout mainShortCardAuthors;
        @BindView(R.id.separatorMainShort)
        View separator;

        public MainShortViewHolder(View itemView) {
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
        } else if (viewType == Constants.URGENT) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_urgent, viewGroup, false);
            return new UrgentNewsViewHolder(view);
        }else if (viewType == Constants.MAIN_SHORT){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_short, viewGroup, false);
            return new MainShortViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            case Constants.SIMPLE_WITH_IMG:
                final SimpleNewsHolder holder = (SimpleNewsHolder) viewHolder;
                activity.loadPhoto(models.get(position).getImage(), holder.image, holder.bar);
                holder.date.setText(models.get(position).getDate());
                holder.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holder.title.setText(models.get(position).getTitle());
                holder.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                if (models.get(position).getPromoted() == 1) {
                    holder.promoted.setVisibility(View.VISIBLE);
                }else {
                    holder.promoted.setVisibility(View.GONE);
                }
                holder.simpleImageCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.simpleImageCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holder.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
                    }
                });
                break;
            case Constants.SIMPLE_TEXT_NEWS:
                final SimpleTextNewsHolder holderSimple = (SimpleTextNewsHolder) viewHolder;
                holderSimple.date.setText(models.get(position).getDate());
                holderSimple.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderSimple.title.setText(models.get(position).getTitle());
                holderSimple.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                if (models.get(position).getPromoted() == 1) {
                    holderSimple.promoted.setVisibility(View.VISIBLE);
                }else {
                    holderSimple.promoted.setVisibility(View.GONE);
                }
                holderSimple.simpleCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderSimple.simpleCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderSimple.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
                    }
                });

                break;
            case Constants.LONG:
                final LongNewsHolder holderLong = (LongNewsHolder) viewHolder;
                holderLong.date.setText(models.get(position).getDate());
                holderLong.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderLong.title.setText(models.get(position).getTitle());
                holderLong.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderLong.content.setText(models.get(position).getShortdecription());
                holderLong.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderLong.longCardStatus.setText(models.get(position).getUpdate_status());
                    holderLong.longCardStatus.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderLong.longCardStatusIcon.setVisibility(View.VISIBLE);
                    holderLong.longCardStatus.setVisibility(View.VISIBLE);
                }
                if (models.get(position).getPromoted() == 1) {
                    holderLong.promoted.setVisibility(View.VISIBLE);
                } else {
                    holderLong.content.setMaxLines(7);
                }
                if (!models.get(position).getAuthor().equals("")) {
                    holderLong.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderLong.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
                    } else {
                        holderLong.author.setText(models.get(position).getAuthor());
                    }
                    holderLong.author.setVisibility(View.VISIBLE);
                    holderLong.longCardAuthors.setVisibility(View.VISIBLE);
                }
                holderLong.longCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderLong.longCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderLong.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);

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
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderInterview.interviewCardStatus.setText(models.get(position).getUpdate_status());
                    holderInterview.interviewCardStatus.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderInterview.interviewCardStatusIcon.setVisibility(View.VISIBLE);
                    holderInterview.interviewCardStatus.setVisibility(View.VISIBLE);
                }
                if (models.get(position).getPromoted() == 1) {
                    holderInterview.promoted.setVisibility(View.VISIBLE);
                }
                if (!models.get(position).getAuthor().equals("")) {
                    holderInterview.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderInterview.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
                    } else {
                        holderInterview.author.setText(models.get(position).getAuthor());
                    }
                    holderInterview.author.setVisibility(View.VISIBLE);
                    holderInterview.interviewCardAuthors.setVisibility(View.VISIBLE);
                }
                activity.loadPhotoCircle(models.get(position).getImage(), holderInterview.image, holderInterview.bar);
                holderInterview.interviewCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderInterview.interviewCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderInterview.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
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
                holderAuthors.authorName.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                if (!models.get(position).getAuthor().equals("")) {
                    holderAuthors.authorName.setText(models.get(position).getAuthor());
                } else {
                    holderAuthors.authorName.setVisibility(View.GONE);
                }
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderAuthors.authorCardStatus.setText(models.get(position).getUpdate_status());
                    holderAuthors.authorCardStatus.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderAuthors.authorCardStatusIcon.setVisibility(View.VISIBLE);
                    holderAuthors.authorCardStatus.setVisibility(View.VISIBLE);
                }
                if (!models.get(position).getAuthors_regalia().equals("")) {
                    holderAuthors.authorCardSubName.setText(models.get(position).getAuthors_regalia());
                } else {
                    holderAuthors.authorCardSubName.setVisibility(View.GONE);
                }
                holderAuthors.authorCardSubName.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                if (models.get(position).getPromoted() == 1) {
                    holderAuthors.promoted.setVisibility(View.VISIBLE);
                } else {
                    holderAuthors.title.setMaxLines(7);
                }

                activity.loadPhotoCircle(models.get(position).getAuthor_photo(), holderAuthors.image, holderAuthors.bar);
                holderAuthors.authorsCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderAuthors.authorsCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderAuthors.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
                    }
                });

                break;
            case Constants.NATIONAL:
                final NationalNewsHolder holderNational = (NationalNewsHolder) viewHolder;
                holderNational.dateOne.setText(models.get(position).getDate());
                holderNational.dateOne.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderNational.titleOne.setText(models.get(position).getTitle());
                holderNational.titleOne.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderNational.nationalCardStatus.setText(models.get(position).getUpdate_status());
                    holderNational.nationalCardStatus.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderNational.nationalCardStatusIcon.setVisibility(View.VISIBLE);
                    holderNational.nationalCardStatus.setVisibility(View.VISIBLE);
                }
                if (models.get(position).getPromoted() == 1) {
                    holderNational.promoted.setVisibility(View.VISIBLE);
                }

                if (!models.get(position).getAuthor().equals("")) {
                    holderNational.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderNational.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());

                    } else {
                        holderNational.author.setText(models.get(position).getAuthor());
                    }
                    holderNational.author.setVisibility(View.VISIBLE);
                    holderNational.nationalCardAuthors.setVisibility(View.VISIBLE);
                }
                holderNational.nationalCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderNational.nationalCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderNational.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
                    }
                });
               break;
            case Constants.IMPORTANT:
                final ImportantNewsWithPhotoHolder holderImportant = (ImportantNewsWithPhotoHolder) viewHolder;
                if (models.get(position).getImage() == null) {
                    holderImportant.image.setVisibility(View.GONE);
                } else {
                    activity.loadPhoto(models.get(position).getImage(), holderImportant.image, holderImportant.bar);
                }
                holderImportant.date.setText(models.get(position).getDate());
                holderImportant.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderImportant.status.setText(models.get(position).getUpdate_status());
                    holderImportant.status.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderImportant.importantCardStatusIcon.setVisibility(View.VISIBLE);
                    holderImportant.status.setVisibility(View.VISIBLE);
                }
                if (models.get(position).getPromoted() == 1) {
                    holderImportant.promoted.setVisibility(View.VISIBLE);
                }
                if (!models.get(position).getAuthor().equals("")) {
                    holderImportant.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderImportant.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
                    } else {
                        holderImportant.author.setText(models.get(position).getAuthor());
                    }
                    holderImportant.author.setVisibility(View.VISIBLE);
                    holderImportant.importantCardAuthors.setVisibility(View.VISIBLE);
                }
                holderImportant.title.setText(models.get(position).getTitle());
                holderImportant.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                holderImportant.content.setText(models.get(position).getShortdecription());
                holderImportant.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderImportant.important.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderImportant.important.startAnimation(alpha);
                        activity.loadArticle(models.get(holderImportant.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
                    }
                });
                break;
            case Constants.GALLERY_ONE:
                final GalleryNewsOneHolder holderGallerySmall = (GalleryNewsOneHolder) viewHolder;
                holderGallerySmall.date.setText(models.get(position).getDate());
                holderGallerySmall.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderGallerySmall.title.setText("     " + models.get(position).getTitle());
                holderGallerySmall.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderGallerySmall.galleryCardStatus.setText(models.get(position).getUpdate_status());
                    holderGallerySmall.galleryCardStatus.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderGallerySmall.galleryCardStatusIcon.setVisibility(View.VISIBLE);
                    holderGallerySmall.galleryCardStatus.setVisibility(View.VISIBLE);
                }
                if (models.get(position).getPromoted() == 1) {
                    holderGallerySmall.promoted.setVisibility(View.VISIBLE);
                }
                if (!models.get(position).getAuthor().equals("")) {
                    holderGallerySmall.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderGallerySmall.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
                    } else {
                        holderGallerySmall.author.setText(models.get(position).getAuthor());
                    }
                    holderGallerySmall.author.setVisibility(View.VISIBLE);
                    holderGallerySmall.galleryCardAuthors.setVisibility(View.VISIBLE);
                }
                activity.loadPhoto(models.get(position).getImage(), holderGallerySmall.image, holderGallerySmall.bar);
                holderGallerySmall.galleryCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderGallerySmall.galleryCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderGallerySmall.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.GALLERY_TYPE);
                    }
                });
                holderGallerySmall.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderGallerySmall.galleryCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderGallerySmall.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.GALLERY_TYPE);
                    }
                });

                break;
            case Constants.GALLERY_TWO:
                final GalleryNewsTwoHolder holderGalleryBig = (GalleryNewsTwoHolder) viewHolder;
                holderGalleryBig.date.setText(models.get(position).getDate());
                holderGalleryBig.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderGalleryBig.title.setText("     " + models.get(position).getTitle());
                holderGalleryBig.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderGalleryBig.galleryCardTwoStatus.setText(models.get(position).getUpdate_status());
                    holderGalleryBig.galleryCardTwoStatus.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderGalleryBig.galleryCardTwoStatusIcon.setVisibility(View.VISIBLE);
                    holderGalleryBig.galleryCardTwoStatus.setVisibility(View.VISIBLE);
                }
                if (models.get(position).getPhotos().size() == 0) {
                    holderGalleryBig.imageOnlyOne.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageOne.setVisibility(View.GONE);
                    holderGalleryBig.imageTwo.setVisibility(View.GONE);
                    holderGalleryBig.barFirst.setVisibility(View.GONE);
                    holderGalleryBig.barSecond.setVisibility(View.GONE);
                    holderGalleryBig.barOneBig.setVisibility(View.VISIBLE);
                    if (models.get(position).getImage() != null) {
                        activity.loadPhoto(models.get(position).getImage(), holderGalleryBig.imageOnlyOne, holderGalleryBig.barOneBig);
                    }
                } else {
                    holderGalleryBig.imageOne.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageTwo.setVisibility(View.VISIBLE);
                    holderGalleryBig.barFirst.setVisibility(View.VISIBLE);
                    holderGalleryBig.barSecond.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageOnlyOne.setVisibility(View.GONE);
                    holderGalleryBig.barOneBig.setVisibility(View.GONE);
                    activity.loadPhoto(models.get(position).getPhotos().get(0), holderGalleryBig.imageOne, holderGalleryBig.barFirst);
                    activity.loadPhoto(models.get(position).getPhotos().get(1), holderGalleryBig.imageTwo, holderGalleryBig.barSecond);
                }
                if (models.get(position).getPromoted() == 1) {
                    holderGalleryBig.promoted.setVisibility(View.VISIBLE);
                }

                if (!models.get(position).getAuthor().equals("")) {
                    holderGalleryBig.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderGalleryBig.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
                    } else {
                        holderGalleryBig.author.setText(models.get(position).getAuthor());
                    }
                    holderGalleryBig.author.setVisibility(View.VISIBLE);
                    holderGalleryBig.galleryCardTwoAuthors.setVisibility(View.VISIBLE);
                }
                holderGalleryBig.galleryCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderGalleryBig.galleryCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderGalleryBig.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.GALLERY_TYPE);
                    }
                });
                holderGalleryBig.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderGalleryBig.galleryCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderGalleryBig.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.GALLERY_TYPE);
                    }
                });
                break;
            case Constants.ADVERTISE:
                final AdvertiseHolder holderAdvertise = (AdvertiseHolder) viewHolder;
                if (!models.get(position).getImage().equals("")) {
                    activity.loadPhoto(models.get(position).getImage(), holderAdvertise.image, holderAdvertise.bar);
                }
                holderAdvertise.adsClose.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderAdvertise.adsTitle.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderAdvertise.advertiseLayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(models.get(holderAdvertise.getAdapterPosition()).getUrl()));
                        context.startActivity(browser);
                    }
                });
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
                holderSimpleWide.date.setText(models.get(position).getDate());
                holderSimpleWide.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderSimpleWide.title.setText(models.get(position).getTitle());
                holderSimpleWide.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderSimpleWide.content.setText(models.get(position).getShortdecription());
                holderSimpleWide.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderSimpleWide.simpleWideCardStatus.setText(models.get(position).getUpdate_status());
                    holderSimpleWide.simpleWideCardStatus.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderSimpleWide.simpleWideCardStatusIcon.setVisibility(View.VISIBLE);
                    holderSimpleWide.simpleWideCardStatus.setVisibility(View.VISIBLE);
                }
                if (models.get(position).getPromoted() == 1) {
                    holderSimpleWide.promoted.setVisibility(View.VISIBLE);
                }

                if (!models.get(position).getAuthor().equals("")) {
                    holderSimpleWide.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderSimpleWide.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
                    } else {
                        holderSimpleWide.author.setText(models.get(position).getAuthor());
                    }
                    holderSimpleWide.author.setVisibility(View.VISIBLE);
                    holderSimpleWide.simpleWideCardAuthors.setVisibility(View.VISIBLE);
                }
                holderSimpleWide.simpleWideCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderSimpleWide.simpleWideCard.startAnimation(alpha);
                        activity.loadArticle(models.get(holderSimpleWide.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
                    }
                });
                break;
            case Constants.POPULAR_MARKER:
                final PopularMarkerViewHolder holderPopular = (PopularMarkerViewHolder) viewHolder;
                holderPopular.popularMarkerTitle.setText(models.get(position).getTitle());
                holderPopular.popularMarkerTitle.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                break;
            case Constants.SEPARATOR:
                final SeparatorViewHolder holderSeparator = (SeparatorViewHolder) viewHolder;
                break;
            case Constants.EXCHANGE:
                final ExchangeViewHolder holderExchange = (ExchangeViewHolder) viewHolder;
                holderExchange.dollar.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderExchange.euro.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderExchange.zlotiy.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-md.ttf"));
                holderExchange.dollarPrice.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderExchange.dollarPrice.setText(currency.getUsd().replace(".", ","));
                holderExchange.euroPrice.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderExchange.euroPrice.setText(currency.getEur().replace(".", ","));
                holderExchange.zlotiyPrice.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-light.ttf"));
                holderExchange.zlotiyPrice.setText(currency.getPln().replace(".", ","));
                break;
            case Constants.URGENT:
                final UrgentNewsViewHolder holderUrgent = (UrgentNewsViewHolder) viewHolder;
                holderUrgent.date.setText(models.get(position).getDate());
                holderUrgent.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderUrgent.status.setText("Срочно");
                holderUrgent.status.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                holderUrgent.statusIcon.setVisibility(View.VISIBLE);
                holderUrgent.title.setText(models.get(position).getTitle());
                holderUrgent.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                if (models.get(position).getPromoted() == 1) {
                    holderUrgent.promoted.setVisibility(View.VISIBLE);
                }
                if (!models.get(position).getAuthor().equals("")) {
                    holderUrgent.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderUrgent.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
                    } else {
                        holderUrgent.author.setText(models.get(position).getAuthor());
                    }
                    holderUrgent.author.setVisibility(View.VISIBLE);
                    holderUrgent.urgentCardAuthors.setVisibility(View.VISIBLE);
                }
                holderUrgent.urgentCardLayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderUrgent.urgentCardLayer.startAnimation(alpha);
                        activity.loadArticle(models.get(holderUrgent.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
                    }
                });
                break;
            case Constants.MAIN_SHORT:
                final MainShortViewHolder holderMainShort = (MainShortViewHolder) viewHolder;
                if (models.get(position).getImage() == null) {
                    holderMainShort.image.setVisibility(View.GONE);
                } else {
                    activity.loadPhoto(models.get(position).getImage(), holderMainShort.image, holderMainShort.bar);
                }
                holderMainShort.date.setText(models.get(position).getDate());
                holderMainShort.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                if (models.get(position).getPromoted() == 0) {
                    holderMainShort.promoted.setVisibility(View.GONE);
                }else {
                    holderMainShort.promoted.setVisibility(View.VISIBLE);
                }
                if (!models.get(position).getUpdate_status().equals("")) {
                    holderMainShort.status.setText(models.get(position).getUpdate_status());
                    holderMainShort.status.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-regular.ttf"));
                    holderMainShort.mainShortCardStatusIcon.setVisibility(View.VISIBLE);
                    holderMainShort.status.setVisibility(View.VISIBLE);
                }

                if (!models.get(position).getAuthor().equals("")) {
                    holderMainShort.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-bold.ttf"));
                    if (!models.get(position).getSource().equals("")) {
                        holderMainShort.author.setText(models.get(position).getSource() + " " + models.get(position).getAuthor());
                    } else {
                        holderMainShort.author.setText(models.get(position).getAuthor());
                    }
                    holderMainShort.author.setVisibility(View.VISIBLE);
                    holderMainShort.mainShortCardAuthors.setVisibility(View.VISIBLE);
                }
                holderMainShort.title.setText(models.get(position).getTitle());
                holderMainShort.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(), "fonts/akzidenzgroteskpro-super.ttf"));
                holderMainShort.mainShort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderMainShort.mainShort.startAnimation(alpha);
                        activity.loadArticle(models.get(holderMainShort.getAdapterPosition()).getId(), models.get(position).getArticle_type(), Constants.ARTICLE_TYPE);
                    }
                });
                break;
        }
    }

    public void remove(int position) {
        models.remove(position);
        dynamicTypes.remove(position);
        notifyItemRemoved(position);
    }

    public void insert(int position, News news) {
        models.add(position, news);
        notifyItemInserted(position);
    }

    public void clearNewFeed() {
        models.clear();
        dynamicTypes.clear();
        notifyDataSetChanged();
    }

    public void addData(ArrayList<News> data, ArrayList<Integer> types, Currency curr){
        clearNewFeed();
        models.addAll(data);
        dynamicTypes.addAll(types);
        currency.setPln(curr.getPln());
        curr.setEur(curr.getEur());
        curr.setUsd(curr.getUsd());


    }

    public void addDataWithoutCurr(ArrayList<News> data, ArrayList<Integer> types){
        clearNewFeed();
        models.addAll(data);
        dynamicTypes.addAll(types);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dynamicTypes.get(position);
    }

    public void updateData(ArrayList<News> updatedModel) {
        models.clear();
        models.addAll(updatedModel);
        notifyDataSetChanged();
    }
}