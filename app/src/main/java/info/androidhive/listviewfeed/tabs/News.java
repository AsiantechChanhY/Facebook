package info.androidhive.listviewfeed.tabs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.listviewfeed.Quickreturn.QuickReturnListView;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.adapter.FeedListAdapter;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

/**
 * Created by MyConputer on 6/30/2015.
 */
public class News extends Fragment {

    /**
     * Created by MyConputer on 6/22/2015.
     */

    private QuickReturnListView mListViewnews;
    private LinearLayout mQuickReturnViewnews;
    private int mQuickReturnHeightnews;

    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;
    private TranslateAnimation anim;

    private RelativeLayout  real1;


    private static final String TAG = News.class.getSimpleName();
        private FeedListAdapter listAdapter;
        private List<FeedItem> feedItems;
        private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

        private View  mHeader;

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.news,container,false);

            real1 = (RelativeLayout) view.findViewById(R.id.real1);
            real1();

            mQuickReturnViewnews = (LinearLayout) view.findViewById(R.id.linearquicknews);

            mListViewnews = (QuickReturnListView) view.findViewById(R.id.list);

            mHeader = inflater.inflate(R.layout.new_header, null);
            mListViewnews.addHeaderView(mHeader);

            feedItems = new ArrayList<FeedItem>();
            listAdapter = new FeedListAdapter(getActivity(), feedItems);
            mListViewnews.setAdapter(listAdapter);

            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(URL_FEED);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                        parseJsonFeed(new JSONObject(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } else {
                // making fresh volley request and getting json
                JsonObjectRequest jsonReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                        URL_FEED, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG, "Response: " + response.toString());
                        if (response != null) {
                            parseJsonFeed(response);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });

                // Adding request to volley request queue
                AppController.getInstance().addToRequestQueue(jsonReq);
            }
            return  view;
        }

        private void parseJsonFeed(JSONObject response) {
            try {
                JSONArray feedArray = response.getJSONArray("feed");

                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);

                    FeedItem item = new FeedItem();
                    item.setId(feedObj.getInt("id"));
                    item.setName(feedObj.getString("name"));

                    // Image might be null sometimes
                    String image = feedObj.isNull("image") ? null : feedObj
                            .getString("image");
                    item.setImge(image);
                    item.setStatus(feedObj.getString("status"));
                    item.setProfilePic(feedObj.getString("profilePic"));
                    item.setTimeStamp(feedObj.getString("timeStamp"));

                    // url might be null sometimes
                    String feedUrl = feedObj.isNull("url") ? null : feedObj.getString("url");
                    item.setUrl(feedUrl);
                    item.setGetCountLiked((int) (Math.random()*100));
                    item.setIsLiked(false);

                    feedItems.add(item);
                }

                // notify data changes to list adapater
                listAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mListViewnews.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            mQuickReturnHeightnews = mQuickReturnViewnews.getHeight();
                            mListViewnews.computeScrollY();
                        }
                    });

            mListViewnews.setOnScrollListener(new AbsListView.OnScrollListener() {
                @SuppressLint("NewApi")
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {

                    mScrollY = 0;
                    int translationY = 0;

                    if (mListViewnews.scrollYIsComputed()) {
                        mScrollY = mListViewnews.getComputedScrollY();
                    }

                    int rawY = mScrollY;

                    switch (mState) {
                        case STATE_OFFSCREEN:
                            if (rawY >= mMinRawY) {
                                mMinRawY = rawY;
                            } else {
                                mState = STATE_RETURNING;
                            }
                            translationY = rawY;
                            break;

                        case STATE_ONSCREEN:
                            if (rawY > mQuickReturnHeightnews) {
                                mState = STATE_OFFSCREEN;
                                mMinRawY = rawY;
                            }
                            translationY = rawY;
                            break;

                        case STATE_RETURNING:

                            translationY = (rawY - mMinRawY) + mQuickReturnHeightnews;

                            System.out.println(translationY);
                            if (translationY < 0) {
                                translationY = 0;
                                mMinRawY = rawY + mQuickReturnHeightnews;
                            }

                            if (rawY == 0) {
                                mState = STATE_ONSCREEN;
                                translationY = 0;
                            }

                            if (translationY > mQuickReturnHeightnews) {
                                mState = STATE_OFFSCREEN;
                                mMinRawY = rawY;
                            }
                            break;
                    }

                    /** this can be used if the build is below honeycomb **/
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                        anim = new TranslateAnimation(0, 0, translationY,
                                translationY);
                        anim.setFillAfter(true);
                        anim.setDuration(0);
                        mQuickReturnViewnews.startAnimation(anim);
                    } else {
                        mQuickReturnViewnews.setTranslationY(translationY);
                    }

                }

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
            });


        }

    public void real1()
    {
        real1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext(), R.style.AppBaseTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.news_dialog_status);
                dialog.show();

                final RelativeLayout toi = (RelativeLayout) dialog.findViewById(R.id.toi);
                toi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(v.getContext(), R.style.AppBaseTheme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.news_dialog_status_toi);
                        dialog.show();

                        final ImageView backcuatoi = (ImageView) dialog.findViewById(R.id.dialog_face_toi);
                        backcuatoi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Dialog dialog = new Dialog(v.getContext(), R.style.AppBaseTheme);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.news_dialog_status);
                                dialog.show();
                            }
                        });
                    }
                });


            }
        });
    }




}
