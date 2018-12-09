package com.wizeline.wizelinemovieapp.api.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieCredits implements Parcelable {
    @SerializedName("cast_id")
    public int cast_id;

    @SerializedName("character")
    public String character;

    @SerializedName("name")
    public String name;

    @SerializedName("order")
    public int order;

    @SerializedName("profile_path")
    public String profile_path;

    @SerializedName("job")
    public String job;

    protected MovieCredits(Parcel in) {
        cast_id = in.readInt();
        character = in.readString();
        name = in.readString();
        order = in.readInt();
        profile_path = in.readString();
        job = in.readString();
    }

    public static final Creator<MovieCredits> CREATOR = new Creator<MovieCredits>() {
        @Override
        public MovieCredits createFromParcel(Parcel in) {
            return new MovieCredits(in);
        }

        @Override
        public MovieCredits[] newArray(int size) {
            return new MovieCredits[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cast_id);
        dest.writeString(character);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeString(profile_path);
        dest.writeString(job);
    }
}
