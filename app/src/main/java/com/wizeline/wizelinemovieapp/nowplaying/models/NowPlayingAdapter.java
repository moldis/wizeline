package com.wizeline.wizelinemovieapp.nowplaying.models;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.wizeline.wizelinemovieapp.R;
import com.wizeline.wizelinemovieapp.api.ApiClient;
import com.wizeline.wizelinemovieapp.api.beans.MovieBean;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {

    MovieBean[] data; // full data
    Context mContext;
    private ResizeOptions mResizeOptions;
    private final int AD_POS = 3;
    private static final int ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;

    public NowPlayingAdapter(MovieBean[] data, Context context) {

        this.data = data;
        this.mContext = context;
    }

    public void setData(MovieBean[] data) {
        this.data = data;
    }

    public void addAll(MovieBean[] input){
        ArrayList<MovieBean> arrayList = new ArrayList<>();
        Collections.addAll(arrayList,data);
        Collections.addAll(arrayList,input);
        MovieBean[] tmpData = new MovieBean[arrayList.size()];
        data = arrayList.toArray(tmpData);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % AD_POS == 0) {
            return AD_VIEW_TYPE;
        }
        return ITEM_VIEW_TYPE ;
    }

    @Override
    public NowPlayingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_movie_item, parent, false);

        NowPlayingAdapter.ViewHolder vh = new NowPlayingAdapter.ViewHolder(view, parent.getContext());
        mResizeOptions = new ResizeOptions(120, 120);

        return vh;
    }

    @Override
    public void onBindViewHolder(NowPlayingAdapter.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        MovieBean bean = data[position];
        if (bean.poster_path != null) {
            final ImageRequest imageRequest =
                    ImageRequestBuilder.newBuilderWithSource(Uri.parse(ApiClient.IMAGE_PATH + bean.poster_path))
                            .setResizeOptions(mResizeOptions)
                            .build();
            holder.movieImage.setImageRequest(imageRequest);
        }
        holder.txtTitle.setText(bean.title);
        holder.txtInfo.setText(bean.overview);
        holder.txtYear.setText(bean.release_date);
        holder.txtRating.setText("" + bean.vote_average);

        // TODO showing ads
       /* if(position!=0) {
            switch (viewType) {
                case AD_VIEW_TYPE:
                    ViewGroup adCardView = (ViewGroup) holder.itemView;
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    LayoutInflater inflater = (LayoutInflater) adCardView.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    ViewGroup bannerView = (ViewGroup) inflater.inflate(R.layout.layout_movie_baner, null);
                    adCardView.addView(bannerView);
            }
        }*/
    }

    public void clearAll(){
        data = new MovieBean[0];
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public MovieBean getItem(int position) {
        return data[position];
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieImage)
        SimpleDraweeView movieImage;
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtYear)
        TextView txtYear;
        @BindView(R.id.txtInfo)
        TextView txtInfo;
        @BindView(R.id.txtRating)
        TextView txtRating;

        public ViewHolder(View view, Context context) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
