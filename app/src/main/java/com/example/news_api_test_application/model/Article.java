package com.example.news_api_test_application.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Url;

public class Articles {

    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private Url url;
    @SerializedName("urlToImage")
    private Url urlToImage;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("content")
    private String content;

    
}
