package com.anythingintellect.hackernews.mock;

import com.anythingintellect.hackernews.model.Item;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;


public class MockData {
    public static Item getMockItem() {
        Item item = new Item();
        item.setBy("ishan");
        item.setScore(100);
        item.setText("Android Z is coming...");
        item.setTitle("Android Z is coming...");
        item.setTime(getMockDate());
        item.setType("story");
        item.setId(100);
        item.setKids(getKids());
        return item;
    }

    public static List<Long> getKids() {
        return Arrays.asList(101l, 102l, 103l);
    }

    public static long[] getKidsArray() {
        return new long[] {101l, 102l, 103l};
    }

    public static Date getMockDate() {
        return new Date(1502948252198l);
    }

    public static List<Long> getTopStories() {
        return Arrays.asList(101l, 102l, 103l);
    }

    public static Observable<Item> getItemObservable() {
        return Observable.just(getMockItem());
    }

    public static long getItemId() {
        return 101l;
    }

    public static Date getDate() {
        return new Date(1503021399486l);
    }
}
