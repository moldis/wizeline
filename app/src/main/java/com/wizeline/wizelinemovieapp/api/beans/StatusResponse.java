package com.wizeline.wizelinemovieapp.api.beans;

import com.google.gson.annotations.SerializedName;

public class StatusResponse {
    @SerializedName("page")
    public int page;

    @SerializedName("total_results")
    public int total_results;

    @SerializedName("total_pages")
    public int total_pages;

    @SerializedName("results")
    public MovieBean[] results;
}
