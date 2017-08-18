package com.anythingintellect.hackernews.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.anythingintellect.hackernews.repo.ItemRepository;
import com.anythingintellect.hackernews.util.Navigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


public class NewsListViewModel {

    // TODO: Dispose listener
    private final ObservableField<Boolean> showProgressBar;
    private final ObservableField<Boolean> showError;
    private final ObservableList<ItemViewModel> topStories;
    private final ItemRepository repository;
    private final Navigator navigator;

    @Inject
    public NewsListViewModel(ItemRepository repository, Navigator navigator) {
        this.showProgressBar = new ObservableField<>(false);
        this.topStories = new ObservableArrayList<>();
        this.showError = new ObservableField<>(false);
        this.repository = repository;
        this.navigator = navigator;
    }

    public void loadNews(boolean skipCache) {
        showProgress();
        showError.set(false);
        Observable<List<Long>> topStoriesObs;
        if (skipCache) {
            topStoriesObs = repository.fetchTopStories();
        } else {
            topStoriesObs = repository.getTopStories();
        }

        topStoriesObs
                .map(new Function<List<Long>, List<ItemViewModel>>() {
                    @Override
                    public List<ItemViewModel> apply(@NonNull List<Long> longs) throws Exception {
                        List<ItemViewModel> list = new ArrayList<>();
                        for (long id : longs) {
                            list.add(new ItemViewModel(repository, id, navigator));
                        }
                        return list;
                    }
                })
                .subscribe(new Observer<List<ItemViewModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<ItemViewModel> news) {
                        topStories.clear();
                        topStories.addAll(news);
                        hideProgress();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (topStories.size() == 0) {
                            showError.set(true);
                        }
                        hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void hideProgress() {
        showProgressBar.set(false);
    }

    private void showProgress() {
        showProgressBar.set(true);
    }

    public ObservableField<Boolean> getShowError() { return showError; }

    public ObservableField<Boolean> getShowProgressBar() {
        return showProgressBar;
    }

    public ObservableList<ItemViewModel> getTopStories() {
        return topStories;
    }
}
