package com.wizeline.wizelinemovieapp.movieInfo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.wizeline.wizelinemovieapp.R;
import com.wizeline.wizelinemovieapp.api.ApiClient;
import com.wizeline.wizelinemovieapp.api.beans.MovieBean;
import com.wizeline.wizelinemovieapp.api.beans.MovieCredits;
import com.wizeline.wizelinemovieapp.components.AbstractAppCompatActivity;
import com.wizeline.wizelinemovieapp.movieInfo.presenter.MovieActivityPresenter;
import com.wizeline.wizelinemovieapp.seeAllCasts.view.CastCrewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieActivity extends AbstractAppCompatActivity implements MovieActivityPresenter.View {

    @BindView(R.id.movieImage)
    SimpleDraweeView movieImage;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtRating)
    TextView txtRating;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.txtGenre)
    TextView txtGenre;
    @BindView(R.id.txtInfo)
    TextView txtInfo;
    @BindView(R.id.progress)
    ProgressBar progress;

    // cast & crew
    @BindView(R.id.directors)
    View directors;
    @BindView(R.id.seeAlltxt)
    TextView seeAlltxt;
    @BindView(R.id.txtDirectors)
    TextView txtDirectors;

    MovieActivityPresenter mPresenter;

    @Override
    public void onRetryInternet() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        MovieBean bean = getIntent().getParcelableExtra(MovieBean.ID_OBJECT_PARCELABLE);
        onDataFetched(bean); // prefetching view

        mPresenter = new MovieActivityPresenter(this);
        mPresenter.fetchData(bean.id);
        mPresenter.fetchCredits(bean.id);
    }

    public static Intent getIntent(MovieBean bean, Activity parent){
        Intent intent = new Intent(parent,MovieActivity.class);
        intent.putExtra(MovieBean.ID_OBJECT_PARCELABLE,bean);
        return intent;
    }

    @Override
    public void showProgress() {
        new Handler(Looper.getMainLooper()).post(() -> progress.setVisibility(View.VISIBLE));
    }

    @Override
    public void dismissProgress() {
        new Handler(Looper.getMainLooper()).post(() -> progress.setVisibility(View.GONE));
    }

    @Override
    public void onDataFetched(MovieBean data) {
        new Handler(Looper.getMainLooper()).post(() -> {
            final ImageRequest imageRequest =
                    ImageRequestBuilder.newBuilderWithSource(Uri.parse(ApiClient.IMAGE_PATH + data.poster_path))
                            .build();
            movieImage.setImageRequest(imageRequest);
            txtTitle.setText(data.title + " ("+data.release_date.substring(0,4) + " year)" );
            setTitle(data.title);

            txtRating.setText(data.vote_average + "/10");

            StringBuilder builder = new StringBuilder();
            if(data.genres!=null) {
                for (int i = 0; i < data.genres.length; i++) {
                    builder.append(data.genres[i].name);
                    if (i != data.genres.length) {
                        builder.append(", ");
                    }
                }
            }

            txtGenre.setText(builder.toString());
            txtInfo.setText(data.overview);

            String time = String.valueOf(data.runtime);
            String h = time.substring(0,1);
            String m = time.substring(1,time.length());
            time = h + "h : "+m + "mm";
            txtTime.setText(time);
        });
    }

    @Override
    public void onMovieCastFetched(MovieCredits[] movieCredits, String value) {

        // remove all childs first
        ViewGroup dirs = ((ViewGroup)directors);
        dirs.removeAllViews();

        int counter = 0;
        for(MovieCredits credits:movieCredits){

            LayoutInflater inflater =  (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_cast_item, null);

            SimpleDraweeView profileImage = view.findViewById(R.id.profileImage);
            TextView txtTitle = view.findViewById(R.id.txtTitle);
            TextView txtRole = view.findViewById(R.id.txtRole);
            profileImage.setImageURI(ApiClient.IMAGE_PATH + credits.profile_path);
            txtTitle.setText(credits.name);
            txtRole.setText(credits.character);
            ((ViewGroup)directors).addView(view);

            // max 4 items to show
            counter++;
            if(counter>3){
                break;
            }
        }
        txtDirectors.setText(value);
    }

    @Override
    public void onFetchError() {

    }

    @OnClick(R.id.seeAlltxt)
    public void onClick(View view){
        if(mPresenter.getCreditsResponse()!=null){
            Intent intent = CastCrewActivity.getIntent(mPresenter.getCreditsResponse(),mPresenter.getMovie(),this);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
