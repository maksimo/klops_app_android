package ru.klops.klops.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
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

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.klops.klops.ArticleActivity;
import ru.klops.klops.R;
import ru.klops.klops.cutomfonts.CircleImageView;
import ru.klops.klops.cutomfonts.TextViewProBold;
import ru.klops.klops.cutomfonts.TextViewProBoldEx;
import ru.klops.klops.cutomfonts.TextViewProLight;
import ru.klops.klops.cutomfonts.TextViewProMd;
import ru.klops.klops.cutomfonts.TextViewProMdEx;
import ru.klops.klops.cutomfonts.TextViewProRegular;
import ru.klops.klops.cutomfonts.TextViewProSuper;
import ru.klops.klops.cutomfonts.singletons.ProBold;
import ru.klops.klops.cutomfonts.singletons.ProLight;
import ru.klops.klops.cutomfonts.singletons.ProMd;
import ru.klops.klops.cutomfonts.singletons.ProRegular;
import ru.klops.klops.cutomfonts.singletons.ProSuper;
import ru.klops.klops.fragments.NewDataNewsFragment;
import ru.klops.klops.models.feed.News;
import ru.klops.klops.services.ServiceArticleLoader;
import ru.klops.klops.utils.Constants;

public class RVNewDataAdapter extends RecyclerView.Adapter<RVNewDataAdapter.ViewHolder> {
    NewDataNewsFragment context;
    ArrayList<News> models;
    int[] cardTypes;
    ArrayList<Integer> dynamicTypes;
    Animation alpha;

    public RVNewDataAdapter(NewDataNewsFragment context, ArrayList<News> models, int[] cardTypes) {
        this.models = models;
        this.context = context;
        this.cardTypes = cardTypes;
    }

