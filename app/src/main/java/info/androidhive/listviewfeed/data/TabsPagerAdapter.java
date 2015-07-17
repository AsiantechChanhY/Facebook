package info.androidhive.listviewfeed.data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.androidhive.listviewfeed.tabs.Messenger;
import info.androidhive.listviewfeed.tabs.News;
import info.androidhive.listviewfeed.tabs.Request;
import info.androidhive.listviewfeed.tabs.canhan;
import info.androidhive.listviewfeed.tabs.tintuc;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	int[] img;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}



	public TabsPagerAdapter(FragmentManager fm, int[] img) {
		super(fm);
		this.img = img;
	}

	@Override
	public int getCount() {
		return img.length;
	}

	public int getDrawableId(int icons) {
		return img[icons];
	}


	public Fragment getItem(int index) {

		switch (index) {
			case 0:
				// Top Rated fragment activity
				return new News();
			case 1:
				// Games fragment activity
				return new Request();
			case 2:
				// Movies fragment activity
				return new Messenger();
			case 3:
				return new tintuc();
			case 4:
				return new canhan();
		}

		return null;
	}

 }

