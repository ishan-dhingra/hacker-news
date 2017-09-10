package com.anythingintellect.hackernews.di;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = BaseModule.class)
@Singleton
public interface AppComponent {

    ContextComponent plusContextComponent(ContextModule fragmentModule);

}
