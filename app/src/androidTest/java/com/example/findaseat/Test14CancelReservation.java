package com.example.findaseat;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
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
public class Test14CancelReservation {

    @Rule
    public ActivityScenarioRule<MapsActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void test14CancelReservation() {
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
                allOf(withId(R.id.buttonLogIn), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.uscid),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("7777777777"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("perry"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.buttonLogIn), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.currresoutertoggle),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatImageView.perform(scrollTo(), click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.currentreservationtoggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.currentreservationbox),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.buttonCancelReservation), withText("Cancel Reservation"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.currresinfo),
                                        4),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.currresoutertoggle),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatImageView3.perform(scrollTo(), click());

        onView(withId(R.id.CurrResName)).check(matches(not(isDisplayed())));

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.pastrestoggle),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatImageView4.perform(scrollTo(), click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.pasttoggle9),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pastbox9),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.pastname9), withText("Taper Hall"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Taper Hall")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.paststatus9), withText("Status: Canceled"),
                        withParent(allOf(withId(R.id.pasttext9),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Status: Canceled")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.pasttime9), withText("Time: 09:00:00 - 10:00:00"),
                        withParent(allOf(withId(R.id.pasttext9),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("Time: 09:00:00 - 10:00:00")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.pastlocation9), withText("Location: Outdoor"),
                        withParent(allOf(withId(R.id.pasttext9),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView4.check(matches(withText("Location: Outdoor")));
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
}
