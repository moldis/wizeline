package com.wizeline.wizelinemovieapp.nowplaying.presenters;

import com.wizeline.wizelinemovieapp.api.ApiClient;
import com.wizeline.wizelinemovieapp.api.ApiInterface;
import com.wizeline.wizelinemovieapp.api.beans.MovieBean;
import com.wizeline.wizelinemovieapp.api.beans.StatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingPresenter {

    // pagination
    private int mCurrentPage = 0;
    private int PAGE_SIZE = 15;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    View mView;
    public NowPlayingPresenter(View view){
        this.mView = view;
    }

    public void fetchData(int page){
        mView.showProgress();
        mCurrentPage = page;
        isLoading = true;

        ApiInterface apiInterface = ApiClient.getClient();
        apiInterface.nowPlaying(ApiClient.API_KEY,page,"en-US").enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                isLoading = false;
                if(mView!=null) {
                    mView.dismissProgress();
                    if (!response.isSuccessful()) {
                        mView.onFetchError();
                        return;
                    }
                    mView.onDataFetched(response.body().results);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                isLoading = false;
                if(mView!=null) {
                    mView.dismissProgress();
                    mView.onFetchError();
                }
            }
        });
    }

    public boolean isLoading() {
        return isLoading;
    }

    public int getPageSize() {
        return PAGE_SIZE;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void fetchDataTopRated(int page){
        ApiInterface apiInterface = ApiClient.getClient();
        mCurrentPage = page;
        isLoading = true;

        mView.showProgress();
        apiInterface.topRated(ApiClient.API_KEY,page,"en-US").enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                isLoading = false;
                if(mView!=null) {
                    mView.dismissProgress();
                    if (!response.isSuccessful()) {
                        mView.onFetchError();
                        return;
                    }
                    mView.onDataFetched(response.body().results);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                isLoading = false;
                if(mView!=null) {
                    mView.dismissProgress();
                    mView.onFetchError();
                }
            }
        });
    }

    public interface View{
        public void showProgress();
        public void dismissProgress();
        public void onDataFetched(MovieBean[] data);
        public void onFetchError();
    }
}
