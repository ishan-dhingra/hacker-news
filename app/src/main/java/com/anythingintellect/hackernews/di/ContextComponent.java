package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.view.CommentListFragment;
import com.anythingintellect.hackernews.view.MainActivity;

import dagger.Subcomponent;


@Subcomponent(modules = ContextModule.class)
@PerFragment
public interface ContextComponent {

    void inject(MainActivity mainActivity);
    void inject(CommentListFragment commentListFragment);

}

