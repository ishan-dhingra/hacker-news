package com.anythingintellect.hackernews.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anythingintellect.hackernews.R;

public class CommentActivity extends AppCompatActivity {

    public static final String KEY_COMMENT_IDS = "commentIds";
    public static final String KEY_TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        String title = getIntent().getStringExtra(KEY_TITLE);
        setTitle(title);
        if (savedInstanceState == null) {
            CommentListFragment commentListFragment = new CommentListFragment();
            commentListFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, commentListFragment)
                    .commit();
        }
    }

}
