package ru.klops.klops.models.search;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable{

    public Integer id;
    public Integer doc_rubric_id;
    public String title;
    public String annotation;
    public String text;
    public Boolean main;
    public String created_at;
    public String updated_at;
    public String gallery_author;
    public String param;
    public Boolean approved;
    public Boolean skip_sponsor_banner;
    public Boolean in_rss;
    public Integer views_rating;
    public Integer site_id;
    public Integer user_id;
    public String approved_at;
    public Integer page_views;
    public String text_hash;
    public Boolean watermark;
    public String search;
    public Boolean pictureless;
    public Integer doc_topic_id;
    public Boolean head;
    public Boolean subhead;
    public String update_status;
    public Integer comment_counter;
    public Boolean allowed_for_most_block;
    public String interview_caption;
    public Integer author_column_id;
    public Boolean pr;
    public Boolean on_news_list;
    public String source;
    public Integer main_position;
    public Boolean commentless;
    public Integer publisher_id;
    public Boolean top;
    public Boolean post_to_twitter;
    public Boolean in_news_feed;
    public Boolean show_map;
    public Boolean social_embed;
    public String contact_text;
    public Boolean show_image_in_feed_news;
    public Boolean show_author;
    public Integer galleries_count;
    public Integer persons_count;
    public Boolean hot;
    public Boolean small_head;
    public Integer doc_sponsor_id;
    public Boolean post_to_telegram;
    public Boolean telegram_posted;
    public Boolean post_to_telegram_breaking_klops;
    public Boolean telegram_breaking_klops_posted;

    public News() {
    }

    public News(Integer id, Integer doc_rubric_id, String title, String annotation, String text, Boolean main, String created_at, String updated_at, String gallery_author, String param, Boolean approved, Boolean skip_sponsor_banner, Boolean in_rss, Integer views_rating, Integer site_id, Integer user_id, String approved_at, Integer page_views, String text_hash, Boolean watermark, String search, Boolean pictureless, Integer doc_topic_id, Boolean head, Boolean subhead, String update_status, Integer comment_counter, Boolean allowed_for_most_block, String interview_caption, Integer author_column_id, Boolean pr, Boolean on_news_list, String source, Integer main_position, Boolean commentless, Integer publisher_id, Boolean top, Boolean post_to_twitter, Boolean in_news_feed, Boolean show_map, Boolean social_embed, String contact_text, Boolean show_image_in_feed_news, Boolean show_author, Integer galleries_count, Integer persons_count, Boolean hot, Boolean small_head, Integer doc_sponsor_id, Boolean post_to_telegram, Boolean telegram_posted, Boolean post_to_telegram_breaking_klops, Boolean telegram_breaking_klops_posted) {
        this.id = id;
        this.doc_rubric_id = doc_rubric_id;
        this.title = title;
        this.annotation = annotation;
        this.text = text;
        this.main = main;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.gallery_author = gallery_author;
        this.param = param;
        this.approved = approved;
        this.skip_sponsor_banner = skip_sponsor_banner;
        this.in_rss = in_rss;
        this.views_rating = views_rating;
        this.site_id = site_id;
        this.user_id = user_id;
        this.approved_at = approved_at;
        this.page_views = page_views;
        this.text_hash = text_hash;
        this.watermark = watermark;
        this.search = search;
        this.pictureless = pictureless;
        this.doc_topic_id = doc_topic_id;
        this.head = head;
        this.subhead = subhead;
        this.update_status = update_status;
        this.comment_counter = comment_counter;
        this.allowed_for_most_block = allowed_for_most_block;
        this.interview_caption = interview_caption;
        this.author_column_id = author_column_id;
        this.pr = pr;
        this.on_news_list = on_news_list;
        this.source = source;
        this.main_position = main_position;
        this.commentless = commentless;
        this.publisher_id = publisher_id;
        this.top = top;
        this.post_to_twitter = post_to_twitter;
        this.in_news_feed = in_news_feed;
        this.show_map = show_map;
        this.social_embed = social_embed;
        this.contact_text = contact_text;
        this.show_image_in_feed_news = show_image_in_feed_news;
        this.show_author = show_author;
        this.galleries_count = galleries_count;
        this.persons_count = persons_count;
        this.hot = hot;
        this.small_head = small_head;
        this.doc_sponsor_id = doc_sponsor_id;
        this.post_to_telegram = post_to_telegram;
        this.telegram_posted = telegram_posted;
        this.post_to_telegram_breaking_klops = post_to_telegram_breaking_klops;
        this.telegram_breaking_klops_posted = telegram_breaking_klops_posted;
    }


    protected News(Parcel in) {
        title = in.readString();
        annotation = in.readString();
        text = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        gallery_author = in.readString();
        param = in.readString();
        approved_at = in.readString();
        text_hash = in.readString();
        search = in.readString();
        update_status = in.readString();
        interview_caption = in.readString();
        source = in.readString();
        contact_text = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoc_rubric_id() {
        return doc_rubric_id;
    }

    public void setDoc_rubric_id(Integer doc_rubric_id) {
        this.doc_rubric_id = doc_rubric_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getGallery_author() {
        return gallery_author;
    }

    public void setGallery_author(String gallery_author) {
        this.gallery_author = gallery_author;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getSkip_sponsor_banner() {
        return skip_sponsor_banner;
    }

    public void setSkip_sponsor_banner(Boolean skip_sponsor_banner) {
        this.skip_sponsor_banner = skip_sponsor_banner;
    }

    public Boolean getIn_rss() {
        return in_rss;
    }

    public void setIn_rss(Boolean in_rss) {
        this.in_rss = in_rss;
    }

    public Integer getViews_rating() {
        return views_rating;
    }

    public void setViews_rating(Integer views_rating) {
        this.views_rating = views_rating;
    }

    public Integer getSite_id() {
        return site_id;
    }

    public void setSite_id(Integer site_id) {
        this.site_id = site_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getApproved_at() {
        return approved_at;
    }

    public void setApproved_at(String approved_at) {
        this.approved_at = approved_at;
    }

    public Integer getPage_views() {
        return page_views;
    }

    public void setPage_views(Integer page_views) {
        this.page_views = page_views;
    }

    public String getText_hash() {
        return text_hash;
    }

    public void setText_hash(String text_hash) {
        this.text_hash = text_hash;
    }

    public Boolean getWatermark() {
        return watermark;
    }

    public void setWatermark(Boolean watermark) {
        this.watermark = watermark;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Boolean getPictureless() {
        return pictureless;
    }

    public void setPictureless(Boolean pictureless) {
        this.pictureless = pictureless;
    }

    public Integer getDoc_topic_id() {
        return doc_topic_id;
    }

    public void setDoc_topic_id(Integer doc_topic_id) {
        this.doc_topic_id = doc_topic_id;
    }

    public Boolean getHead() {
        return head;
    }

    public void setHead(Boolean head) {
        this.head = head;
    }

    public Boolean getSubhead() {
        return subhead;
    }

    public void setSubhead(Boolean subhead) {
        this.subhead = subhead;
    }

    public String getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    public Integer getComment_counter() {
        return comment_counter;
    }

    public void setComment_counter(Integer comment_counter) {
        this.comment_counter = comment_counter;
    }

    public Boolean getAllowed_for_most_block() {
        return allowed_for_most_block;
    }

    public void setAllowed_for_most_block(Boolean allowed_for_most_block) {
        this.allowed_for_most_block = allowed_for_most_block;
    }

    public String getInterview_caption() {
        return interview_caption;
    }

    public void setInterview_caption(String interview_caption) {
        this.interview_caption = interview_caption;
    }

    public Integer getAuthor_column_id() {
        return author_column_id;
    }

    public void setAuthor_column_id(Integer author_column_id) {
        this.author_column_id = author_column_id;
    }

    public Boolean getPr() {
        return pr;
    }

    public void setPr(Boolean pr) {
        this.pr = pr;
    }

    public Boolean getOn_news_list() {
        return on_news_list;
    }

    public void setOn_news_list(Boolean on_news_list) {
        this.on_news_list = on_news_list;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getMain_position() {
        return main_position;
    }

    public void setMain_position(Integer main_position) {
        this.main_position = main_position;
    }

    public Boolean getCommentless() {
        return commentless;
    }

    public void setCommentless(Boolean commentless) {
        this.commentless = commentless;
    }

    public Integer getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(Integer publisher_id) {
        this.publisher_id = publisher_id;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getPost_to_twitter() {
        return post_to_twitter;
    }

    public void setPost_to_twitter(Boolean post_to_twitter) {
        this.post_to_twitter = post_to_twitter;
    }

    public Boolean getIn_news_feed() {
        return in_news_feed;
    }

    public void setIn_news_feed(Boolean in_news_feed) {
        this.in_news_feed = in_news_feed;
    }

    public Boolean getShow_map() {
        return show_map;
    }

    public void setShow_map(Boolean show_map) {
        this.show_map = show_map;
    }

    public Boolean getSocial_embed() {
        return social_embed;
    }

    public void setSocial_embed(Boolean social_embed) {
        this.social_embed = social_embed;
    }

    public String getContact_text() {
        return contact_text;
    }

    public void setContact_text(String contact_text) {
        this.contact_text = contact_text;
    }

    public Boolean getShow_image_in_feed_news() {
        return show_image_in_feed_news;
    }

    public void setShow_image_in_feed_news(Boolean show_image_in_feed_news) {
        this.show_image_in_feed_news = show_image_in_feed_news;
    }

    public Boolean getShow_author() {
        return show_author;
    }

    public void setShow_author(Boolean show_author) {
        this.show_author = show_author;
    }

    public Integer getGalleries_count() {
        return galleries_count;
    }

    public void setGalleries_count(Integer galleries_count) {
        this.galleries_count = galleries_count;
    }

    public Integer getPersons_count() {
        return persons_count;
    }

    public void setPersons_count(Integer persons_count) {
        this.persons_count = persons_count;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public Boolean getSmall_head() {
        return small_head;
    }

    public void setSmall_head(Boolean small_head) {
        this.small_head = small_head;
    }

    public Integer getDoc_sponsor_id() {
        return doc_sponsor_id;
    }

    public void setDoc_sponsor_id(Integer doc_sponsor_id) {
        this.doc_sponsor_id = doc_sponsor_id;
    }

    public Boolean getPost_to_telegram() {
        return post_to_telegram;
    }

    public void setPost_to_telegram(Boolean post_to_telegram) {
        this.post_to_telegram = post_to_telegram;
    }

    public Boolean getTelegram_posted() {
        return telegram_posted;
    }

    public void setTelegram_posted(Boolean telegram_posted) {
        this.telegram_posted = telegram_posted;
    }

    public Boolean getPost_to_telegram_breaking_klops() {
        return post_to_telegram_breaking_klops;
    }

    public void setPost_to_telegram_breaking_klops(Boolean post_to_telegram_breaking_klops) {
        this.post_to_telegram_breaking_klops = post_to_telegram_breaking_klops;
    }

    public Boolean getTelegram_breaking_klops_posted() {
        return telegram_breaking_klops_posted;
    }

    public void setTelegram_breaking_klops_posted(Boolean telegram_breaking_klops_posted) {
        this.telegram_breaking_klops_posted = telegram_breaking_klops_posted;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", doc_rubric_id=" + doc_rubric_id +
                ", title='" + title + '\'' +
                ", annotation='" + annotation + '\'' +
                ", text='" + text + '\'' +
                ", main=" + main +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", gallery_author='" + gallery_author + '\'' +
                ", param='" + param + '\'' +
                ", approved=" + approved +
                ", skip_sponsor_banner=" + skip_sponsor_banner +
                ", in_rss=" + in_rss +
                ", views_rating=" + views_rating +
                ", site_id=" + site_id +
                ", user_id=" + user_id +
                ", approved_at='" + approved_at + '\'' +
                ", page_views=" + page_views +
                ", text_hash='" + text_hash + '\'' +
                ", watermark=" + watermark +
                ", search='" + search + '\'' +
                ", pictureless=" + pictureless +
                ", doc_topic_id=" + doc_topic_id +
                ", head=" + head +
                ", subhead=" + subhead +
                ", update_status='" + update_status + '\'' +
                ", comment_counter=" + comment_counter +
                ", allowed_for_most_block=" + allowed_for_most_block +
                ", interview_caption='" + interview_caption + '\'' +
                ", author_column_id=" + author_column_id +
                ", pr=" + pr +
                ", on_news_list=" + on_news_list +
                ", source='" + source + '\'' +
                ", main_position=" + main_position +
                ", commentless=" + commentless +
                ", publisher_id=" + publisher_id +
                ", top=" + top +
                ", post_to_twitter=" + post_to_twitter +
                ", in_news_feed=" + in_news_feed +
                ", show_map=" + show_map +
                ", social_embed=" + social_embed +
                ", contact_text='" + contact_text + '\'' +
                ", show_image_in_feed_news=" + show_image_in_feed_news +
                ", show_author=" + show_author +
                ", galleries_count=" + galleries_count +
                ", persons_count=" + persons_count +
                ", hot=" + hot +
                ", small_head=" + small_head +
                ", doc_sponsor_id=" + doc_sponsor_id +
                ", post_to_telegram=" + post_to_telegram +
                ", telegram_posted=" + telegram_posted +
                ", post_to_telegram_breaking_klops=" + post_to_telegram_breaking_klops +
                ", telegram_breaking_klops_posted=" + telegram_breaking_klops_posted +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(annotation);
        dest.writeString(text);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(gallery_author);
        dest.writeString(param);
        dest.writeString(approved_at);
        dest.writeString(text_hash);
        dest.writeString(search);
        dest.writeString(update_status);
        dest.writeString(interview_caption);
        dest.writeString(source);
        dest.writeString(contact_text);
    }
}
