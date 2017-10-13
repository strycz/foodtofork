package com.pstrycz.foodtofork;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.pstrycz.foodtofork.dagger.AppComponent;
import com.pstrycz.foodtofork.dagger.ContextModule;
import com.pstrycz.foodtofork.dagger.DaggerAppComponent;
import com.pstrycz.foodtofork.dagger.RetrofitModule;


/**
 * Created by User on 2017-07-22.
 */

public class FoodApplication extends MultiDexApplication {
    private AppComponent appComponent;
    private String endPoint = "http://food2fork.com/api/";

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger(this);
    }

    protected void initDagger(FoodApplication application) {
        appComponent = DaggerAppComponent.builder()
                .retrofitModule(new RetrofitModule(endPoint))
                .contextModule(new ContextModule(application))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
