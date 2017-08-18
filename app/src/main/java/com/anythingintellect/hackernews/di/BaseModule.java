package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.network.HackerNewsAPIService;
import com.anythingintellect.hackernews.persistence.PersistenceStore;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.repo.ItemRepositoryImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(includes = {NetworkModule.class, PersistenceModule.class})
public class BaseModule {

    @Singleton
    @Provides
    ItemRepository providesItemRepository(HackerNewsAPIService apiService, @Named("inMemory") PersistenceStore persistenceStore) {
        return new ItemRepositoryImpl(apiService, persistenceStore);
    }

}
