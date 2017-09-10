package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.view.CommentListFragment;
import com.anythingintellect.hackernews.view.MainActivity;
import com.anythingintellect.hackernews.view.NewsListFragment;

import dagger.Subcomponent;


@Subcomponent(modules = BaseFragmentModule.class)
@PerFragment
public interface FragmentComponent {

    void inject(MainActivity mainActivity);
    void inject(CommentListFragment commentListFragment);

}

