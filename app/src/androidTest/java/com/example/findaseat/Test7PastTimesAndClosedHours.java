package com.example.findaseat;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Checks;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Test7PastTimesAndClosedHours {

    @Rule
    public ActivityScenarioRule<MapsActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void test7PastTimesAndClosedHours() {
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
        textInputEditText.perform(replaceText("0135792468"), closeSoftKeyboard());

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

        onView(withId(R.id.nav1)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }
                    @Override
                    public String getDescription() {
                        return "click plus button";
                    }
                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject marker = mDevice.findObject(new UiSelector().descriptionContains("Taper Hall"));
        try {
            marker.click();
            marker.clickTopLeft();
            Rect rects = marker.getBounds();
            mDevice.click(rects.centerX(), rects.top - 30);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button = onView(
                allOf(withId(R.id.buttonReserve), withText("Make Reservation"),
                        childAtPosition(
                                allOf(withId(R.id.overlay),
                                        childAtPosition(
                                                withId(R.id.root_view),
                                                1)),
                                2),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction textView = onView(withId(7));
        textView.perform(click());

        ViewInteraction textView1 = onView(withId(19));
        textView1.perform(click());


        // closed hours: Color.LTGRAY
        // past times: #b2b2b2

//        textView.check(matches(isDisplayed()));
        textView.check(matches(backgroundShouldHaveColor(Color.LTGRAY)));

        textView1.check(matches(backgroundShouldHaveColor(Color.parseColor("#b2b2b2"))));

//        ViewInteraction textView4 = onView(
//                allOf(withParent(allOf(withId(R.id.gridLayout01),
//                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
//                        isDisplayed()));
//        textView4.check(matches(isDisplayed()));
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
    public static Matcher<Object> backgroundShouldHaveColor(int expectedColor) {
        return buttonShouldHaveBackgroundColor(equalTo(expectedColor));
    }
    private static Matcher<Object> buttonShouldHaveBackgroundColor(final Matcher<Integer> expectedObject) {
        final int[] color = new int[1];
        return new BoundedMatcher<Object, TextView>( TextView.class) {
            @Override
            public boolean matchesSafely(final TextView actualObject) {

                color[0] =((ColorDrawable) actualObject.getBackground()).getColor();


                if( expectedObject.matches(color[0])) {
                    return true;
                } else {
                    return false;
                }
            }
            @Override
            public void describeTo(final Description description) {
                // Should be improved!
                description.appendText("Color did not match "+color[0]);
            }
        };
    }
}
