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


    @Inject
    CommentListViewModel viewModel;
    @Inject
    ItemRepository repository;

    private ItemListAdapter adapter;


    public CommentListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDependencies();
        adapter = new ItemListAdapter(viewModel.getComments(), repository, R.layout.item_comment);
        long[] commentIds = getArguments().getLongArray(CommentActivity.KEY_COMMENT_IDS);
        viewModel.loadComments(commentIds);
    }

    private void resolveDependencies() {
        AppComponent appComponent = ((HackerNewsApp)getActivity().getApplication()).getAppComponent();
        appComponent.plusContextComponent(new ContextModule(getContext())).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCommentListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_list, container, false);
        binding.setVm(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rvList);
        setupRv(rvList);
    }

    private void setupRv(RecyclerView rvList) {
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvList.addItemDecoration(dividerItemDecoration);
    }

}
