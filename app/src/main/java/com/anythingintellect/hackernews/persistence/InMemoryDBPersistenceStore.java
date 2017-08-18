package com.anythingintellect.hackernews.persistence;

import com.anythingintellect.hackernews.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public class InMemoryDBPersistenceStore implements PersistenceStore {

    private final Map<Long,Item> newsCache;
    private List<Long> topStoriesCache;

    public InMemoryDBPersistenceStore() {
        this.newsCache = new HashMap<>();
    }


    @Override
    public Observable<Item> getItem(long id) {
        Item item = newsCache.get(id);
        if (item == null) {
            return null;
        }
        return Observable.just(item);
    }

    @Override
    public void saveItem(Item item) {
        newsCache.put(item.getId(), item);
    }

    @Override
    public Observable<List<Long>> getTopStories() {
        if (topStoriesCache == null) {
            return null;
        }
        return Observable.just(topStoriesCache);
    }

    @Override
    public void saveTopStories(List<Long> topStoriesCache) {
        this.topStoriesCache = topStoriesCache;
    }
}
