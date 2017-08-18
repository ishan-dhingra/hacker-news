package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.model.Item;
import com.anythingintellect.hackernews.network.HackerNewsAPIService;
import com.anythingintellect.hackernews.util.MockData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Path;

public class MockHackerNewsAPI implements HackerNewsAPIService {
    @Override
    public Observable<List<Long>> getTopStories() {
        return Observable.just(MockData.getTopStories());
    }

    @Override
    public Observable<Item> getKid(@Path("id") long id) {
        return Observable.just(MockData.getMockItem());
    }
}
