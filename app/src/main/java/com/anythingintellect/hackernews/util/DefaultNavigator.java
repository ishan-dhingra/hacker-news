package com.anythingintellect.hackernews.util;

import android.content.Context;
import android.content.Intent;

import com.anythingintellect.hackernews.view.CommentActivity;

import java.util.List;

public class DefaultNavigator implements Navigator {

    final Context context;

    public DefaultNavigator(Context context) {
        this.context = context;
    }

    @Override
    public void openComments(List<Long> commentList, String title) {
        long[] comments = toArray(commentList);
        Intent commentIntent = new Intent(context, CommentActivity.class);
        commentIntent.putExtra(CommentActivity.KEY_COMMENT_IDS, comments);
        commentIntent.putExtra(CommentActivity.KEY_TITLE, title);
        context.startActivity(commentIntent);
    }

    private long[] toArray(List<Long> commentList) {
        long[] comments = new long[commentList.size()];
        int count = 0;
        for (long id : commentList) {
            comments[count++] = id;
        }
        return comments;
    }
}
