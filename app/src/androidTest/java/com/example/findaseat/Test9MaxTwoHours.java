package com.example.findaseat;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.graphics.Rect;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Test9MaxTwoHours {

    @Rule
    public ActivityScenarioRule<MapsActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void test9MaxTwoHours() {
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

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.week_toggle),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatImageView2.perform(click());

//        ViewInteraction textView = onView(
//                allOf(withText("15"),
//                        childAtPosition(
//                                allOf(withId(R.id.gridLayout01),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.LinearLayout")),
//                                                0)),
//                                19),
//                        isDisplayed()));
//        textView.perform(click());

        int initial1 = Integer.valueOf(getText(withId(19)));
        int initial2 = Integer.valueOf(getText(withId(25)));
        int initial3 = Integer.valueOf(getText(withId(31)));
        int initial4 = Integer.valueOf(getText(withId(37)));
        int initial5 = Integer.valueOf(getText(withId(43)));

        initial1--;
        initial2--;
        initial3--;
        initial4--;

        String expect1 = String.valueOf(initial1);
        String expect2 = String.valueOf(initial2);
        String expect3 = String.valueOf(initial3);
        String expect4 = String.valueOf(initial4);
        String expect5 = String.valueOf(initial5);


        ViewInteraction textView = onView(withId(19));
        textView.perform(click());

//        ViewInteraction textView2 = onView(
//                allOf(withText("15"),
//                        childAtPosition(
//                                allOf(withId(R.id.gridLayout01),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.LinearLayout")),
//                                                0)),
//                                25),
//                        isDisplayed()));
//        textView2.perform(click());
        ViewInteraction textView2 = onView(withId(25));
        textView2.perform(click());

//        ViewInteraction textView3 = onView(
//                allOf(withText("15"),
//                        childAtPosition(
//                                allOf(withId(R.id.gridLayout01),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.LinearLayout")),
//                                                0)),
//                                31),
//                        isDisplayed()));
//        textView3.perform(click());
        ViewInteraction textView3 = onView(withId(31));
        textView3.perform(click());

//        ViewInteraction textView4 = onView(
//                allOf(withText("15"),
//                        childAtPosition(
//                                allOf(withId(R.id.gridLayout01),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.LinearLayout")),
//                                                0)),
//                                37),
//                        isDisplayed()));
//        textView4.perform(click());
        ViewInteraction textView4 = onView(withId(37));
        textView4.perform(scrollTo()).perform(click());

//        ViewInteraction textView5 = onView(
//                allOf(withText("15"),
//                        childAtPosition(
//                                allOf(withId(R.id.gridLayout01),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.LinearLayout")),
//                                                0)),
//                                43),
//                        isDisplayed()));
//        textView5.perform(click());
        ViewInteraction textView5 = onView(withId(43));
        textView5.perform(scrollTo()).perform(click());

        textView = onView(allOf(withId(19), withText(expect1)));
        textView2 = onView(allOf(withId(25), withText(expect2)));
        textView3 = onView(allOf(withId(31), withText(expect3)));
        textView4 = onView(allOf(withId(37), withText(expect4)));
        textView5 = onView(allOf(withId(43), withText(expect5)));
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
    String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }
}
