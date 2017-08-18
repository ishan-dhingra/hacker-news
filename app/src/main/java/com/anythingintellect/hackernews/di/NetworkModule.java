package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.network.HackerNewsAPIService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetworkModule {

    private final String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory providesRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    JacksonConverterFactory providesJacksonConverterFactory() {
        return JacksonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                              JacksonConverterFactory jacksonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(jacksonConverterFactory)
                .build();

    }

    @Provides
    @Singleton
    protected HackerNewsAPIService provideHackerNewsAPIService(Retrofit retrofit) {
        return retrofit.create(HackerNewsAPIService.class);
    }


}
