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
     * @param pageNumber by default page number is 0, by default call 1st
     * @return
     */
    @Headers("Content-Type: application/json")
    @GET("/api/get_news")
    Call<Page> getAllNews(@Query("page") Integer pageNumber);

    /**
     * @param id page id
     * @return
     */
    @Headers("Content-Type: application/json")
    @GET("/api/get_news_item/{id}")
    Call<Article> getItemById(@Path("id") Integer id);

    /**
     * @param searchValue value, which will be used as keyword in search request
     * @param pageNumber  number of page for searching
     * @return
     */
    @Headers("Content-Type: application/json")
    @GET("/api/search")
    Call<Search> getSearchResult(@Query("search") String searchValue, @Query("page") Integer pageNumber);


    // @Body  use in tokens for pojo token
    /**
     * @param tokenValue   device token to register device in klops.ru database
     */
    @Headers("Content-Type: application/json")
    @POST("/api/subscribe_token")
    Call<ResponseBody> subscribeNotification(@Query("device_token") String tokenValue, @Query("platform") String platform);

    /**
     * @param deviceToken device token to unregister device in klops.ru database
     */
    @Headers("Content-Type: application/json")
    @POST("/api/unsubscribe_token")
    Call<ResponseBody> unSubscribeNotification(@Query("device_token") String deviceToken, @Query("platform") String platform);

}

