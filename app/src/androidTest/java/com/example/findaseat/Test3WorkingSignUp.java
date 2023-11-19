package com.example.findaseat;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)

public class Test3WorkingSignUp {

    @Rule
    public ActivityScenarioRule<MapsActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void test2WorkingSignUp() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.nav2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        imageView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.buttonSignUp), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        Uri uri = Uri.parse("android.resource://com.example.findaseat/drawable/isabella");
        try {
            simulatePictureFromCameraRoll(uri);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ViewInteraction textInputEditText = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.name),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Isabella"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.uscid),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("7777777777"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.affiliation),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("staff"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("perry"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.buttonLogIn), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                14),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.studentname), withText("Isabella"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Isabella")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.studentid), withText("7777777777"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView2.check(matches(withText("7777777777")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.studentaffil), withText("STAFF"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView3.check(matches(withText("STAFF")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static void simulatePictureFromCameraRoll(Uri pictureUri) throws  Exception {
        Exception returnException = null;
        Intent resultData = new Intent();

        resultData.setData(pictureUri);

        Intents.init();
        try {
            Matcher<Intent> expectedIntent = hasAction(Intent.ACTION_GET_CONTENT);
            intending(expectedIntent).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
            onView(withId(R.id.profpic)).perform(click());
            intended(expectedIntent);
        }
        catch (Exception e) {
            returnException = e;
        }
        finally {
            Intents.release();
        }

        if (returnException != null) {
            throw returnException;
        }
    }
}
