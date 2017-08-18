package com.anythingintellect.hackernews;

import android.app.Application;

import com.anythingintellect.hackernews.di.AppComponent;
import com.anythingintellect.hackernews.di.BaseModule;
import com.anythingintellect.hackernews.di.DaggerAppComponent;
import com.anythingintellect.hackernews.di.NetworkModule;
import com.anythingintellect.hackernews.di.PersistenceModule;
import com.anythingintellect.hackernews.util.Constants;


public class HackerNewsApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    protected void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .baseModule(new BaseModule())
                .networkModule(new NetworkModule(Constants.BASE_URL))
                .persistenceModule(new PersistenceModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

}
