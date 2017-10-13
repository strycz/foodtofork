package com.pstrycz.foodtofork.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by User on 2017-07-23.
 */

@Module
public class ContextModule {

    private Application application;

    public ContextModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplication() {
        return application;
    }
}
