package com.anythingintellect.hackernews.repo;

import com.anythingintellect.hackernews.model.Item;
import com.anythingintellect.hackernews.network.HackerNewsAPIService;
import com.anythingintellect.hackernews.persistence.PersistenceStore;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ItemRepositoryImpl implements ItemRepository {

    private final HackerNewsAPIService apiService;
    private final PersistenceStore persistenceStore;

    public ItemRepositoryImpl(HackerNewsAPIService apiService, PersistenceStore persistenceStore) {
        this.apiService = apiService;
        this.persistenceStore = persistenceStore;
    }

    @Override
    public Observable<Item> getItem(long id) {
        Observable<Item> news = persistenceStore.getItem(id);
        if(news == null) {
            news = apiService.getKid(id).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<Item>() {
                        @Override
                        public void accept(@NonNull Item item) throws Exception {
                            persistenceStore.saveItem(item);
                        }
                    });
        }
        return news;
    }

    @Override
    public Observable<List<Long>> getTopStories() {
        Observable<List<Long>> topStories = persistenceStore.getTopStories();
        if (topStories == null) {
            return apiService.getTopStories().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<List<Long>>() {
                @Override
                public void accept(@NonNull List<Long> ids) throws Exception {
                    persistenceStore.saveTopStories(ids);
                }
            });
        }
        return topStories;
    }

    // Skip the cache
    @Override
    public Observable<List<Long>> fetchTopStories() {
        return apiService.getTopStories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<List<Long>>() {
            @Override
            public void accept(@NonNull List<Long> ids) throws Exception {
                persistenceStore.saveTopStories(ids);
            }
        });
    }


}
