package com.anythingintellect.hackernews.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anythingintellect.hackernews.HackerNewsApp;
import com.anythingintellect.hackernews.R;
import com.anythingintellect.hackernews.adapter.ItemListAdapter;
import com.anythingintellect.hackernews.databinding.FragmentNewsListBinding;
import com.anythingintellect.hackernews.di.AppComponent;
import com.anythingintellect.hackernews.di.BaseFragmentModule;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.viewmodel.NewsListViewModel;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment {

    @Inject
    ItemRepository repository;
    @Inject
    NewsListViewModel viewModel;

    private ItemListAdapter adapter;

    public NewsListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDependencies();
        adapter = new ItemListAdapter(viewModel.getTopStories(), repository, R.layout.item_news);
        viewModel.loadNews(false);
    }

    private void resolveDependencies() {
        AppComponent appComponent = ((HackerNewsApp)getActivity().getApplication()).getAppComponent();
        appComponent.plusFragmentComponent(new BaseFragmentModule(getContext())).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNewsListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false);
        binding.setVm(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rvList);
        setupRv(rvList);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        setupSwipeLayout(refreshLayout);
    }

    private void setupSwipeLayout(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.loadNews(true);
            }
        });
    }

    private void setupRv(RecyclerView rvList) {
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(dividerItemDecoration);
    }

}
