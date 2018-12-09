package com.wizeline.wizelinemovieapp.api.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieBean implements Parcelable {

    public static final String ID_PARAM_PARCELABLE = "ID_PARCELABLE";
    public static final String ID_OBJECT_PARCELABLE = "ID_OBJECT_MOVIE_BEAN_PARCELABLE";

    @SerializedName("vote_count")
    public int vote_count;

    @SerializedName("id")
    public int id;

    @SerializedName("video")
    public boolean video;

    @SerializedName("vote_average")
    public double vote_average;

    @SerializedName("title")
    public String title;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("poster_path")
    public String poster_path;

    @SerializedName("original_language")
    public String original_language;

    @SerializedName("original_title")
    public String original_title;

    @SerializedName("backdrop_path")
    public String backdrop_path;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String release_date;

    @SerializedName("genres")
    public Genres[] genres;

    @SerializedName("runtime")
    public int runtime;

    protected MovieBean(Parcel in) {
        vote_count = in.readInt();
        id = in.readInt();
        video = in.readByte() != 0;
        vote_average = in.readDouble();
        title = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        runtime = in.readInt();
    }

    public static final Creator<MovieBean> CREATOR = new Creator<MovieBean>() {
        @Override
        public MovieBean createFromParcel(Parcel in) {
            return new MovieBean(in);
        }

        @Override
        public MovieBean[] newArray(int size) {
            return new MovieBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(vote_count);
        dest.writeInt(id);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(vote_average);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeInt(runtime);
    }
}
