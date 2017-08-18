package com.anythingintellect.hackernews.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.anythingintellect.hackernews.BuildConfig;
import com.anythingintellect.hackernews.TestHackerNewsApp;
import com.anythingintellect.hackernews.util.MockData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestHackerNewsApp.class)
public class CommentActivityTest {

    // Should set title given by intent
    @Test
    public void shouldSetTitleGivenByIntent() {
        String title = "Test";
        Intent intent = new Intent(ShadowApplication.getInstance().getApplicationContext(), CommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(CommentActivity.KEY_TITLE, title);
        bundle.putLongArray(CommentActivity.KEY_COMMENT_IDS, MockData.getKidsArray());
        intent.putExtras(bundle);
        Activity activity = Robolectric.buildActivity(CommentActivity.class, intent)
                .create().get();
        assertEquals(title, activity.getTitle());
    }

}
