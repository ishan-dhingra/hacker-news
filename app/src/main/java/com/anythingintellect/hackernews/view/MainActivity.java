package com.anythingintellect.hackernews.view;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.anythingintellect.hackernews.HackerNewsApp;
import com.anythingintellect.hackernews.R;
import com.anythingintellect.hackernews.adapter.ItemListAdapter;
import com.anythingintellect.hackernews.databinding.FragmentNewsListBinding;
import com.anythingintellect.hackernews.di.AppComponent;
import com.anythingintellect.hackernews.di.BaseFragmentModule;
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
        Log.d("hacker", "On Create");
        resolveDependencies();
        FragmentNewsListBinding binding = DataBindingUtil.setContentView(this, R.layout.fragment_news_list);
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
        appComponent.plusFragmentComponent(new BaseFragmentModule(this)).inject(this);
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
