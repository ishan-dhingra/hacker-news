package com.anythingintellect.hackernews.repo;

import com.anythingintellect.hackernews.BaseTest;
import com.anythingintellect.hackernews.model.Item;
import com.anythingintellect.hackernews.network.HackerNewsAPIService;
import com.anythingintellect.hackernews.persistence.PersistenceStore;
import com.anythingintellect.hackernews.util.MockData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemRepositoryTest extends BaseTest {

    @Mock
    HackerNewsAPIService apiService;

    @Mock
    PersistenceStore persistenceStore;

    ItemRepository itemRepository;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        itemRepository = new ItemRepositoryImpl(apiService, persistenceStore);
    }


    @After
    public void tearDown() {
        RxAndroidPlugins.reset();
    }

    // Should return fresh item for the first time
    @Test
    public void shouldCallAndReturnItemFromApiForFirstCall() {
        Item item = MockData.getMockItem();
        long id = item.getId();
        when(apiService.getKid(id)).thenReturn(Observable.just(item));
        when(persistenceStore.getItem(id)).thenReturn(null);

        Observable<Item> itemObs = itemRepository.getItem(id);

        verify(persistenceStore, only()).getItem(id);
        verify(apiService, only()).getKid(id);

        assertEquals(itemObs.blockingFirst(), item);


    }

    // Should get item from cache for the second time
    @Test
    public void shouldCallAndReturnItemFromPersistenceStoreForSecondCall() {
        // Verify first call
        Item item = MockData.getMockItem();
        long id = item.getId();
        when(apiService.getKid(id)).thenReturn(Observable.just(item));
        when(persistenceStore.getItem(id)).thenReturn(null);

        Observable<Item> firstTime = itemRepository.getItem(id);
        // TODO: Why assertResult not working here?
        assertEquals(firstTime.blockingFirst(), item);

        verify(persistenceStore).getItem(id);
        verify(apiService).getKid(id);
        verify(persistenceStore).saveItem(item);

        // Verify second call
        reset(persistenceStore);
        reset(apiService);

        when(persistenceStore.getItem(id)).thenReturn(Observable.just(item));

        Observable<Item> secondTime = itemRepository.getItem(id);

        secondTime.test().assertNoErrors().assertResult(item);

        verify(persistenceStore, only()).getItem(id);
        verify(apiService, never()).getKid(id);


    }


    // Should return fresh TopStories from API for the first time
    @SuppressWarnings("unchecked")
    @Test
    public void shouldCallAndReturnTopStoriesFromApiForFirstCall() {
        List<Long> topStories = MockData.getTopStories();
        when(apiService.getTopStories()).thenReturn(Observable.just(topStories));
        when(persistenceStore.getTopStories()).thenReturn(null);

        Observable<List<Long>> topStoriesObs = itemRepository.getTopStories();

        assertEquals(topStoriesObs.blockingFirst(), topStories);

        verify(persistenceStore).getTopStories();
        verify(apiService).getTopStories();
        verify(persistenceStore).saveTopStories(topStories);

    }


    // Should get TopStories from cache for the second time
    @SuppressWarnings("unchecked")
    @Test
    public void shouldCallAndReturnTopStoriesFromPersistenceStoreForSecondCall() {
        // First call
        List<Long> topStories = MockData.getTopStories();
        when(apiService.getTopStories()).thenReturn(Observable.just(topStories));
        when(persistenceStore.getTopStories()).thenReturn(null);

        Observable<List<Long>> firstCall = itemRepository.getTopStories();

        assertEquals(firstCall.blockingFirst(), topStories);

        verify(persistenceStore).getTopStories();
        verify(apiService).getTopStories();
        verify(persistenceStore).saveTopStories(topStories);

        // Second call
        reset(apiService);
        reset(persistenceStore);
        when(persistenceStore.getTopStories()).thenReturn(Observable.just(topStories));

        Observable<List<Long>> secondCall = itemRepository.getTopStories();

        secondCall.test().assertNoErrors().assertResult(topStories);

        verify(persistenceStore).getTopStories();
        verify(apiService, never()).getTopStories();
    }

    // Should fetch TopStories only from API
    @SuppressWarnings("unchecked")
    @Test
    public void shouldNeverCallPersistenceForFetchTopStoriesCall() {
        List<Long> topStories = MockData.getTopStories();
        when(apiService.getTopStories()).thenReturn(Observable.just(topStories));

        Observable<List<Long>> fetchObs = itemRepository.fetchTopStories();

        assertEquals(fetchObs.blockingFirst(), topStories);

        verify(persistenceStore, never()).getTopStories();
        verify(apiService).getTopStories();
    }
}
