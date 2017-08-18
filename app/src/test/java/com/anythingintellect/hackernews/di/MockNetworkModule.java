package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.network.HackerNewsAPIService;
import com.anythingintellect.hackernews.util.Constants;

import retrofit2.Retrofit;


public class MockNetworkModule extends NetworkModule {

    public MockNetworkModule() {
        super(Constants.BASE_URL);
    }


    @Override
    public HackerNewsAPIService provideHackerNewsAPIService(Retrofit retrofit) {
        return new MockHackerNewsAPI();
    }
}
