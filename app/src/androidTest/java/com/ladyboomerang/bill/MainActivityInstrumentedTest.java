package com.ladyboomerang.bill;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.res.Resources;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest
{

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBillField_maskWorks()
    {
        onView(withId(R.id.bill)).perform(typeText("320"));
        onView(withText("R$3,20")).check(matches(isDisplayed()));
    }

    @Test
    public void testAmountPaidField_maskWorks()
    {
        onView(withId(R.id.amount_paid)).perform(typeText("200"));
        onView(withText("R$2,00")).check(matches(isDisplayed()));
    }

    @Test
    public void testCalculateChangeMessage_BillIsGreaterThanAmountPaid()
    {
        onView(withId(R.id.bill)).perform(typeText("320"));
        onView(withId(R.id.amount_paid)).perform(typeText("200"));
        onView(withId(R.id.calculate)).perform(click());

        Resources res = mActivityRule.getActivity().getResources();
        String result = res.getString(R.string.change_error);

        onView(withText(result)).check(matches(isDisplayed()));
    }

    @Test
    public void testCalculateChangeMessage_NumberOfBanknotesAndCoinsAreGreaterThanZero()
    {
        onView(withId(R.id.bill)).perform(typeText("194"));
        onView(withId(R.id.amount_paid)).perform(typeText("1000"));
        onView(withId(R.id.calculate)).perform(click());

        String result = getMessageResult("R$8,06", 4, 2);
        onView(withText(result)).check(matches(isDisplayed()));
    }

    @Test
    public void testCalculateChangeMessage_OnlyNumberOfBanknoteAreGreaterThanZero()
    {
        onView(withId(R.id.bill)).perform(typeText("900"));
        onView(withId(R.id.amount_paid)).perform(typeText("2000"));
        onView(withId(R.id.calculate)).perform(click());

        String result = getMessageResult("R$11,00", 2, 0);
        onView(withText(result)).check(matches(isDisplayed()));
    }

    @Test
    public void testCalculateChangeMessage_OnlyNumberOfCoinsAreGreaterThanZero()
    {
        onView(withId(R.id.bill)).perform(typeText("194"));
        onView(withId(R.id.amount_paid)).perform(typeText("200"));
        onView(withId(R.id.calculate)).perform(click());

        String result = getMessageResult( "R$0,06", 0,  2);
        onView(withText(result)).check(matches(isDisplayed()));
    }

    private String getMessageResult(String value, int numBanknotes, int numCoins)
    {
        Resources res = mActivityRule.getActivity().getResources();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(res.getString(R.string.change_result), value));

        if (numBanknotes > 0)
        {
            sb.append(" ");
            sb.append(res.getQuantityString(R.plurals.change_result_banknotes, numBanknotes, numBanknotes));
        }

        if (numCoins > 0)
        {
            sb.append(numBanknotes > 0 ? " e " : " ");
            sb.append(res.getQuantityString(R.plurals.change_result_coins, numCoins, numCoins));
        }

        sb.append(".");
        System.out.println(sb.toString());
        return sb.toString();
    }
}