package com.anythingintellect.hackernews.view;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anythingintellect.hackernews.HackerNewsApp;
import com.anythingintellect.hackernews.R;
import com.anythingintellect.hackernews.adapter.ItemListAdapter;
import com.anythingintellect.hackernews.databinding.ActivityMainBinding;
import com.anythingintellect.hackernews.di.AppComponent;
import com.anythingintellect.hackernews.di.ContextModule;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.viewmodel.NewsListViewModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ItemRepository repository;
    @Inject
    NewsListViewModel viewModel;

    private ItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDependencies();
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(viewModel);
        adapter = new ItemListAdapter(viewModel.getTopStories(), repository, R.layout.item_news);
        viewModel.loadNews(false);
        RecyclerView rvList = (RecyclerView) findViewById(R.id.rvList);
        setupRv(rvList);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        setupSwipeLayout(refreshLayout);

    }

    private void resolveDependencies() {
        AppComponent appComponent = ((HackerNewsApp)getApplication()).getAppComponent();
        appComponent.plusContextComponent(new ContextModule(this)).inject(this);
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
        rvList.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(dividerItemDecoration);
    }

}
