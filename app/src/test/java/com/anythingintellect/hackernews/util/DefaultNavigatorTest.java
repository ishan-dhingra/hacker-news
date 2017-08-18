package com.anythingintellect.hackernews.util;

import android.content.Context;
import android.content.Intent;

import com.anythingintellect.hackernews.BuildConfig;
import com.anythingintellect.hackernews.view.CommentActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DefaultNavigatorTest {

    Navigator navigator;
    @Mock
    Context context;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        navigator = new DefaultNavigator(context);
    }

    // Should call start activity with CommentActivity Intent on openComments
    @Test
    public void shouldCallStartActivityWithCommentActivityIntentWhenOpenComments() {
        List<Long> comments = MockData.getKids();
        String text = MockData.getMockItem().getText();
        ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
        navigator.openComments(comments, text);

        verify(context).startActivity(intentCaptor.capture());

        Intent intent = intentCaptor.getValue();
        assertEquals(CommentActivity.class.getName(), intent.getComponent().getClassName());
    }
}
