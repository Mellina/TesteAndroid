package com.UI;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityAndroidTest {

     //new rule
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void onCreate() {
    }

    @Test
    public void onSetTitle() {
    }

    @Test
    public void onOpenFragment() {
    }

    @Test
    public void onSend() {
    }
}