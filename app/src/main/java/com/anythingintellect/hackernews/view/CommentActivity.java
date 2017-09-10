package com.anythingintellect.hackernews.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anythingintellect.hackernews.HackerNewsApp;
import com.anythingintellect.hackernews.R;
import com.anythingintellect.hackernews.adapter.ItemListAdapter;
import com.anythingintellect.hackernews.databinding.FragmentCommentListBinding;
import com.anythingintellect.hackernews.di.AppComponent;
import com.anythingintellect.hackernews.di.ContextModule;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.viewmodel.CommentListViewModel;

import javax.inject.Inject;

public class CommentActivity extends AppCompatActivity {

    public static final String KEY_COMMENT_IDS = "commentIds";
    public static final String KEY_TITLE = "title";

    @Inject
    CommentListViewModel viewModel;
    @Inject
    ItemRepository repository;

    private ItemListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentCommentListBinding binding = DataBindingUtil.setContentView(this, R.layout.fragment_comment_list);
        binding.setVm(viewModel);
        String title = getIntent().getStringExtra(KEY_TITLE);
        setTitle(title);
        resolveDependencies();
        adapter = new ItemListAdapter(viewModel.getComments(), repository, R.layout.item_comment);
        long[] commentIds = getIntent().getLongArrayExtra(CommentActivity.KEY_COMMENT_IDS);
        viewModel.loadComments(commentIds);
        RecyclerView rvList = (RecyclerView)findViewById(R.id.rvList);
        setupRv(rvList);
    }

    private void setupRv(RecyclerView rvList) {
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(dividerItemDecoration);
    }

    private void resolveDependencies() {
        AppComponent appComponent = ((HackerNewsApp)getApplication()).getAppComponent();
        appComponent.plusContextComponent(new ContextModule(this)).inject(this);
    }

}
