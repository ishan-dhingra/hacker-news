package com.anythingintellect.hackernews.network;

import com.anythingintellect.hackernews.model.Item;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface HackerNewsAPIService {

    @GET("v0/topstories.json")
    Observable<List<Long>> getTopStories();

    @GET("v0/item/{id}.json")
    Observable<Item> getKid(@Path("id") long id);


}
