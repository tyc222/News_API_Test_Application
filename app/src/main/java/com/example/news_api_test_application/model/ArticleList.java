package com.example.news_api_test_application.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArticleList {

    @SerializedName("articles")
    private ArrayList<Article> articleArrayList;

    public ArrayList<Article> getArticleList() {
        return articleArrayList;
    }

    public void setArticleList(ArrayList<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
    }
}
