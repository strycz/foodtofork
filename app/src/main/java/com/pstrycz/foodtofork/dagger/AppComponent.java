package com.pstrycz.foodtofork.dagger;

import com.pstrycz.foodtofork.activities.DetailsActivity;
import com.pstrycz.foodtofork.activities.MainActivity;
import com.pstrycz.foodtofork.adapters.RecipeListAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by User on 2017-07-22.
 */

@Singleton
@Component(modules = {RetrofitModule.class, ContextModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(DetailsActivity activity);
    void inject(RecipeListAdapter adapter);
}
