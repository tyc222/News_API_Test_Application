package com.example.news_api_test_application.viewer;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news_api_test_application.R;
import com.example.news_api_test_application.activity.MainActivity;
import com.example.news_api_test_application.model.Article;
import com.example.news_api_test_application.model.Source;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private ArrayList<Article> articleList;
    private Context context;

    public ArticleAdapter (ArrayList<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }




    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.article_view_item, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ArticleViewHolder articleViewHolder, int i) {
    Picasso.with(context).load(articleList.get(i).getUrlToImage()).into(articleViewHolder.empImage);
    articleViewHolder.empPublishedAt.setText(articleList.get(i).getPublishedAt().replace("T", "  ").replace("Z", "  "));
    articleViewHolder.empTitle.setText(articleList.get(i).getTitle());
    articleViewHolder.empDescription.setText(articleList.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{

        ImageView empImage;
        TextView empPublishedAt, empTitle, empDescription;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            empImage = itemView.findViewById(R.id.img_article);
            empPublishedAt = itemView.findViewById(R.id.publish_date_article);
            empTitle = itemView.findViewById(R.id.title_article);
            empDescription = itemView.findViewById(R.id.description_article);
        }
    }

}
