package com.wizeline.wizelinemovieapp.seeAllCasts.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wizeline.wizelinemovieapp.R;
import com.wizeline.wizelinemovieapp.api.beans.CreditsResponse;
import com.wizeline.wizelinemovieapp.api.beans.MovieBean;
import com.wizeline.wizelinemovieapp.api.beans.MovieCredits;
import com.wizeline.wizelinemovieapp.components.AbstractAppCompatActivity;
import com.wizeline.wizelinemovieapp.components.DividerItemDecoration;
import com.wizeline.wizelinemovieapp.components.RecyclerItemClickListener;
import com.wizeline.wizelinemovieapp.movieInfo.view.MovieActivity;
import com.wizeline.wizelinemovieapp.seeAllCasts.models.AllCastCrewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastCrewActivity extends AbstractAppCompatActivity {

    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtAmount)
    TextView txtAmount;

    AllCastCrewAdapter adapter;

    public static Intent getIntent(CreditsResponse creditsResponse,MovieBean movieBean, Activity parent){
        Intent intent = new Intent(parent,CastCrewActivity.class);
        intent.putExtra(CreditsResponse.ID_OBJECT_PARCELABLE,creditsResponse);
        intent.putExtra(MovieBean.ID_OBJECT_PARCELABLE,movieBean);
        return intent;
    }

    @Override
    public void onRetryInternet() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_crew);
        ButterKnife.bind(this);

        CreditsResponse creditsResponse = getIntent().getParcelableExtra(CreditsResponse.ID_OBJECT_PARCELABLE);
        if(creditsResponse==null){
            finish();
        }
        MovieBean movieBean = getIntent().getParcelableExtra(MovieBean.ID_OBJECT_PARCELABLE);
        if(movieBean==null){
            finish();
        }
        txtAmount.setText(creditsResponse.cast.length + creditsResponse.cast.length + " " + getResources().getString(R.string.people_str));
        txtName.setText(movieBean.title);
        setTitle(getResources().getString(R.string.cast_crew_str));

        // init list view
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this);
        listView.addItemDecoration(itemDecoration);

        listView.setItemViewCacheSize(20);
        listView.setDrawingCacheEnabled(true);
        listView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        List<MovieCredits> both = new ArrayList<>();
        Collections.addAll(both,creditsResponse.cast);
        Collections.addAll(both,creditsResponse.crew);
        MovieCredits[] full = new MovieCredits[both.size()];

        adapter = new AllCastCrewAdapter(both.toArray(full),this);
        listView.setAdapter(adapter);
    }
}
