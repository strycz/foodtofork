package com.pstrycz.foodtofork.activities;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.pstrycz.foodtofork.FoodApplication;
import com.pstrycz.foodtofork.R;
import com.pstrycz.foodtofork.adapters.RecipeListAdapter;
import com.pstrycz.foodtofork.rest.model.Recipe;
import com.pstrycz.foodtofork.rest.model.SearchResult;
import com.pstrycz.foodtofork.rest.service.FoodApi;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.FoodAdapterClickListener {

    public static final String LOG_TAG = MainActivity.class.getName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipeRecyclerView;

    @Inject
    Retrofit retrofit;

    private List<Recipe> recipeData = new ArrayList<>();
    private FoodApi foodApi;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((FoodApplication) getApplication())
                .getAppComponent()
                .inject(this);

        compositeDisposable = new CompositeDisposable();

        foodApi = retrofit.create(FoodApi.class);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Paweł Stryczniewicz");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recipeRecyclerView.setLayoutManager(linearLayoutManager);
        recipeRecyclerView.setAdapter(new RecipeListAdapter(recipeData));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            compositeDisposable.add(foodApi.searchForRecipe(query)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(handleResult(),
                            this::handleError));
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
    private Consumer<SearchResult> handleResult() {
        return searchResult -> {
            recipeData.clear();
            recipeData.addAll(searchResult.getRecipes());
            recipeRecyclerView.getAdapter().notifyDataSetChanged();
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        ComponentName componentName = new ComponentName(getApplicationContext(), MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        return true;
    }

    @Override
    public void onSearchResultItemClicked(Recipe recipe) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Recipe.RECIPE_ID, Parcels.wrap(recipe));
        startActivity(intent);
    }
}
