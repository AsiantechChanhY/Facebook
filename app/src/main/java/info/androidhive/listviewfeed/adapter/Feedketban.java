package info.androidhive.listviewfeed.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

/**
 * Created by MyConputer on 6/29/2015.
 */
public class Feedketban extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public Feedketban(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item_kb, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.txtcmnamecutom);

        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.imgcmanh);

        final FeedItem item = feedItems.get(position);

        name.setText(item.getName());
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

        final TextView dakb = (TextView) convertView.findViewById(R.id.txttlcm);

        final Button imgkb = (Button) convertView.findViewById(R.id.imgcmketban);
        imgkb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setSelected(!v.isSelected());
                if (v.isSelected()) {

                  imgkb.setBackgroundResource(R.drawable.nokb);
                    dakb.setText("Request");
                }
                else {
                    imgkb.setBackgroundResource(R.drawable.kbf);
                    dakb.setText("No request");
                }

            }
        });
        return convertView;
    }
}
