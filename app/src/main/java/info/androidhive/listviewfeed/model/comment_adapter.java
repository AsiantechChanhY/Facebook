package info.androidhive.listviewfeed.model;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.listviewfeed.R;


/**
 * Created by MyConputer on 6/30/2015.
 */
public class comment_adapter extends ArrayAdapter<comment> {

    Activity context;
    int layoutId;
    ArrayList<comment> arrcomment;

    public comment_adapter(Activity context, int textViewResourceId, ArrayList<comment> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutId = textViewResourceId;
        this.arrcomment = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=context.getLayoutInflater().inflate(layoutId, null);
        TextView txtcm= (TextView) convertView.findViewById(R.id.txttlcmdeeitem);
        comment cm=arrcomment.get(position);
        txtcm.setText(cm.toString());
        return convertView;
    }
}
