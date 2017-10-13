package com.pstrycz.foodtofork.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by User on 2017-07-23.
 */

@Parcel
public class Recipe {

    public static final String RECIPE_ID = "recipe_id";

    @SerializedName("publisher")
    String publisher;
    @SerializedName("f2f_url")
    @Expose
    String f2fUrl;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("source_url")
    String sourceUrl;
    @SerializedName("recipe_id")
    @Expose
    String recipeId;
    @SerializedName("image_url")
    @Expose
    String imageUrl;
    @SerializedName("social_rank")
    Double socialRank;
    @SerializedName("publisher_url")
    String publisherUrl;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getF2fUrl() {
        return f2fUrl;
    }

    public void setF2fUrl(String f2fUrl) {
        this.f2fUrl = f2fUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getSocialRank() {
        return socialRank;
    }

    public void setSocialRank(Double socialRank) {
        this.socialRank = socialRank;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
