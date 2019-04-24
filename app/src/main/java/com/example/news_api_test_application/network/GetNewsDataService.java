package com.example.news_api_test_application.network;

import com.example.news_api_test_application.model.ArticleList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetNewsDataService {
    @GET("top-headlines")
    Call<ArticleList> getArticleData(@Query("country=au" + "&" + "apiKey=30f23670bbb5441bbd9e77746df08fd4") int countryNo);
}
