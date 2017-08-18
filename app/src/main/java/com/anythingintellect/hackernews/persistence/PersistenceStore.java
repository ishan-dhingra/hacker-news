package com.anythingintellect.hackernews.persistence;

import com.anythingintellect.hackernews.model.Item;

import java.util.List;

import io.reactivex.Observable;


public interface PersistenceStore {

    Observable<Item> getItem(long id);
    void saveItem(Item item);
    Observable<List<Long>> getTopStories();
    void saveTopStories(List<Long> topStories);

}
