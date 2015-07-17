package info.androidhive.listviewfeed.tabs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.TextView;

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
import info.androidhive.listviewfeed.adapter.Feedketban;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

/**
 * Created by MyConputer on 6/30/2015.
 */
public class Request extends Fragment{

        private static final String TAG = Request.class.getSimpleName();
    private QuickReturnListView mListView;
    private TextView mQuickReturnView;
    private int mQuickReturnHeight;

    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private int mState = STATE_ONSCREEN;
    private int mScrollY;
    private int mMinRawY = 0;

    private TranslateAnimation anim;
        private Feedketban listAdapter;
        private List<FeedItem> feedItems;
        private String URL_FEED = "http://api.androidhive.info/feed/feed.json";


        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.request,container,false);

            mQuickReturnView = (TextView) view.findViewById(R.id.footer);


            mListView = (QuickReturnListView) view.findViewById(R.id.listkb);

            feedItems = new ArrayList<FeedItem>();
            listAdapter = new Feedketban(getActivity(), feedItems);
            mListView.setAdapter(listAdapter);

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


        /**
         * Parsing json reponse and passing the data to feed view list adapter
         * */
        private void parseJsonFeed(JSONObject response) {
            try {
                JSONArray feedArray = response.getJSONArray("feed");

                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);

                    FeedItem item = new FeedItem();
                    item.setId(feedObj.getInt("id"));
                    item.setName(feedObj.getString("name"));

//                 Image might be null sometimes
                    String image = feedObj.isNull("image") ? null : feedObj
                            .getString("image");
                    item.setImge(image);
//                item.setStatus(feedObj.getString("status"));
                    item.setProfilePic(feedObj.getString("profilePic"));
                    item.setTimeStamp(feedObj.getString("timeStamp"));

                    // url might be null sometimes
//                String feedUrl = feedObj.isNull("url") ? null : feedObj
//                        .getString("url");
//                item.setUrl(feedUrl);
                    item.setGetCountLiked((int) (Math.random()*100));
                    item.setIsLiked(false);

                    feedItems.add(item);
                }

                // notify data changes to list adapater
                listAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mListView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            mQuickReturnHeight = mQuickReturnView.getHeight();
                            mListView.computeScrollY();
                        }
                    });

            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @SuppressLint("NewApi")
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {

                    mScrollY = 0;
                    int translationY = 0;

                    if (mListView.scrollYIsComputed()) {
                        mScrollY = mListView.getComputedScrollY();
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
                            if (rawY > mQuickReturnHeight) {
                                mState = STATE_OFFSCREEN;
                                mMinRawY = rawY;
                            }
                            translationY = rawY;
                            break;

                        case STATE_RETURNING:

                            translationY = (rawY - mMinRawY) + mQuickReturnHeight;

                            System.out.println(translationY);
                            if (translationY < 0) {
                                translationY = 0;
                                mMinRawY = rawY + mQuickReturnHeight;
                            }

                            if (rawY == 0) {
                                mState = STATE_ONSCREEN;
                                translationY = 0;
                            }

                            if (translationY > mQuickReturnHeight) {
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
                        mQuickReturnView.startAnimation(anim);
                    } else {
                        mQuickReturnView.setTranslationY(translationY);
                    }

                }

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
            });

        }

}

