package com.anythingintellect.hackernews.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anythingintellect.hackernews.HackerNewsApp;
import com.anythingintellect.hackernews.R;
import com.anythingintellect.hackernews.adapter.ItemListAdapter;
import com.anythingintellect.hackernews.databinding.FragmentCommentListBinding;
import com.anythingintellect.hackernews.di.AppComponent;
import com.anythingintellect.hackernews.di.ContextModule;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.viewmodel.CommentListViewModel;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentListFragment extends Fragment {


    public CommentListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
