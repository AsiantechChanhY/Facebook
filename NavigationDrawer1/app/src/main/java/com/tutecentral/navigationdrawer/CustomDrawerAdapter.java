package com.tutecentral.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

	Context context;
	List<DrawerItem> drawerItemList;
	int layoutResID;


	public CustomDrawerAdapter(Context context, int layoutResourceID,
			List<DrawerItem> listItems) {
		super(context, layoutResourceID, listItems);
		this.context = context;
		this.drawerItemList = listItems;
		this.layoutResID = layoutResourceID;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

	
		DrawerItemHolder drawerHolder;
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			view = inflater.inflate(layoutResID, parent, false);

			drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);

			drawerHolder.textkhong = (LinearLayout) view.findViewById(R.id.textkhong);

			drawerHolder.text = (TextView) view.findViewById(R.id.text);

			drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

			drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);

			drawerHolder.headerLayout = (LinearLayout) view.findViewById(R.id.headerLayout);

			drawerHolder.itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);

			view.setTag(drawerHolder);

		}
		else
		{
			drawerHolder = (DrawerItemHolder) view.getTag();

		}
		
		DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
		if(dItem.Text != null)
		{
			drawerHolder.textkhong.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);

			drawerHolder.text.setText(dItem.getText());
		}


		else  if (dItem.getTitle() != null) {
			drawerHolder.textkhong.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.title.setText(dItem.getTitle());
			Log.d("Getview","Passed4");
		}
		else
		{
			drawerHolder.textkhong.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);

			drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
					dItem.getImgResID()));
			drawerHolder.ItemName.setText(dItem.getItemName());
			Log.d("Getview","Passed5");
		}
		return view;
	}

	private static class DrawerItemHolder {
		TextView ItemName, title, text;
		ImageView icon ;
		LinearLayout headerLayout, itemLayout, textkhong;
	}
}