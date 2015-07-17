package info.androidhive.listviewfeed;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import info.androidhive.listviewfeed.data.SlidingTabLayout;
import info.androidhive.listviewfeed.data.TabsPagerAdapter;
import info.androidhive.listviewfeed.friend_olline.BaseActivity;
import info.androidhive.listviewfeed.friend_olline.SampleListFragment;


public class MainActivity extends BaseActivity {
    public MainActivity() {
        super(R.string.app_name);
    }
    ViewPager viewPager;
    SlidingTabLayout tabs;
    BadgeView badge;
    int[] icons= {
            R.drawable.newfeed,
            R.drawable.request,
            R.drawable.messenger,
            R.drawable.notifi,
            R.drawable.more
    };
    CharSequence[] titles= {
            "News",
            "Request",
            "Messenger",
            "Notification",
            "More"
    };
    TabsPagerAdapter pagerAdapter;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        getActionBar().setIcon(R.drawable.ic_facebook);

        viewPager=(ViewPager) findViewById(R.id.pager);
        tabs=(SlidingTabLayout) findViewById(R.id.tabBar);
        pagerAdapter=new TabsPagerAdapter(getSupportFragmentManager(),icons);
        viewPager.setAdapter(pagerAdapter);

        getSlidingMenu().setMode(SlidingMenu.RIGHT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame_two, new SampleListFragment())
                .commit();

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                getActionBar().setTitle(titles[position]);
                if(position==2)
                {
                        badge.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabs.setDistributeEvenly(true);
        //tabs.setCustomTabView(R.layout.custom_tabs_layout, 0);
        tabs.setViewPager(viewPager);

         badge = new BadgeView(this, tabs.getTabStrip().getChildAt(2));
        badge.setText("7");
        badge.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.menu_frame_two, new SampleListFragment())
                        .commit();
                getSlidingMenu().getContext();
                getSlidingMenu().showMenu();


                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();

                return true;
            }
        });

        return true;
    }

}