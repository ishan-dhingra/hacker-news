package com.anythingintellect.hackernews.view;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.anythingintellect.hackernews.HackerNewsApp;
import com.anythingintellect.hackernews.R;
import com.anythingintellect.hackernews.di.AppComponent;
import com.anythingintellect.hackernews.di.BaseModule;
import com.anythingintellect.hackernews.di.DaggerAppComponent;
import com.anythingintellect.hackernews.di.PersistenceModule;
import com.anythingintellect.hackernews.mock.EspressoMockNetworkModule;
import com.anythingintellect.hackernews.mock.MockData;
import com.anythingintellect.hackernews.model.Item;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CommentActivityTest {

    @Rule
    ActivityTestRule<CommentActivity> activityRule =
            new ActivityTestRule<>(CommentActivity.class, false, false);


    @Before
    public void setup() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .baseModule(new BaseModule())
                .networkModule(new EspressoMockNetworkModule())
                .persistenceModule(new PersistenceModule())
                .build();
        getApp().setAppComponent(appComponent);
    }

    private HackerNewsApp getApp() {
        return (HackerNewsApp) InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getApplicationContext();
    }

    @Test
    public void shouldRenderCommentsFromMockData() {
        Item item = MockData.getMockItem();
        Intent intent = new Intent(getApp(), CommentActivity.class);
        intent.putExtra(CommentActivity.KEY_COMMENT_IDS, MockData.getKidsArray());
        intent.putExtra(CommentActivity.KEY_TITLE, item.getTitle());
        activityRule.launchActivity(null);
        onView(withId(R.id.rvList))
                .check(matches(hasDescendant(withText(item.getText()))));
        onView(withId(R.id.rvList))
                .check(matches(hasDescendant(withText(String.valueOf(item.getBy())))));
    }

}
