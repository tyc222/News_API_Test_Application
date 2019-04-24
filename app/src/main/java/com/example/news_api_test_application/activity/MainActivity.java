package com.example.news_api_test_application.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.news_api_test_application.R;
import com.example.news_api_test_application.model.Article;
import com.example.news_api_test_application.model.ArticleList;
import com.example.news_api_test_application.network.GetNewsDataService;
import com.example.news_api_test_application.network.RetrofitInstance;
import com.example.news_api_test_application.viewer.ArticleAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArticleAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create handle for the RetrofitInstance interface
        GetNewsDataService service = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);

        // Call the method with parameter in the interface to get the news data
        Call<ArticleList> call = service.getArticleData("tw", "30f23670bbb5441bbd9e77746df08fd4");

        // Log the URL called
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(Call<ArticleList> call, Response<ArticleList> response) {
                generateNewsList(response.body().getArticleList());


            }

            @Override
            public void onFailure(Call<ArticleList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong... Please try again later", Toast.LENGTH_SHORT).show();

                Log.e("Error", t.getMessage());
            }
        });
    }

    // Method to generate a List of Articles using RecycerView with custom adatper
    private void generateNewsList(ArrayList<Article> empArticleList) {
        recyclerView = findViewById(R.id.recycler_view_article_list);
        adapter = new ArticleAdapter(empArticleList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
