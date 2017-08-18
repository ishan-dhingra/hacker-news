package com.anythingintellect.hackernews.util;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class BindingUtils {

    @BindingAdapter("bind:html")
    public static void bindHtml(TextView view, String text) {
        if (text == null) {
            return;
        }
        Spanned spannedText;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spannedText = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spannedText = Html.fromHtml(text);
        }
        view.setText(spannedText);
    }

    @BindingAdapter("bind:timeAgo")
    public static void bindTimeAgo(TextView view, Date date) {
        if (date == null) {
            return;
        }
        Calendar postDate = Calendar.getInstance();
        postDate.setTime(date);
        Calendar now = Calendar.getInstance();
        String timeAgo;
        if (now.get(Calendar.YEAR) != postDate.get(Calendar.YEAR)) {
            timeAgo = (now.get(Calendar.YEAR) - postDate.get(Calendar.YEAR) ) + "y ago";
        } else if (now.get(Calendar.MONTH) != postDate.get(Calendar.MONTH)) {
            timeAgo = (now.get(Calendar.MONTH) - postDate.get(Calendar.MONTH) ) + "m ago";
        } else if (now.get(Calendar.DAY_OF_MONTH) != postDate.get(Calendar.DAY_OF_MONTH)) {
            timeAgo = (now.get(Calendar.DAY_OF_MONTH) - postDate.get(Calendar.DAY_OF_MONTH) ) + "d ago";
        } else if (now.get(Calendar.HOUR_OF_DAY) != postDate.get(Calendar.HOUR_OF_DAY)) {
            timeAgo = (now.get(Calendar.HOUR_OF_DAY) - postDate.get(Calendar.HOUR_OF_DAY) ) + "h ago";
        } else if (now.get(Calendar.MINUTE) != postDate.get(Calendar.MINUTE)) {
            timeAgo = (now.get(Calendar.MINUTE) - postDate.get(Calendar.MINUTE) ) + "min ago";
        } else {
            timeAgo = "just now";
        }
        view.setText(timeAgo);
    }

    @BindingAdapter("bind:isLoading")
    public static void swipeLoader(SwipeRefreshLayout layout, boolean isLoading) {
        layout.setRefreshing(isLoading);
    }



}
