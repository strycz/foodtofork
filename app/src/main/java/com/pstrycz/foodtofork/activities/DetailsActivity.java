package com.pstrycz.foodtofork.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pstrycz.foodtofork.FoodApplication;
import com.pstrycz.foodtofork.R;
import com.pstrycz.foodtofork.adapters.IngredientsListAdapter;
import com.pstrycz.foodtofork.rest.model.Recipe;
import com.pstrycz.foodtofork.rest.model.RecipeDetails;
import com.pstrycz.foodtofork.rest.model.RecipeDetailsContainer;
import com.pstrycz.foodtofork.rest.service.FoodApi;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by User on 2017-07-23.
 */

public class DetailsActivity extends AppCompatActivity {

    public static final String LOG_TAG = DetailsActivity.class.getName();

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.image_header)
    ImageView imageHeader;
    @BindView(R.id.ingredients_recycler_view)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.publisher_name)
    TextView publisherName;
    @BindView(R.id.social_rank)
    TextView socialRank;

    @Inject
    Retrofit retrofit;

    private FoodApi foodApi;
    private List<String> ingredients = new ArrayList<>();
    private String f2fUrl;
    private String sourceUrl;
    private CompositeDisposable compositeDisposable;

    @OnClick(R.id.viewInstructionsButton)
    public void viewInstructions() {
        startWebView(f2fUrl);
    }

    @OnClick(R.id.viewOriginalButton)
    public void viewOriginal() {
        startWebView(sourceUrl);
    }

    private void startWebView(String url) {
        Intent intent = new Intent(this, FoodWebView.class);
        intent.putExtra(FoodWebView.WEB_URL_KEY, url);

        if (url != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        compositeDisposable = new CompositeDisposable();

        ((FoodApplication) getApplication())
                .getAppComponent()
                .inject(this);

        ingredientsRecyclerView.setNestedScrollingEnabled(false);

        foodApi = retrofit.create(FoodApi.class);

        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra(Recipe.RECIPE_ID));

        compositeDisposable.add(foodApi.getRecipe(recipe.getRecipeId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResult(),
                        this::handleError)
        );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientsRecyclerView.setAdapter(new IngredientsListAdapter(ingredients));

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void handleError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());
    }

    @Override
    protected void onStop() {
        compositeDisposable.dispose();
        super.onStop();
    }

    @NonNull
    private Consumer<RecipeDetailsContainer> handleResult() {
        return recipeDetailsContainer -> {
            RecipeDetails body = recipeDetailsContainer.getRecipe();
            refreshUi(body);
        };
    }

    private void refreshUi(RecipeDetails recipeDetails) {
        collapsingToolbarLayout.setTitle(recipeDetails.getTitle());

        Picasso.with(DetailsActivity.this)
                .load(recipeDetails.getImageUrl())
                .fit()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imageHeader);

        ingredients.clear();
        ingredients.addAll(recipeDetails.getIngredients());
        ingredientsRecyclerView.getAdapter().notifyDataSetChanged();

        publisherName.setText(recipeDetails.getPublisher());

        String socialRankFormatted = getResources().getString(R.string.social_rank, recipeDetails.getSocialRank());
        socialRank.setText(socialRankFormatted);

        f2fUrl = recipeDetails.getF2fUrl();
        sourceUrl = recipeDetails.getSourceUrl();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
