package com.anythingintellect.hackernews.mock;

import com.anythingintellect.hackernews.di.NetworkModule;
import com.anythingintellect.hackernews.network.HackerNewsAPIService;
import com.anythingintellect.hackernews.util.Constants;

import retrofit2.Retrofit;


public class EspressoMockNetworkModule extends NetworkModule {

    public EspressoMockNetworkModule() {
        super(Constants.BASE_URL);
    }

    @Override
    protected HackerNewsAPIService provideHackerNewsAPIService(Retrofit retrofit) {
        return new EspressoMockHackerNewsAPI();
    }
}