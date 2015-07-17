package info.androidhive.listviewfeed.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.listviewfeed.R;

/**
 * Created by MyConputer on 7/11/2015.
 */
public class Messenger extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.messenger, container, false);

        return v;
    }
}
