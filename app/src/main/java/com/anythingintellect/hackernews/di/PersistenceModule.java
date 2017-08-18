package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.persistence.InMemoryDBPersistenceStore;
import com.anythingintellect.hackernews.persistence.PersistenceStore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    @Provides
    @Singleton
    @Named("inMemory")
    PersistenceStore providesInMemoryStore() {
        return new InMemoryDBPersistenceStore();
    }

}
