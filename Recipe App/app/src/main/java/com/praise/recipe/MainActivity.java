package com.praise.recipe;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.praise.recipe.Model.All;
import com.praise.recipe.Model.Results;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    final String apiKey = "Your API Key";
    RecyclerView recyclerView;
    com.praise.recipe.adapter adapter;
    List<Results> foods = new ArrayList<>();
    EditText editText;
    Button button;
    SwipeRefreshLayout swipeRefreshLayout;
    LottieAnimationView lottieAnimationView;

    //example link on fecthing requests...
    String searchurl = "https://api.spoonacular.com/recipes/complexSearch?apiKey=44479338b9954d4ca340b984102073ae&query=pasta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edtQuery);
        button = findViewById(R.id.search);
        swipeRefreshLayout = findViewById(R.id.swipe);
        recyclerView = findViewById(R.id.recyclerView);
        lottieAnimationView = findViewById(R.id.lottie);
        lottieAnimationView.setVisibility(View.INVISIBLE);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String query = "";
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(apiKey, "");
            }
        });
        retrieveJson(apiKey, query);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson(apiKey, editText.getText().toString());
                        }
                    });
                    retrieveJson(apiKey, editText.getText().toString());
                } else {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson(apiKey, "");
                        }
                    });
                    retrieveJson(apiKey, "");
                }
            }
        });

    }

    public void retrieveJson(String apikey, String query) {
        swipeRefreshLayout.setRefreshing(true);
        Call<All> call;
        call = ApiClient.getInstance().getApi().getAll(apiKey, query);
        call.enqueue(new Callback<All>() {
            @Override
            public void onResponse(Call<All> call, Response<All> response) {
                if (response.isSuccessful() && response.body().getResultsList() != null) {
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    foods.clear();
                    swipeRefreshLayout.setRefreshing(false);
                    foods = response.body().getResultsList();
                    adapter = new adapter(MainActivity.this, foods);
                    recyclerView.setAdapter(adapter);

                }
                if (response.isSuccessful() && response.body().getResultsList().isEmpty()) {
                    Toast.makeText(MainActivity.this, "no resource in our database", Toast.LENGTH_SHORT).show();
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                }
                if(!response.isSuccessful()){
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                    Toast.makeText(MainActivity.this, "check your connection!!!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<All> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}