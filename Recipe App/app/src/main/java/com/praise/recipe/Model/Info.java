package com.praise.recipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sourceName")
    @Expose
    private String sourceName;
    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;
    @SerializedName("aggregateLikes")
    @Expose
    private String likes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
