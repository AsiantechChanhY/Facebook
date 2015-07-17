package info.androidhive.listviewfeed;

import android.support.v4.view.ViewPager;

import info.androidhive.listviewfeed.data.SlidingTabLayout;

/**
 * Created by MyConputer on 7/11/2015.
 */
public class MainActivity {

    ViewPager viewPager;
    SlidingTabLayout tabs;
    int[] icons={
            R.drawable.first_tab,
            R.drawable.second_tab,
            R.drawable.third_tab,
            R.drawable.fourth_tab,
            R.drawable.fifth_tab
    };
    CharSequence[] titles={
            "News",
            "Request",
            "Messenger",
            "Notification",
            "More"
    };
    ViewpagerAdapter pagerAdapter;
}
