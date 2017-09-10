package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.view.CommentActivity;
import com.anythingintellect.hackernews.view.MainActivity;

import dagger.Subcomponent;


@Subcomponent(modules = ContextModule.class)
@PerContext
public interface ContextComponent {

    void inject(MainActivity mainActivity);
    void inject(CommentActivity commentActivity);

}

