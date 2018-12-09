package com.wizeline.wizelinemovieapp.seeAllCasts.models;

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
import com.wizeline.wizelinemovieapp.api.beans.MovieCredits;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllCastCrewAdapter extends RecyclerView.Adapter<AllCastCrewAdapter.ViewHolder> {

    MovieCredits[] data; // full data
    Context mContext;
    private ResizeOptions mResizeOptions;

    public AllCastCrewAdapter(MovieCredits[] data, Context context) {

        this.data = data;
        this.mContext = context;
    }

    public void setData(MovieCredits[] data) {
        this.data = data;
    }

    @Override
    public AllCastCrewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cast_crew_item, parent, false);

        AllCastCrewAdapter.ViewHolder vh = new AllCastCrewAdapter.ViewHolder(view, parent.getContext());
        mResizeOptions = new ResizeOptions(120, 120);

        return vh;
    }

    @Override
    public void onBindViewHolder(AllCastCrewAdapter.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        MovieCredits bean = data[position];
        if (bean.profile_path != null) {
            final ImageRequest imageRequest =
                    ImageRequestBuilder.newBuilderWithSource(Uri.parse(ApiClient.IMAGE_PATH + bean.profile_path))
                            .setResizeOptions(mResizeOptions)
                            .build();
            holder.profileImage.setImageRequest(imageRequest);
        }
        holder.txtName.setText(bean.name);
        holder.txtRole.setText(bean.character==null?bean.job:bean.character);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public MovieCredits getItem(int position) {
        return data[position];
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profileImage)
        SimpleDraweeView profileImage;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtRole)
        TextView txtRole;

        public ViewHolder(View view, Context context) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
