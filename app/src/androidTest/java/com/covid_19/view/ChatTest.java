package com.covid_19.view;

import android.app.Activity;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.covid_19.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class ChatTest {

    @Rule
    public ActivityTestRule<Chat> chatActivityTestRule = new ActivityTestRule<Chat>(Chat.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSendMessage() {
        //sendButton click
        Espresso.onView(withId(R.id.img_send)).perform(click());
        //close soft keyboard
        Espresso.closeSoftKeyboard();
    }

    @After
    public void tearDown() throws Exception {
        
    }

}