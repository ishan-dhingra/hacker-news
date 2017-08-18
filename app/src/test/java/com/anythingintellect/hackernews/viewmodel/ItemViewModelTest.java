package com.anythingintellect.hackernews.viewmodel;

import com.anythingintellect.hackernews.BaseTest;
import com.anythingintellect.hackernews.model.Item;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.util.MockData;
import com.anythingintellect.hackernews.util.Navigator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemViewModelTest extends BaseTest {

    private ItemViewModel itemViewModel;

    @Mock
    ItemRepository repository;
    @Mock
    Navigator navigator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        itemViewModel = new ItemViewModel(repository, MockData.getItemId(), navigator);
    }

    // Should load item data on success response
    @Test
    public void shouldLoadItemDataOnSuccessResponse() {
        Item item = MockData.getMockItem();
        item.setId(MockData.getItemId());
        when(repository.getItem(MockData.getItemId())).thenReturn(Observable.just(item));

        itemViewModel.loadItem();

        verify(repository).getItem(MockData.getItemId());
        assertEquals(itemViewModel.getItem().get(), item);
    }

    // Should only load the item for the first time only
    @Test
    public void shouldOnlyCallRepositoryForTheFirstTimeOnly() {
        // First call
        Item item = MockData.getMockItem();
        item.setId(MockData.getItemId());
        when(repository.getItem(MockData.getItemId())).thenReturn(Observable.just(item));

        itemViewModel.loadItem();

        assertEquals(itemViewModel.getItem().get(), item);

        // Second call
        reset(repository);

        itemViewModel.loadItem();

        verify(repository, never()).getItem(MockData.getItemId());
    }

    // Should show error on failed response
    @Test
    public void shouldShowErrorOnFailedResponse() {
        when(repository.getItem(MockData.getItemId()))
                .thenReturn(Observable.<Item>error(new IOException()));

        assertFalse(itemViewModel.getShowError().get());

        itemViewModel.loadItem();

        assertTrue(itemViewModel.getShowError().get());
    }

    // Should show progress while request in progress
    @Test
    public void shouldShowProgressWhileRequestInProgress() {
        BehaviorSubject<Item> response = BehaviorSubject.create();
        when(repository.getItem(MockData.getItemId())).thenReturn(response);

        itemViewModel.loadItem();

        assertTrue(itemViewModel.getShowProgress().get());
        response.onNext(MockData.getMockItem());
        response.onComplete();
        assertFalse(itemViewModel.getShowProgress().get());

    }

    // Should open comment activity on click
    @Test
    public void shouldOpenCommentActivityOnClick() {
        Item item = MockData.getMockItem();
        item.setId(MockData.getItemId());
        when(repository.getItem(MockData.getItemId())).thenReturn(Observable.just(item));
        itemViewModel.loadItem();

        itemViewModel.openKids();

        verify(navigator).openComments(item.getKids(), item.getTitle());

    }

}
