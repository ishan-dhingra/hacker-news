package com.anythingintellect.hackernews;

import com.anythingintellect.hackernews.di.AppComponent;
import com.anythingintellect.hackernews.di.BaseModule;
import com.anythingintellect.hackernews.di.DaggerAppComponent;
import com.anythingintellect.hackernews.di.MockNetworkModule;
import com.anythingintellect.hackernews.di.PersistenceModule;


public class TestHackerNewsApp extends HackerNewsApp {

    AppComponent appComponent;
    @Override
    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .baseModule(new BaseModule())
                    .networkModule(new MockNetworkModule())
                    .persistenceModule(new PersistenceModule()).build();
        }
        return appComponent;
    }
}
