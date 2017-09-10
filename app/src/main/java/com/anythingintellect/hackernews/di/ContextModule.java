package com.anythingintellect.hackernews.di;

import android.content.Context;

import com.anythingintellect.hackernews.util.DefaultNavigator;
import com.anythingintellect.hackernews.util.Navigator;

import dagger.Module;
import dagger.Provides;


@Module
@PerContext
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @PerContext
    Navigator providesNavigator() {
        return new DefaultNavigator(context);
    }

}
