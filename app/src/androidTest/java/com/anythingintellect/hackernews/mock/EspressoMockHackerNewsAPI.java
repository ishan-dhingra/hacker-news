package com.anythingintellect.hackernews.mock;

import com.anythingintellect.hackernews.model.Item;
import com.anythingintellect.hackernews.network.HackerNewsAPIService;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Path;


public class EspressoMockHackerNewsAPI implements HackerNewsAPIService {
    @Override
    public Observable<List<Long>> getTopStories() {
        return Observable.just(MockData.getTopStories());
    }

    @Override
    public Observable<Item> getKid(@Path("id") long id) {
        return Observable.just(MockData.getMockItem());
    }
}
