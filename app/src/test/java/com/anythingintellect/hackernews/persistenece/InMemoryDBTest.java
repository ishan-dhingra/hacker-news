package com.anythingintellect.hackernews.persistenece;

import com.anythingintellect.hackernews.BaseTest;
import com.anythingintellect.hackernews.model.Item;
import com.anythingintellect.hackernews.persistence.InMemoryDBPersistenceStore;
import com.anythingintellect.hackernews.persistence.PersistenceStore;
import com.anythingintellect.hackernews.util.MockData;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.Observable;


public class InMemoryDBTest extends BaseTest {

    private PersistenceStore inMemoryStore;

    @Before
    public void setup() {
        inMemoryStore = new InMemoryDBPersistenceStore();
    }

    @Test
    public void shouldSaveItem() {
        Item mockItem = MockData.getMockItem();

        inMemoryStore.saveItem(mockItem);
        Observable<Item> itemObservable = inMemoryStore.getItem(mockItem.getId());

        itemObservable.test().assertNoErrors();
        itemObservable.test().assertResult(mockItem);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSaveTopStories() {
        List<Long> topStories = MockData.getTopStories();

        inMemoryStore.saveTopStories(topStories);
        Observable<List<Long>> topStoriesObs = inMemoryStore.getTopStories();

        topStoriesObs.test().assertNoErrors();
        topStoriesObs.test().assertResult(topStories);
    }




}
