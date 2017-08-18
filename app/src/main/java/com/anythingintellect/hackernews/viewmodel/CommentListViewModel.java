package com.anythingintellect.hackernews.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.anythingintellect.hackernews.repo.ItemRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class CommentListViewModel {

    // TODO: Dispose listener
    private final ObservableList<ItemViewModel> comments;
    private final ObservableField<Boolean> noComments;
    private final ItemRepository repository;

    @Inject
    public CommentListViewModel(ItemRepository repository) {
        this.comments = new ObservableArrayList<>();
        this.repository = repository;
        this.noComments = new ObservableField<>(false);
    }

    public void loadComments(long[] ids) {
        if (ids == null || ids.length == 0) {
            noComments.set(true);
            return;
        }
        List<ItemViewModel> list = new ArrayList<>();
        for (long id : ids) {
            list.add(new ItemViewModel(repository, id, null));
        }
        comments.addAll(list);

    }

    public ObservableList<ItemViewModel> getComments() {
        return comments;
    }

    public ObservableField<Boolean> getNoComments() {
        return noComments;
    }

}
