package com.pstrycz.foodtofork.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by User on 2017-07-23.
 */

public class RecipeDetailsContainer {

    @SerializedName("recipe")
    @Expose
    private RecipeDetails recipe;

    public RecipeDetails getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDetails recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}