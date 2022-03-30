package com.lambton.moviebuddy.ui.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("now_playing")
    Call<ResponseBody> getMovies(@Query("api_key") String key);

    @GET("{movie_id}/reviews")
    Call<ResponseBody> getReviews(@Path ("movie_id") Integer id,@Query("api_key") String key);
}
