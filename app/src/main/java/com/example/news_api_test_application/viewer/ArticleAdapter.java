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
import android.widget.LinearLayout;
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
    private OnCustomClickListerner onCustomClickListerner;
    private OnCustomLongclickListener onCustomLongclickListener;

    public ArticleAdapter (ArrayList<Article> articleList, Context context, OnCustomClickListerner onCustomClickListerner, OnCustomLongclickListener onCustomLongclickListener) {
        this.articleList = articleList;
        this.context = context;
        this.onCustomClickListerner = onCustomClickListerner;
        this.onCustomLongclickListener = onCustomLongclickListener;
    }

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(ArrayList<Article> articleList) {
        this.articleList = articleList;
    }

    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.article_view_item, viewGroup, false);
        return new ArticleViewHolder(view, onCustomClickListerner, onCustomLongclickListener);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ArticleViewHolder articleViewHolder, int i) {

        if (articleList.get(i).getDescription() == "" ||articleList.get(i).getDescription() == null) {
            articleViewHolder.itemView.setVisibility(View.GONE);
            articleViewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        }
            Picasso.with(context)
                    .load(articleList.get(i).getUrlToImage())
                    .placeholder(R.mipmap.image_unavailable_picture)
                    .error(R.mipmap.image_unavailable_picture)
                    .into(articleViewHolder.empImage);

    articleViewHolder.empPublishedAt.setText(articleList.get(i).getPublishedAt().replace("T", "  ").replace("Z", "  "));
    articleViewHolder.empTitle.setText(articleList.get(i).getTitle());
    articleViewHolder.empDescription.setText(articleList.get(i).getDescription());

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        ImageView empImage;
        TextView empPublishedAt, empTitle, empDescription;
        OnCustomClickListerner onCustomClickListerner;
        OnCustomLongclickListener onCustomLongclickListener;

        public ArticleViewHolder(View itemView, OnCustomClickListerner onCustomClickListerner, OnCustomLongclickListener onCustomLongclickListener) {
            super(itemView);
            empImage = itemView.findViewById(R.id.img_article);
            empPublishedAt = itemView.findViewById(R.id.publish_date_article);
            empTitle = itemView.findViewById(R.id.title_article);
            empDescription = itemView.findViewById(R.id.description_article);
            this.onCustomClickListerner = onCustomClickListerner;
            this.onCustomLongclickListener = onCustomLongclickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            empImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCustomClickListerner.onShortClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            onCustomLongclickListener.onLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnCustomClickListerner{
        void onShortClick (int i, View v);
    }

    public interface OnCustomLongclickListener{
        void onLongClick (int i);
    }
}
