package com.wizeline.wizelinemovieapp.movieInfo.presenter;

import com.wizeline.wizelinemovieapp.AppObj;
import com.wizeline.wizelinemovieapp.R;
import com.wizeline.wizelinemovieapp.api.ApiClient;
import com.wizeline.wizelinemovieapp.api.ApiInterface;
import com.wizeline.wizelinemovieapp.api.beans.CreditsResponse;
import com.wizeline.wizelinemovieapp.api.beans.MovieBean;
import com.wizeline.wizelinemovieapp.api.beans.MovieCredits;
import com.wizeline.wizelinemovieapp.api.beans.StatusResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivityPresenter {

    private CreditsResponse mCreditsResponse;
    private MovieBean mMovie;
    View mView;

    public MovieActivityPresenter(View view){
        this.mView = view;
    }

    public void fetchData(int movieId){
        ApiInterface apiInterface = ApiClient.getClient();
        mView.showProgress();
        apiInterface.movieInfo(movieId,ApiClient.API_KEY).enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                if(mView!=null) {
                    mView.dismissProgress();
                    if (!response.isSuccessful()) {
                        mView.onFetchError();
                        return;
                    }
                    mMovie = response.body();
                    mView.onDataFetched(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                if(mView!=null) {
                    mView.dismissProgress();
                    mView.onFetchError();
                }
            }
        });
    }

    public void fetchCredits(int movieId){
        ApiInterface apiInterface = ApiClient.getClient();
        mView.showProgress();
        apiInterface.credits(movieId,ApiClient.API_KEY).enqueue(new Callback<CreditsResponse>() {
            @Override
            public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                if(mView!=null) {
                    mView.dismissProgress();
                    if (!response.isSuccessful()) {
                        mView.onFetchError();
                        return;
                    }

                    mCreditsResponse = response.body();
                    MovieCredits[] credits = mCreditsResponse.cast;
                    // TODO sorting
                    /*Collections.sort(credits, new Comparator<MovieCredits>() {
                        @Override
                        public int compare(MovieCredits o1, MovieCredits o2) {
                            return (o1.order > o2.order);
                        }
                    });*/

                    ArrayList<String> directors = new ArrayList<>();
                    for(MovieCredits movieCredits:mCreditsResponse.crew){
                        directors.add(movieCredits.name);
                    }

                    int moreText = 0;
                    int counter = 0;
                    StringBuilder stringBuilder = new StringBuilder();
                    for(String str:directors){
                        if(counter>2){
                            moreText++;
                        }else{
                            stringBuilder.append(str);
                            stringBuilder.append(" &");
                        }
                        counter++;
                    }

                    String result = "";
                    if(moreText>0){
                        result = stringBuilder.append(" " + moreText + " more ").toString();
                        result = AppObj.getContext().getResources().getString(R.string.directors_str) + result;
                    }else{
                        result = AppObj.getContext().getResources().getString(R.string.director_str) + stringBuilder.toString();
                    }

                    mView.onMovieCastFetched(credits,result);
                }
            }

            @Override
            public void onFailure(Call<CreditsResponse> call, Throwable t) {
                if(mView!=null) {
                    mView.dismissProgress();
                    mView.onFetchError();
                }
            }
        });
    }

    public CreditsResponse getCreditsResponse() {
        return mCreditsResponse;
    }

    public MovieBean getMovie() {
        return mMovie;
    }

    public interface View{
        public void showProgress();
        public void dismissProgress();
        public void onDataFetched(MovieBean data);
        public void onMovieCastFetched(MovieCredits[] movieCredits,String producers);
        public void onFetchError();
    }
}
