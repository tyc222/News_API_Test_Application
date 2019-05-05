package com.example.news_api_test_application.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news_api_test_application.R;
import com.example.news_api_test_application.model.Article;
import com.example.news_api_test_application.model.ArticleList;
import com.example.news_api_test_application.network.GetNewsDataService;
import com.example.news_api_test_application.network.RetrofitInstance;
import com.example.news_api_test_application.viewer.ArticleAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArticleAdapter.OnCustomClickListerner, ArticleAdapter.OnCustomLongclickListener {

    FloatingActionButton webViewfloatingActionButton;
    private boolean doubleBackToExitPressedOnce = false;
    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    private String country;
    private String category;
    private String search;
    private String currentUrlHolder;
    private String currentTitleHolder;
    FloatingActionButton fabAu, fabTw, fabUK, fabNZ, fabUS, fabSwitch;
    // Boolean to determine if our FAB is pressed
    private boolean isFABOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Default title to default news country
       setTitle("Taiwanese News");

       // Set Up PullToRefresh
        setUpPullToRefresh();

       // Set Up TabLayout
        setUpTableLayout();

        // Initiate FAB
        createFAB();

        // Default Mews Fetch
        fetchNewsList();

    }

    // Method to fetch news
    private void fetchNewsList (){
        // Create handle for the RetrofitInstance interface
        GetNewsDataService service = RetrofitInstance.getRetrofitInstance().create(GetNewsDataService.class);

        // Default country
        if (country == null) {
            country = "tw";
        }

        // Default category
        if (category == null) {
            category = "";
        }

        // Default search
        if (search == null) {
            search = "";
        }

        // Call the method with parameter in the interface to get the news data
        Call<ArticleList> call = service.getArticleData(country, category, search,"30f23670bbb5441bbd9e77746df08fd4");

        // Log the URL called
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(Call<ArticleList> call, Response<ArticleList> response) {
                generateNewsList(response.body().getArticleList());
            }

            @Override
            public void onFailure(Call<ArticleList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong... Please check your Internet Connection and try again later", Toast.LENGTH_SHORT).show();

                Log.e("Error", t.getMessage());
            }
        });
    }

    // Method to generate a List of Articles using RecycerView with custom adapter
    private void generateNewsList(ArrayList<Article> empArticleList) {
        if (recyclerView == null){
            recyclerView = findViewById(R.id.recycler_view_article_list);
        }
        recyclerView.getRecycledViewPool().clear();
        adapter = new ArticleAdapter(empArticleList, this, this, this);
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
        fabSwitch = findViewById(R.id.fabSwitch);
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
        fabSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Please Enter Your Country Domain");
                alertDialog.setMessage("Eg. tw for Taiwan, hk for Hong Kong");
                final EditText editText = new EditText(MainActivity.this);
                alertDialog.setView(editText);
                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput = editText.getText().toString();
                        country = userInput;
                        setTitle(userInput + " News");
                        fetchNewsList();
                        dialog.dismiss();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.create().show();


                closeFABMenu();
            }
        });
        fabAu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "au";
                fetchNewsList();
                setTitle("Australian News");
                closeFABMenu();
            }
        });
        fabTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "tw";
                fetchNewsList();
                setTitle("Taiwanese News");
                closeFABMenu();
            }
        });
        fabUK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "gb";
                fetchNewsList();
                setTitle("British News");
                closeFABMenu();
            }
        });
        fabNZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "nz";
                fetchNewsList();
                setTitle("New Zealand News");
                closeFABMenu();
            }
        });
        fabUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = "us";
                fetchNewsList();
                setTitle("American News");
                closeFABMenu();
            }
        });
    }

    // To open FAB menu
    private void showFABMenu(){
        isFABOpen =true;
        fabSwitch.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        fabAu.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
        fabTw.animate().translationY(-getResources().getDimension(R.dimen.standard_180));
        fabUK.animate().translationY(-getResources().getDimension(R.dimen.standard_240));
        fabNZ.animate().translationY(-getResources().getDimension(R.dimen.standard_300));
        fabUS.animate().translationY(-getResources().getDimension(R.dimen.standard_360));
    }

    // To close FAB menu
    private void closeFABMenu(){
        isFABOpen=false;
        fabSwitch.animate().translationY(0);
        fabAu.animate().translationY(0);
        fabTw.animate().translationY(0);
        fabUK.animate().translationY(0);
        fabNZ.animate().translationY(0);
        fabUS.animate().translationY(0);
    }

    // To set up tablayout
    private void setUpTableLayout(){
        TabLayout categoryTabLayout = findViewById(R.id.tabLayout_tab);

        categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            onTabPressed(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // Method for tabs
    private void onTabPressed (int position) {
        switch (position) {
            case 0:
                category = "";
                fetchNewsList();
                break;
            case 1:
                category = "technology";
                fetchNewsList();
                break;
            case 2:
                category = "sports";
                fetchNewsList();
                break;
            case 3:
                category = "entertainment";
                fetchNewsList();
                break;
            case 4:
                category = "business";
                fetchNewsList();
                break;
            case 5:
                category = "science";
                fetchNewsList();
                break;
            case 6:
                category = "health";
                fetchNewsList();
                break;
    }}

    // Set Up Pull To Refresh function
    private void setUpPullToRefresh (){
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNewsList();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    // Set up option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search_menu:
                AlertDialog.Builder searchMenuBuilder = new AlertDialog.Builder(this);
                final EditText enterSearch = new EditText(this);
                searchMenuBuilder.setView(enterSearch);
                searchMenuBuilder.setTitle("Enter Your Search");
                searchMenuBuilder.setCancelable(true);
                searchMenuBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setTitle("Search Results");
                        search = enterSearch.getText().toString();
                        category = "";
                        fetchNewsList();
                    }
                });
                final AlertDialog dialog = searchMenuBuilder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogScaleAnimation;
                dialog.show();
                return true;
            case R.id.exit_menu:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onShortClick(int i) {
        String url = adapter.getArticleList().get(i).getUrl();
        if (!url.startsWith("http://") && !url.startsWith("https://"))
        url = "http://" + url;

        // Passing Url to WebView Fab
        currentUrlHolder = url;
        // Passing Title to WebView Fab
        currentTitleHolder = adapter.getArticleList().get(i).getTitle();
        // Passing Url to open the WebView
        showClickDialogAnimation(url);
    }

    @Override
    public void onLongClick(int i) {
        String title = adapter.getArticleList().get(i).getTitle();
        String content = adapter.getArticleList().get(i).getContent();
        String imageUrl = adapter.getArticleList().get(i).getUrlToImage();

        showLongClickDialogAnimation(title, content, imageUrl);
    }

    private void showClickDialogAnimation (String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_news_web_content, null);
        WebView webView = view.findViewById(R.id.web_view);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        }) ;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(url);
        builder.setView(view);
        final AlertDialog dialog = builder.create();



        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogScaleAnimation;

        dialog.show();
    }

    private void showLongClickDialogAnimation (String title, String newsContent, String imageUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_news_contents, null);
        TextView newsTitleView = view.findViewById(R.id.news_title);
        TextView newsContentView = view.findViewById(R.id.news_content);
        ImageView newsImageView = view.findViewById(R.id.news_imageView);
        builder.setView(view);
        newsTitleView.setText(title);
        newsContentView.setText(newsContent);
        if (imageUrl == null){
            newsImageView.setImageResource(R.mipmap.image_unavailable_picture);
        }
        else {
            Picasso.with(this).load(imageUrl).into(newsImageView);
        }


        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogScaleAnimation;

        dialog.show();
    }

    // WebView FAB method for sharing url
    public void web_view_floatingActionButton(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Article");
        intent.putExtra(Intent.EXTRA_TEXT, currentTitleHolder + "\n" + currentUrlHolder);
        startActivity(Intent.createChooser(intent, "Sharing Article"));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        search = "";
        fetchNewsList();
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        switch (country){
            case "tw":
                setTitle("Taiwanese News");
                break;
            case "us":
                setTitle("American News");
                break;
            case "nz":
                setTitle("New Zealand News");
                break;
            case "uk":
                setTitle("British News");
                break;
            case "au":
                setTitle("Australian News");
                break;
                default:
                    setTitle(country + " News");
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    // URL Intent
//    String url = adapter.getArticleList().get(i).getUrl();
//        if (!url.startsWith("http://") && !url.startsWith("https://"))
//    url = "http://" + url;
//    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//    startActivity(browserIntent);
}
