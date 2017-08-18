package com.anythingintellect.hackernews.viewmodel;

import android.databinding.ObservableField;

import com.anythingintellect.hackernews.model.Item;
import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.util.Navigator;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public class ItemViewModel {

    private final ItemRepository repository;
    private final ObservableField<Item> item;
    private final ObservableField<Boolean> showProgress;
    private final ObservableField<Boolean> showError;
    private final long id;
    private final Navigator navigator;

    public ItemViewModel(ItemRepository repository, long id, Navigator navigator) {
        this.repository = repository;
        this.id = id;
        this.item = new ObservableField<>();
        this.showProgress = new ObservableField<>(false);
        this.navigator = navigator;
        this.showError = new ObservableField<>(false);
    }


    public ObservableField<Item> getItem() {
        return item;
    }

    public void loadItem() {
        if (item.get() != null) {
            return;
        }
        showLoading();
        repository.getItem(id).subscribe(new Observer<Item>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Item itemObj) {
                item.set(itemObj);
                hideLoading();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                hideLoading();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void showLoading() {
        showError.set(false);
        showProgress.set(true);
    }

    private void hideLoading() {
        if (item.get() == null) {
            showError.set(true);
        }
        showProgress.set(false);
    }

    public void openKids() {
        if (item.get() == null) {
            return;
        }
        navigator.openComments(item.get().getKids(), item.get().getTitle());
    }

    public long getId() {
        return id;
    }

    public ObservableField<Boolean> getShowProgress() {
        return showProgress;
    }

    public ObservableField<Boolean> getShowError() {
        return showError;
    }
}
