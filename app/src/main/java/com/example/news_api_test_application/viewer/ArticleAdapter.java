package com.example.news_api_test_application.viewer;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news_api_test_application.R;
import com.example.news_api_test_application.model.Article;
import com.example.news_api_test_application.model.Source;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private ArrayList<Article> articleList;

    public ArticleAdapter (ArrayList<Article> articleList) {
        this.articleList = articleList;
    }

    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.article_view_item, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ArticleViewHolder articleViewHolder, int i) {
    articleViewHolder.empImage.setImageURI(Uri.parse(articleList.get(i).getUrlToImage()));
    articleViewHolder.empSourceName.setText(articleList.get(i).getSourceArrayList());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{

        ImageView empImage;
        TextView empSourceName, empTitle, empDescription;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            empImage = itemView.findViewById(R.id.img_article);
            empSourceName = itemView.findViewById(R.id.source_name_article);
            empTitle = itemView.findViewById(R.id.title_article);
            empDescription = itemView.findViewById(R.id.description_article);
        }
    }
}
