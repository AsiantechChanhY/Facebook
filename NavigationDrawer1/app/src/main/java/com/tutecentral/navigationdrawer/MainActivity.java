package com.tutecentral.navigationdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerList = (ListView) findViewById(R.id.left_drawer);


		dataList.add(new DrawerItem(" Favorites"));

		dataList.add(new DrawerItem("Events", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("Instagram", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("Find Friends", R.drawable.ic_action_good));
		dataList.add(new DrawerItem(" Favorites"));


		dataList.add(new DrawerItem("Feeds")); // adding a header to the list
		dataList.add(new DrawerItem("Most Recent", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("Close Friends", R.drawable.ic_action_good));

		dataList.add(new DrawerItem("SeeA ll"));
		dataList.add(new DrawerItem("Games", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("on This Day", R.drawable.ic_action_good));
		dataList.add(new DrawerItem("Chat", R.drawable.ic_action_gamepad));
		dataList.add(new DrawerItem("Like Pages", R.drawable.ic_action_labels));
		dataList.add(new DrawerItem("Nearby Places", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("Friends", R.drawable.ic_action_good));
		dataList.add(new DrawerItem("App Invites", R.drawable.ic_action_gamepad));
		dataList.add(new DrawerItem("Photos", R.drawable.ic_action_labels));
		dataList.add(new DrawerItem("Pokes", R.drawable.ic_action_good));
		dataList.add(new DrawerItem("Saved", R.drawable.ic_action_gamepad));
		dataList.add(new DrawerItem("Apps For You", R.drawable.ic_action_labels));

		 dataList.add(new DrawerItem("Groups")); // adding a header to the list
		dataList.add(new DrawerItem("Create Groups", R.drawable.ic_action_about));

		dataList.add(new DrawerItem("Pages")); // adding a header to the list
		dataList.add(new DrawerItem("Create Page", R.drawable.ic_action_about));

		dataList.add(new DrawerItem("Interests"));
		dataList.add(new DrawerItem("Pages and Public Figures", R.drawable.ic_action_settings));


		dataList.add(new DrawerItem("Settings"));
		dataList.add(new DrawerItem("Beta Program", R.drawable.ic_action_help));
		dataList.add(new DrawerItem("App Settings", R.drawable.app_settings_caspian));
		dataList.add(new DrawerItem("New Feed preferences", R.drawable.ic_action_good));
		dataList.add(new DrawerItem("Account Settings", R.drawable.account_settings_caspian));
		dataList.add(new DrawerItem("Code Generator", R.drawable.code_generator_caspian));
		dataList.add(new DrawerItem("Help Center", R.drawable.help_center_caspian));
		dataList.add(new DrawerItem("Activity Log", R.drawable.activity_log_caspian));
		dataList.add(new DrawerItem("privacy shortcuts", R.drawable.privacy_shortcuts_caspian));
		dataList.add(new DrawerItem("Terms & Policies", R.drawable.terms_policies_caspian));
		dataList.add(new DrawerItem("Report a Problem", R.drawable.ic_action_good));
		dataList.add(new DrawerItem("About", R.drawable.about_caspian));
		dataList.add(new DrawerItem("Mobile Data", R.drawable.mobile_data_caspian));
		dataList.add(new DrawerItem("Log Out", R.drawable.log_out_caspian));


		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);

		mDrawerList.setAdapter(adapter);
//
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}


}
