package com.wizeline.wizelinemovieapp.api.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CreditsResponse implements Parcelable {

    public static final String ID_OBJECT_PARCELABLE = "ID_OBJECT_CREDITS_PARCELABLE";

    @SerializedName("id")
    public int id;

    @SerializedName("cast")
    public MovieCredits[] cast;

    @SerializedName("crew")
    public MovieCredits[] crew;

    protected CreditsResponse(Parcel in) {
        id = in.readInt();
        cast = in.createTypedArray(MovieCredits.CREATOR);
        crew = in.createTypedArray(MovieCredits.CREATOR);
    }

    public static final Creator<CreditsResponse> CREATOR = new Creator<CreditsResponse>() {
        @Override
        public CreditsResponse createFromParcel(Parcel in) {
            return new CreditsResponse(in);
        }

        @Override
        public CreditsResponse[] newArray(int size) {
            return new CreditsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedArray(cast, flags);
        dest.writeTypedArray(crew, flags);
    }
}
