package com.anythingintellect.hackernews.util;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Spannable;
import android.widget.TextView;

import com.anythingintellect.hackernews.BaseTest;
import com.anythingintellect.hackernews.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class BindingUtilsTest extends BaseTest {

    @Mock
    TextView textView;
    @Mock
    SwipeRefreshLayout swipeRefreshLayout;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    // Bind HTML Should set spannable on textView
    @Test
    public void shouldSetSpannedTextWhenBindHTML() {
        BindingUtils.bindHtml(textView, "<b>strong</b>");
        verify(textView).setText(any(Spannable.class));
    }

    // Time Ago Should set text on textView
    @Test
    public void shouldSetTimeTextWhenBindTimeAgo() {
        BindingUtils.bindTimeAgo(textView, MockData.getDate());
        verify(textView).setText(any(String.class));
    }

    // IsLoading Should set refreshing on layout
    @Test
    public void shouldSetRefreshingOnIsLoadingSet() {
        boolean isLoading = true;
        BindingUtils.swipeLoader(swipeRefreshLayout, isLoading);
        verify(swipeRefreshLayout).setRefreshing(isLoading);
    }

}
