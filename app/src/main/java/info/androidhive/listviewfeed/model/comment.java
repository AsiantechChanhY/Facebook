package info.androidhive.listviewfeed.model;

import android.app.Activity;

/**
 * Created by MyConputer on 6/30/2015.
 */
public class comment extends Activity  {


    private String noidungcm;

    private static final long serialVersionUID = 1L;

    public comment(String noidungcm) {

        this.noidungcm = noidungcm;
    }

    public String getNoidungcm() {
        return noidungcm;
    }
    public void setNoidungcm(String noidungcm) {
        this.noidungcm = noidungcm;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.noidungcm;
    }

}