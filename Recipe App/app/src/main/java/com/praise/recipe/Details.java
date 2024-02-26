package com.praise.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Details extends AppCompatActivity {
    TextView tvTitle, tvSource, tvDate, tvDesc;
    WebView webView;
    ImageView i;
    ProgressBar progressBar;
    final String apiKey = "YOUR API KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvSource = findViewById(R.id.tvSource);
        tvDate = findViewById(R.id.tvDate);
        webView = findViewById(R.id.webview);
        i = findViewById(R.id.imageV);
        progressBar = findViewById(R.id.webloader);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        tvTitle.setText(title);
        String imageUrl = intent.getStringExtra("imageUrl");
        Picasso.get().load(imageUrl).into(i);
        String id = intent.getStringExtra("id");
        tvDesc.setText(id);
        fetchData(id,apiKey);
    }
    private void fetchData(String id , String apikey) {
        String url = " https://api.spoonacular.com/recipes/"+id+"/information?apiKey="+apikey;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(Details.this, "error", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resp = response.body().string();
                    Details.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(resp);
                                String sourceName = jsonObject.getString("sourceName");
                                    String sourceUrl = jsonObject.getString("sourceUrl");
                                    String likes = jsonObject.getString("aggregateLikes");
                                    String ready = jsonObject.getString("readyInMinutes");
                                    tvDesc.setText("ready in minutes : "+ready);
                                    tvSource.setText("Source name : "+sourceName);
                                    tvDate.setText("likes : " +likes);
                                webView.getSettings().setDomStorageEnabled(true);
                                webView.getSettings().setJavaScriptEnabled(true);
                                webView.getSettings().setLoadsImagesAutomatically(true);
                                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                                webView.setWebViewClient(new WebViewClient());
                                webView.loadUrl(sourceUrl);
                                if (webView.isShown()){
                                    progressBar.setVisibility(View.INVISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Details.this, "error "+ e, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }
}