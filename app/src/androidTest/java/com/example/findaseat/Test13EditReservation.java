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
public class Test13EditReservation {

    @Rule
    public ActivityScenarioRule<MapsActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void test13EditReservation() {
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
                allOf(withId(R.id.buttonEdit), withText("Edit Reservation"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.currresinfo),
                                        4),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());



        ViewInteraction textView = onView(withId(69));
        textView.perform(scrollTo(), click());

        ViewInteraction textView2 = onView(withId(21));
        textView2.perform(scrollTo(), click());

        ViewInteraction textView3 = onView(withId(27));
        textView3.perform(scrollTo(), click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.buttonReserve), withText("Edit Reservation"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.currresoutertoggle),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatImageView3.perform(scrollTo(), click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.currentreservationtoggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.currentreservationbox),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.currresstatus), withText("Status: Current"),
                        withParent(allOf(withId(R.id.currresinfo),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView5.check(matches(withText("Status: Current")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.currrestime), withText("Time: 09:00:00 - 10:00:00"),
                        withParent(allOf(withId(R.id.currresinfo),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView6.check(matches(withText("Time: 09:00:00 - 10:00:00")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.currreslocation), withText("Location: Outdoor"),
                        withParent(allOf(withId(R.id.currresinfo),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView7.check(matches(withText("Location: Outdoor")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.CurrResName), withText("Taper Hall"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView8.check(matches(withText("Taper Hall")));
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
