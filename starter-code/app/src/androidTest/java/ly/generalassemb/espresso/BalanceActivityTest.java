package ly.generalassemb.espresso;


import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.collection.IsMapContaining.hasEntry;


import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

/**
 * Created by Jonathan Taylor on 7/21/16.
 */
@RunWith(AndroidJUnit4.class)
public class BalanceActivityTest {
    @Rule
    public ActivityTestRule<BalanceActivity> mActivityTestRule = new ActivityTestRule<>(BalanceActivity.class);

    @Test
    public void testBalanceVisible() throws Exception {
        onView(withId(R.id.balanceTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.balanceLabel)).check(matches(isDisplayed()));
    }

    @Test
    public void testWithdraw() throws Exception {
        String amt = "123.45";
        String expected = "-$123.45";
        testWithdraw(amt, expected);

    }

    public void testWithdraw(String amt, String expected){
        String desc = "Withdrawal";
        onView(withId(R.id.newTransactionButton)).perform(click());
        onView(withId(R.id.descriptionEditText)).perform(click(), clearText(), typeText(desc));
        onView(withId(R.id.amountEditText)).perform(click(), clearText(), typeText(amt), closeSoftKeyboard());
        onView(withId(R.id.withdrawButton)).perform(click());
        onView(withId(R.id.balanceTextView)).check(matches(withText(expected)));
    }

    @Test
    public void testDeposit() throws Exception {
        String amt = "123.45";
        String expected = "$123.45";
        testDeposit(amt, expected);

    }

    public void testDeposit(String amt, String expected){
        String desc = "Deposit";
        onView(withId(R.id.newTransactionButton)).perform(click());
        onView(withId(R.id.descriptionEditText)).perform(click(), clearText(), typeText(desc));
        onView(withId(R.id.amountEditText)).perform(click(), clearText(), typeText(amt), closeSoftKeyboard());
        onView(withId(R.id.depositButton)).perform(click());
        onView(withId(R.id.balanceTextView)).check(matches(withText(expected)));
    }

    @Test
    public void testMultiTransaction() throws Exception {
        testDeposit("12.50", "$12.50");
        testDeposit("12.50", "$25.00");
        testWithdraw("25.00", "$0.00");
        testWithdraw("25.00", "-$25.00");
    }

    @Test
    public void testTransactionsVisible() throws Exception {
        testDeposit("12.50", "$12.50");
        testDeposit("12.50", "$25.00");
        testWithdraw("25.00", "$0.00");
        testWithdraw("25.00", "-$25.00");

        onData(withText("$12.50"))
                .onChildView(withId(R.id.balance_item_description)).check(matches(withText("Deposit")));
        onData(allOf(is(instanceOf(Transaction.class)), withContent("25.00")))
                .onChildView(withId(R.id.balance_item_description)).check(matches(withText("Withdrawal")));


    }


    public static BoundedMatcher withContent(final String content) {
        return new BoundedMatcher<Transaction>() {
            @Override
            public void describeTo(org.hamcrest.Description description) {

            }

            @Override
            protected boolean matchesSafely(Transaction) {
                return false;
            }
        }
    }


}
