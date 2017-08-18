package com.anythingintellect.hackernews.viewmodel;

import com.anythingintellect.hackernews.BaseTest;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.util.MockData;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewListViewModelTest extends BaseTest {

    @Mock
    ItemRepository itemRepository;


    NewsListViewModel newsListViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        newsListViewModel = new NewsListViewModel(itemRepository, null);
    }


    // Should load news considering cache when called with skipCache false
    @Test
    public void shouldLoadNewsConsideringCacheWhenCalledWithSkipCacheFalse() {

        List<Long> topStories = MockData.getTopStories();
        when(itemRepository.getTopStories()).thenReturn(Observable.just(topStories));

        newsListViewModel.loadNews(false);

        verify(itemRepository).getTopStories();

    }
    // Should load news without considering cache when called with skipCache true
    @Test
    public void shouldLoadNewsWithoutCacheWhenCalledWithSkipCacheTrue() {

        List<Long> topStories = MockData.getTopStories();
        when(itemRepository.fetchTopStories()).thenReturn(Observable.just(topStories));

        newsListViewModel.loadNews(true);

        verify(itemRepository).fetchTopStories();
        verify(itemRepository, never()).getTopStories();

    }

    // Should populate topStories on success
    @Test
    public void shouldPopulateStoriesOnSuccess() {

        List<Long> topStories = MockData.getTopStories();
        when(itemRepository.getTopStories()).thenReturn(Observable.just(topStories));

        newsListViewModel.loadNews(false);

        assertEquals(newsListViewModel.getTopStories().size(), topStories.size());

    }


    // Should trigger error flag and reset progress flag when failed to get stories
    @Test
    public void shouldTriggerErrorFlagAndResetProgressFlagWhenFailedToGetStories() {
        when(itemRepository.getTopStories())
                .thenReturn(Observable.<List<Long>>error(new IOException()));

        newsListViewModel.loadNews(false);

        assertTrue(newsListViewModel.getShowError().get());
        assertFalse(newsListViewModel.getShowProgressBar().get());
    }

    // Should show and hide progress bar during request
    @Test
    public void shouldShowHideProgressBarDuringRequest() {

        List<Long> topStories = MockData.getTopStories();
        BehaviorSubject<List<Long>> response = BehaviorSubject.create();
        when(itemRepository.getTopStories()).thenReturn(response);

        newsListViewModel.loadNews(false);

        assertTrue(newsListViewModel.getShowProgressBar().get());

        response.onNext(topStories);
        response.onComplete();

        assertFalse(newsListViewModel.getShowProgressBar().get());


    }



}
