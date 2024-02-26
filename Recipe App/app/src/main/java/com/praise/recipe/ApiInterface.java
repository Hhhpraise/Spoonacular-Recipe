package com.praise.recipe;

import com.praise.recipe.Model.All;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("complexSearch")
    Call<All> getAll(
            @Query("apiKey") String apiKey,
            @Query("query") String query

    );
    @GET("information")
    Call<All> getInfo(
            @Query("apiKey") String apiKey,
            @Query("id") String id
    );
}