    public RVNewDataAdapter(NewDataNewsFragment context, ArrayList<News> models, ArrayList<Integer> dynamicTypes) {
        this.models = models;
        this.context = context;
        this.dynamicTypes = dynamicTypes;
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
        @BindView(R.id.simpleImageCard)
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
        @BindView(R.id.simpleCard)
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
        @BindView(R.id.longCard)
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
        @BindView(R.id.interviewCard)
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
        @BindView(R.id.authorCard)
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
        @BindView(R.id.importantCard)
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
        @BindView(R.id.galleryCardOne)
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
        @BindView(R.id.galleryCardTwo)
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
        @BindView(R.id.advertiseCardDate)
        TextView date;
        @BindView(R.id.advertiseCardTitle)
        TextView title;
        @BindView(R.id.advertiseCardCommercial)
        TextView commercial;
        @BindView(R.id.advertiseCard)
        RelativeLayout adsCard;

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
        @BindView(R.id.simpleWideCard)
        RelativeLayout simpleWideCard;
        @BindView(R.id.simpleWideCardAuthor)
        TextView author;

        public SimpleWideHolder(View itemView) {
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
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case Constants.SIMPLE_WITH_IMG:
                final SimpleNewsHolder holder = (SimpleNewsHolder) viewHolder;
                Picasso.with(context.getActivity()).load(models.get(position).getImage()).into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        holder.bar.setVisibility(View.VISIBLE);
                    }
                });
                holder.date.setText(models.get(position).getDate());
                holder.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holder.title.setText(models.get(position).getTitle());
                holder.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-md.ttf"));
                holder.simpleImageCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.simpleImageCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holder.getAdapterPosition()).getId()));

                    }
                });
                break;
            case Constants.SIMPLE_TEXT_NEWS:
                final SimpleTextNewsHolder holderSimple = (SimpleTextNewsHolder) viewHolder;
                holderSimple.date.setText(models.get(position).getDate());
                holderSimple.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderSimple.title.setText(models.get(position).getTitle());
                holderSimple.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-md.ttf"));
                holderSimple.simpleCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderSimple.simpleCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderSimple.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.LONG:
                final LongNewsHolder holderLong = (LongNewsHolder) viewHolder;
                holderLong.date.setText(models.get(position).getDate());
                holderLong.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderLong.author.setText(models.get(position).getAuthor());
                holderLong.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-bold.ttf"));
                holderLong.title.setText(models.get(position).getTitle());
                holderLong.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-md.ttf"));
                holderLong.content.setText(models.get(position).getShortdecription());
                holderLong.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-light.ttf"));
                holderLong.longCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderLong.longCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderLong.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.INTERVIEW:
                final InterviewNewsHolder holderInterview = (InterviewNewsHolder) viewHolder;
                holderInterview.date.setText(models.get(position).getDate());
                holderInterview.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderInterview.title.setText(models.get(position).getTitle());
                holderInterview.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-md.ttf"));
                holderInterview.content.setText(models.get(position).getShortdecription());
                holderInterview.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-light.ttf"));
                Picasso.with(context.getActivity()).load(models.get(position).getImage()).into(holderInterview.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holderInterview.bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        holderInterview.bar.setVisibility(View.VISIBLE);
                    }
                });
                holderInterview.interviewCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderInterview.interviewCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderInterview.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.AUTHORS:
                final AuthorColumnsNewsHolder holderAuthors = (AuthorColumnsNewsHolder) viewHolder;
                holderAuthors.date.setText(models.get(position).getDate());
                holderAuthors.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderAuthors.title.setText(models.get(position).getTitle());
                holderAuthors.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-md.ttf"));
                holderAuthors.content.setText(models.get(position).getShortdecription());
                holderAuthors.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-light.ttf"));
                Picasso.with(context.getActivity()).load(models.get(position).getImage()).into(holderAuthors.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holderAuthors.bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        holderAuthors.bar.setVisibility(View.VISIBLE);
                    }
                });

                holderAuthors.contentAuthor.setText(models.get(position).getAuthor());
                holderAuthors.contentAuthor.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-bold.ttf"));
                holderAuthors.authorsCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderAuthors.authorsCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderAuthors.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.NATIONAL:
                final NationalNewsHolder holderNational = (NationalNewsHolder) viewHolder;
                holderNational.author.setText(models.get(position).getAuthor());
                holderNational.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-bold.ttf"));
                holderNational.dateOne.setText(models.get(position).getDate());
                holderNational.dateOne.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderNational.titleOne.setText(models.get(position).getTitle());
                holderNational.titleOne.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-light.ttf"));
                holderNational.nationalCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderNational.nationalCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderNational.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.IMPORTANT:
                final ImportantNewsWithPhotoHolder holderImportant = (ImportantNewsWithPhotoHolder) viewHolder;
                if (models.get(position).getImage() == null) {
                    holderImportant.image.setVisibility(View.GONE);
                } else {
                    Picasso.with(context.getActivity()).load(models.get(position).getImage()).into(holderImportant.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holderImportant.bar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onError() {
                            holderImportant.bar.setVisibility(View.VISIBLE);
                        }
                    });
                }
                holderImportant.date.setText(models.get(position).getDate());
                holderImportant.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderImportant.status.setText(models.get(position).getUpdate_status());
                holderImportant.status.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderImportant.title.setText(models.get(position).getTitle());
                holderImportant.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-super.ttf"));
                holderImportant.content.setText(models.get(position).getShortdecription());
                holderImportant.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-light.ttf"));
                holderImportant.important.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderImportant.important.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderImportant.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.GALLERY_ONE:
                final GalleryNewsOneHolder holderGallerySmall = (GalleryNewsOneHolder) viewHolder;
                holderGallerySmall.date.setText(models.get(position).getDate());
                holderGallerySmall.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderGallerySmall.title.setText(String.format("%1$" + 5 + "s", models.get(position).getTitle()));
                holderGallerySmall.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-super.ttf"));
                Picasso.with(context.getActivity()).load(models.get(position).getImage()).into(holderGallerySmall.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holderGallerySmall.bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        holderGallerySmall.bar.setVisibility(View.VISIBLE);
                    }
                });
                holderGallerySmall.galleryCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderGallerySmall.galleryCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderGallerySmall.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.GALLERY_TWO:
                final GalleryNewsTwoHolder holderGalleryBig = (GalleryNewsTwoHolder) viewHolder;
                holderGalleryBig.date.setText(models.get(position).getDate());
                holderGalleryBig.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderGalleryBig.title.setText(String.format("%1$" + 5 + "s", models.get(position).getTitle()));
                holderGalleryBig.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-super.ttf"));
                if (models.get(position).getPhotos().size() == 0) {
                    holderGalleryBig.imageOnlyOne.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageOne.setVisibility(View.GONE);
                    holderGalleryBig.imageTwo.setVisibility(View.GONE);
                    holderGalleryBig.barFirst.setVisibility(View.GONE);
                    holderGalleryBig.barSecond.setVisibility(View.GONE);
                    holderGalleryBig.barOneBig.setVisibility(View.VISIBLE);
                    if (models.get(position).getImage() != null) {
                        Picasso.with(context.getActivity()).load(models.get(position).getImage()).into(holderGalleryBig.imageOnlyOne, new Callback() {
                            @Override
                            public void onSuccess() {
                                holderGalleryBig.barOneBig.setVisibility(View.GONE);

                            }

                            @Override
                            public void onError() {
                                holderGalleryBig.barOneBig.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                } else {
                    holderGalleryBig.imageOne.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageTwo.setVisibility(View.VISIBLE);
                    holderGalleryBig.barFirst.setVisibility(View.VISIBLE);
                    holderGalleryBig.barSecond.setVisibility(View.VISIBLE);
                    holderGalleryBig.imageOnlyOne.setVisibility(View.GONE);
                    holderGalleryBig.barOneBig.setVisibility(View.GONE);
                    Picasso.with(context.getActivity()).load(models.get(position).getPhotos().get(0)).into(holderGalleryBig.imageOne, new Callback() {
                        @Override
                        public void onSuccess() {
                            holderGalleryBig.barFirst.setVisibility(View.GONE);

                        }

                        @Override
                        public void onError() {
                            holderGalleryBig.barFirst.setVisibility(View.VISIBLE);
                        }
                    });
                    Picasso.with(context.getActivity()).load(models.get(position).getPhotos().get(1)).into(holderGalleryBig.imageTwo, new Callback() {
                        @Override
                        public void onSuccess() {
                            holderGalleryBig.barSecond.setVisibility(View.GONE);

                        }

                        @Override
                        public void onError() {
                            holderGalleryBig.barSecond.setVisibility(View.VISIBLE);
                        }
                    });
                }
                holderGalleryBig.galleryCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderGalleryBig.galleryCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderGalleryBig.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.ADVERTISE:
                final AdvertiseHolder holderAdvertise = (AdvertiseHolder) viewHolder;
                Picasso.with(context.getActivity()).load(models.get(position).getImage()).into(holderAdvertise.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holderAdvertise.bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        holderAdvertise.bar.setVisibility(View.VISIBLE);
                    }
                });
                holderAdvertise.date.setText(models.get(position).getDate());
                holderAdvertise.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderAdvertise.title.setText(models.get(position).getTitle());
                holderAdvertise.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-super.ttf"));
                holderAdvertise.adsCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderAdvertise.adsCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderAdvertise.getAdapterPosition()).getId()));
                    }
                });
                break;
            case Constants.SIMPLE_WIDE:
                final SimpleWideHolder holderSimpleWide = (SimpleWideHolder) viewHolder;
                holderSimpleWide.author.setText(models.get(position).getAuthor());
                holderSimpleWide.author.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-bold.ttf"));
                holderSimpleWide.date.setText(models.get(position).getDate());
                holderSimpleWide.date.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-regular.ttf"));
                holderSimpleWide.title.setText(models.get(position).getTitle());
                holderSimpleWide.title.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-md.ttf"));
                holderSimpleWide.content.setText(models.get(position).getShortdecription());
                holderSimpleWide.content.setTypeface(Typeface.createFromAsset(context.getContext().getAssets(),"fonts/akzidenzgroteskpro-light.ttf"));
                holderSimpleWide.simpleWideCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holderSimpleWide.simpleWideCard.startAnimation(alpha);
                        context.getActivity().startService(new Intent(context.getContext(), ServiceArticleLoader.class).putExtra(Constants.ARTICLE_ID, models.get(holderSimpleWide.getAdapterPosition()).getId()));
                    }
                });
                break;
        }
    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dynamicTypes.get(position);
    }

    public ArrayList<Integer> checkTypes(ArrayList<News> data) {
        ArrayList<Integer> reloadedTypes = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String article = data.get(i).getArticle_type();
            switch (article) {
                case Constants.SIMPLE_IMAGE_TEXT:
                    reloadedTypes.add(Constants.SIMPLE_WITH_IMG);
                    break;
                case Constants.SIMPLE_TEXT:
                    reloadedTypes.add(Constants.SIMPLE_TEXT_NEWS);
                    break;
                case Constants.LONG_TEXT:
                    reloadedTypes.add(Constants.LONG);
                    break;
                case Constants.INTERVIEW_TEXT:
                    reloadedTypes.add(Constants.INTERVIEW);
                    break;
                case Constants.AUTHORS_TEXT:
                    reloadedTypes.add(Constants.AUTHORS);
                    break;
                case Constants.NATIONAL_TEXT:
                    reloadedTypes.add(Constants.NATIONAL);
                    break;
                case Constants.IMPORTANT_TEXT:
                    reloadedTypes.add(Constants.IMPORTANT);
                    break;
                case Constants.GALLERY_FIRST_TEXT:
                    reloadedTypes.add(Constants.GALLERY_ONE);
                    break;
                case Constants.GALLERY_SECOND_TEXT:
                    reloadedTypes.add(Constants.GALLERY_TWO);
                    break;
                case Constants.ADS_TEXT:
                    reloadedTypes.add(Constants.ADVERTISE);
                    break;
                case Constants.SIMPLE_WIDE_TEXT:
                    reloadedTypes.add(Constants.SIMPLE_WIDE);
                    break;
            }
        }
        dynamicTypes.clear();
        dynamicTypes.addAll(reloadedTypes);
        return reloadedTypes;
    }

}