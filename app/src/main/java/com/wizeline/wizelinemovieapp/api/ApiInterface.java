package com.wizeline.wizelinemovieapp.api;

import com.wizeline.wizelinemovieapp.api.beans.CreditsResponse;
import com.wizeline.wizelinemovieapp.api.beans.MovieBean;
import com.wizeline.wizelinemovieapp.api.beans.StatusResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by artembogomaz on 5/31/2018.
 */

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("movie/top_rated")
    Call<StatusResponse> topRated(@Query("api_key") String api_key,@Query("page") int page,@Query("language") String language);

    @Headers("Content-Type: application/json")
    @GET("movie/now_playing")
    Call<StatusResponse> nowPlaying(@Query("api_key") String api_key, @Query("page") int page, @Query("language") String language);

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}")
    Call<MovieBean> movieInfo(@Path("movie_id") int movie_id,@Query("api_key") String api_key);

    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}/credits")
    Call<CreditsResponse> credits(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
}
