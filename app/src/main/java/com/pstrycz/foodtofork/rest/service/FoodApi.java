package com.pstrycz.foodtofork.rest.service;

import com.pstrycz.foodtofork.rest.model.RecipeDetailsContainer;
import com.pstrycz.foodtofork.rest.model.SearchResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 2017-07-22.
 */

public interface FoodApi {

    @GET("search")
    Observable<SearchResult> searchForRecipe(@Query("q") String searchQuery);

    @GET("get")
    Observable<RecipeDetailsContainer> getRecipe(@Query("rId") String recipeId);
}
