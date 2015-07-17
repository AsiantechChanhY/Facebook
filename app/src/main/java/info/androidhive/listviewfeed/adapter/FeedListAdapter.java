package info.androidhive.listviewfeed.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.listviewfeed.FeedImageView;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;
import info.androidhive.listviewfeed.model.comment;
import info.androidhive.listviewfeed.model.comment_adapter;

public class FeedListAdapter extends BaseAdapter {

    private RelativeLayout real3;
    private EditText edtcm;
    private String noidungcm;
    private ListView lvcm;
    private comment_adapter adaptercm = null;
    private static ArrayList<comment> arrcomment = new ArrayList<comment>();

    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    int tanglike = 0;

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);

        final FeedItem item = feedItems.get(position);

        name.setText(item.getName());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Feed image
        if (item.getImge() != null) {
            feedImageView.setImageUrl(item.getImge(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        final TextView like = (TextView) convertView.findViewById(R.id.txtlike);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isLiked()) {
                    item.setGetCountLiked(item.getGetCountLiked() - 1);
                    item.setIsLiked(false);
                    notifyDataSetChanged();
                } else {
                    item.setGetCountLiked(item.getGetCountLiked() + 1);
                    item.setIsLiked(true);
                    notifyDataSetChanged();

                }
            }
        });
        if (item.isLiked()) {
            like.setText("Thích");
            like.setTextColor(Color.parseColor("#5890ff"));
        } else {
            like.setText("Thích");
            like.setTextColor(Color.parseColor("#000000"));
        }


   final TextView countlike = (TextView) convertView.findViewById(R.id.txtlcs);
        if (item.getGetCountLiked() == 0) {
            countlike.setVisibility(View.GONE);
        } else {
            countlike.setText(item.getGetCountLiked() + " người thích ");
            countlike.setVisibility(View.VISIBLE);
        }

     final TextView comment = (TextView) convertView.findViewById(R.id.txtcomment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext(), R.style.AppBaseTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.comment_dialog);
                dialog.show();

                String likecoment;
                final TextView txtlikecomment = (TextView) dialog.findViewById(R.id.txtcomentsl);
                if (item.isLiked()) {
                    likecoment = "Bạn và " + (item.getGetCountLiked() - 1) + " người thích nội dung này";
                } else {
                    likecoment = item.getGetCountLiked() + " Người thích nội dung này";
                }
                txtlikecomment.setText(likecoment);

      final ImageView imglikecommnet = (ImageView) dialog.findViewById(R.id.comentlike);
                imglikecommnet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.isLiked()) {
                            item.setGetCountLiked(item.getGetCountLiked() - 1);
                            item.setIsLiked(false);
                            txtlikecomment.setText((item.getGetCountLiked()) + " người thích nội dung này");
                            notifyDataSetChanged();
                        } else {
                            item.setGetCountLiked(item.getGetCountLiked() + 1);
                            item.setIsLiked(true);
                            txtlikecomment.setText("Bạn và " + (item.getGetCountLiked() - 1) + " người thích nội dung này");
                            notifyDataSetChanged();

                        }

                    }
                });

       final ImageView imganh = (ImageView) dialog.findViewById(R.id.imganh);
                imganh.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(v.getContext(), R.style.AppBaseTheme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.news_dialog_status);
                        dialog.show();

                    }

                });


                final RelativeLayout imgnext = (RelativeLayout) dialog.findViewById(R.id.realcm);
                imgnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtcm = (EditText) dialog.findViewById(R.id.edtcm);
                        lvcm = (ListView) dialog.findViewById(R.id.listcm);

                        adaptercm=new comment_adapter(activity, R.layout.comment_dialog_item, arrcomment);
                        lvcm.setAdapter(adaptercm);

                        cm();
                    }

                    public void cm()
                    {
                        String cm=edtcm.getText()+"";
                        comment pb=new comment(cm);
                        arrcomment.add(pb);
                        adaptercm.notifyDataSetChanged();
                    }


                });

            }
        });


        return convertView;
    }

}
