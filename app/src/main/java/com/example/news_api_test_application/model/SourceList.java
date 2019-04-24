package com.example.news_api_test_application.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SourceList {

    public ArrayList<Source> getSourceArrayList() {
        return sourceArrayList;
    }

    public void setSourceArrayList(ArrayList<Source> sourceArrayList) {
        this.sourceArrayList = sourceArrayList;
    }

    @SerializedName("source")
    private ArrayList<Source> sourceArrayList;
}
