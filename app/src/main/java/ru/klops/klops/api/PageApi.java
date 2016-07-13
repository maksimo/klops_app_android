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
import ru.klops.klops.models.search.Search;


public interface PageApi {
    /**
     *
     * @return Page with news
     */
    @Headers("Content-Type: application/json")
    @GET("get_news")
    Call<Page> getAllNews();

    /**
     * @param id page id
     * @return Article data with current id
     */
    @Headers("Content-Type: application/json")
    @GET("get_news_item/{id}")
    Call<Article> getItemById(@Path("id") Integer id);

    /**
     * @param searchValue value, which will be used as keyword in search request
     * @param pageNumber  number of page for searching
     * @return Search results , contains list of articles
     */
    @Headers("Content-Type: application/json")
    @GET("search")
    Call<Search> getSearchResult(@Query("search") String searchValue, @Query("page") Integer pageNumber);


    // @Body  use in tokens for pojo token
    /**
     * @param tokenValue   device token to register device in klops.ru database
     * @param platform android platform
     * @return 200 if request is success, 403 if failed
     */
    @Headers("Content-Type: application/json")
    @POST("subscribe_token")
    Call<ResponseBody> subscribeNotification(@Query("device_token") String tokenValue, @Query("platform") String platform);

    /**
     * @param deviceToken device token to unregister device in klops.ru database
     * @param platform android platform
     * @return 200 if request is success, 403 if failed
     */
    @Headers("Content-Type: application/json")
    @POST("unsubscribe_token")
    Call<ResponseBody> unSubscribeNotification(@Query("device_token") String deviceToken, @Query("platform") String platform);

}

