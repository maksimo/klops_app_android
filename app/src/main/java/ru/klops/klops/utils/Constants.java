package ru.klops.klops.utils;

public class Constants {
    // retrofit endpoint
    public static final String BASE_API_URL = "http://klops.ru";
    // articles types
    public static final int SIMPLE_WITH_IMG = 1;
    public static final int SIMPLE_TEXT_NEWS = 2;
    public static final int LONG = 3;
    public static final int INTERVIEW = 4;
    public static final int AUTHORS = 5;
    public static final int NATIONAL = 6;
    public static final int IMPORTANT = 7;
    public static final int GALLERY_ONE = 8;
    public static final int GALLERY_TWO = 9;
    public static final int ADVERTISE = 10;
    public static final int SIMPLE_WIDE = 11;
    // articles String types
    public static final String SIMPLE_IMAGE_TEXT = "simpleWithImage";
    public static final String SIMPLE_TEXT = "simple";
    public static final String LONG_TEXT = "longText";
    public static final String INTERVIEW_TEXT = "Interview";
    public static final String AUTHORS_TEXT = "editorial";
    public static final String NATIONAL_TEXT = "popular";
    public static final String IMPORTANT_TEXT = "main";
    public static final String GALLERY_FIRST_TEXT = "photo1in1";
    public static final String GALLERY_SECOND_TEXT = "photo2in1";
    public static final String ADS_TEXT = "ads";
    public static final String SIMPLE_WIDE_TEXT = "simple_wide";
    // Item text array Objects
    public static final String TEXT = "text";
    public static final String LINK = "link";
    public static final String IMAGE = "image";
    public static final String TITLE = "title";
    // Intents to Article Activity
    public static final String ARTICLE_SEARCH = "ARTICLE_SEARCH";
    public static final String ARTICLE_FEED = "ARTICLE_FEED";
    public static final String ARTICLE = "ARTICLE";
    public static final String ARTICLE_ID = "ID";
    public static final String RECEIVE_ACTION = "ru.klops.klops.intent.action.requestArticle";
    public static final String RECEIVE_SEARCH = "ru.klops.klops.intent.action.requestSearchArticle";
    public static final String ITEM = "ITEM";
    public static final String SEARCH_RESULT = "SEARCH_RESULT";
}
