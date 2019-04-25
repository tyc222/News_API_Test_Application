package com.example.news_api_test_application.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
    private String country;
    FloatingActionButton fabAu, fabTw, fabUK, fabNZ, fabUS;
    // Boolean to determine if our FAB is pressed
    private boolean isFABOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiate FAB
        createFAB();

        // Default Mews Fetch
        fetchNewsList();

    }

    // Method to fetch news
    private void fetchNewsList (){
        // Create handle for the RetrofitInstance interface
        GetNewsDataService service = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);

        if (country == null) {
            country = "au";
        }

        // Call the method with parameter in the interface to get the news data
        Call<ArticleList> call = service.getArticleData(country, "30f23670bbb5441bbd9e77746df08fd4");

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

    // Method to create a FAB menu with functionality
    private void createFAB () {
        // Create a FAB menu
        FloatingActionButton fab = findViewById(R.id.fab);
        fabAu = findViewById(R.id.fabAu);
        fabTw = findViewById(R.id.fabTw);
        fabUK = findViewById(R.id.fabUk);
        fabNZ = findViewById(R.id.fabNz);
        fabUS = findViewById(R.id.fabUs);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });
        fabAu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "au";
                fetchNewsList();
                closeFABMenu();
            }
        });
        fabTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "tw";
                fetchNewsList();
                closeFABMenu();
            }
        });
        fabUK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "gb";
                fetchNewsList();
                closeFABMenu();
            }
        });
        fabNZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "nz";
                fetchNewsList();
                closeFABMenu();
            }
        });
        fabUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "us";
                fetchNewsList();
                closeFABMenu();
            }
        });
    }

    // To open FAB menu
    private void showFABMenu(){
        isFABOpen =true;
        fabAu.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        fabTw.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
        fabUK.animate().translationY(-getResources().getDimension(R.dimen.standard_180));
        fabNZ.animate().translationY(-getResources().getDimension(R.dimen.standard_240));
        fabUS.animate().translationY(-getResources().getDimension(R.dimen.standard_300));
    }

    // To close FAB menu
    private void closeFABMenu(){
        isFABOpen=false;
        fabAu.animate().translationY(0);
        fabTw.animate().translationY(0);
        fabUK.animate().translationY(0);
        fabNZ.animate().translationY(0);
        fabUS.animate().translationY(0);
    }
}
