package com.anythingintellect.hackernews.di;

import com.anythingintellect.hackernews.view.CommentListFragment;
import com.anythingintellect.hackernews.view.NewsListFragment;

import dagger.Subcomponent;


@Subcomponent(modules = BaseFragmentModule.class)
@PerFragment
public interface FragmentComponent {

    void inject(NewsListFragment newsListFragment);
    void inject(CommentListFragment commentListFragment);

}

