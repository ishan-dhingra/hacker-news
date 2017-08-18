package com.anythingintellect.hackernews.view;

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
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

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
    public void shouldRenderNewsListFromMockData() {
        Item item = MockData.getMockItem();
        activityRule.launchActivity(null);
        onView(withId(R.id.rvList))
                .check(matches(hasDescendant(withText(item.getTitle()))));
        onView(withId(R.id.rvList))
                .check(matches(hasDescendant(withText(String.valueOf(item.getScore())))));
        onView(withId(R.id.rvList)).check(matches(hasDescendant(withText(item.getBy()))));

    }


}
