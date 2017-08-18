package com.anythingintellect.hackernews.repo;

import com.anythingintellect.hackernews.model.Item;

import java.util.List;

import io.reactivex.Observable;


public interface ItemRepository {

    Observable<Item> getItem(long id);
    Observable<List<Long>> getTopStories();
    // fetch means skip the cache
    Observable<List<Long>> fetchTopStories();

}
