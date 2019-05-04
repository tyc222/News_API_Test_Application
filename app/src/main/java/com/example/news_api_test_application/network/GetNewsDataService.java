package com.example.news_api_test_application.network;

import com.example.news_api_test_application.model.ArticleList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetNewsDataService {

    @GET("top-headlines")
    Call<ArticleList> getArticleData(@Query("country") String country,@Query("category") String category ,@Query("apiKey") String apiKey);
}
