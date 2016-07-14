package ru.klops.klops.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.klops.klops.models.article.Article;
import ru.klops.klops.models.feed.Page;
import ru.klops.klops.models.popular.Popular;
import ru.klops.klops.models.search.Search;
import rx.Observable;


public interface PageApi {
    /**
     * @return Page with list of news inside
     */
    @Headers("Content-Type: application/json")
    @GET("get_news")
    Observable<Page> getAllNews();

    /**
     * @return Popular news with list of news inside
     */
    @Headers("Content-Type: application/json")
    @GET("get_popular")
    Observable<Popular> getPopularNews();

    /**
     * @param id page id
     * @return Article data with current id
     */
    @Headers("Content-Type: application/json")
    @GET("get_news_item/{id}")
    Observable<Article> getItemById(@Path("id") Integer id);

    /**
     * @param searchValue value, which will be used as keyword in search request
     * @return Search results , contains list of articles
     */
    @Headers("Content-Type: application/json")
    @GET("search")
    Observable<Search> getSearchResult(@Query("search") String searchValue);

    /**
     * @param tokenValue   device token to register device in klops.ru database
     * @param platform android platform
     * @return 200 if request is success, 403 if failed
     */
    @Headers("Content-Type: application/json")
    @POST("subscribe_token")
    Observable<ResponseBody> subscribeNotification(@Query("device_token") String tokenValue, @Query("platform") String platform);

    /**
     * @param deviceToken device token to unregister device in klops.ru database
     * @param platform android platform
     * @return 200 if request is success, 403 if failed
     */
    @Headers("Content-Type: application/json")
    @POST("unsubscribe_token")
    Observable<ResponseBody> unSubscribeNotification(@Query("device_token") String deviceToken, @Query("platform") String platform);

}

