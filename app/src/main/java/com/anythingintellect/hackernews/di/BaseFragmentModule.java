package com.anythingintellect.hackernews.di;

import android.content.Context;

import com.anythingintellect.hackernews.util.DefaultNavigator;
import com.anythingintellect.hackernews.util.Navigator;

import dagger.Module;
import dagger.Provides;


@Module
@PerFragment
public class BaseFragmentModule {

    private final Context context;

    public BaseFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @PerFragment
    Navigator providesNavigator() {
        return new DefaultNavigator(context);
    }

}
