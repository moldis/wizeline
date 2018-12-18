package com.wizeline.wizelinemovieapp.nowplaying.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wizeline.wizelinemovieapp.R;
import com.wizeline.wizelinemovieapp.api.beans.MovieBean;
import com.wizeline.wizelinemovieapp.components.DividerItemDecoration;
import com.wizeline.wizelinemovieapp.components.RecyclerItemClickListener;
import com.wizeline.wizelinemovieapp.movieInfo.view.MovieActivity;
import com.wizeline.wizelinemovieapp.nowplaying.models.NowPlayingAdapter;
import com.wizeline.wizelinemovieapp.nowplaying.presenters.NowPlayingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements NowPlayingPresenter.View {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.noDataText)
    TextView noDataText;

    NowPlayingPresenter mPresenter;
    NowPlayingAdapter adapter;
    private Unbinder unbinder;
    LinearLayoutManager mLayoutManager;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(() -> {
            if(adapter!=null){
                adapter.clearAll();
            }
            mPresenter.fetchData(1);
        });

        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext());
        listView.addItemDecoration(itemDecoration);

        listView.setItemViewCacheSize(20);
        listView.setDrawingCacheEnabled(true);
        listView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        listView.setAdapter(adapter);
        listView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), listView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MovieBean bean = adapter.getItem(position);
                        Intent intent = MovieActivity.getIntent(bean,getActivity());
                        startActivityForResult(intent,50);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
        listView.addOnScrollListener(recyclerViewOnScrollListener);

        mPresenter = new NowPlayingPresenter(this);
        mPresenter.fetchData(1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!mPresenter.isLoading() && !mPresenter.isLastPage()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= mPresenter.getPageSize()) {
                    int curPage = mPresenter.getCurrentPage();
                    mPresenter.fetchData(curPage+1);
                }
            }
        }
    };

    @Override
    public void showProgress() {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(swipeRefresh!=null) {
                swipeRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void dismissProgress() {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(swipeRefresh!=null) {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDataFetched(MovieBean[] data) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (adapter == null) {
                adapter = new NowPlayingAdapter(data, getContext());
                listView.setAdapter(adapter);
            } else {
                adapter.addAll(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFetchError() {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(adapter==null || adapter.getItemCount()==0) {
                noDataText.setVisibility(View.VISIBLE);
            }
            Toast.makeText(getContext(),getString(R.string.goes_wrong_str),Toast.LENGTH_SHORT).show();
        });
    }
}
