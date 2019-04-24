package com.example.news_api_test_application.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArticleList {

    @SerializedName("articles")
    private ArrayList<Article> articleList;

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(ArrayList<Article> articleList) {
        this.articleList = articleList;
    }
}
