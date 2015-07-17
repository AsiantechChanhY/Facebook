package info.androidhive.listviewfeed.Messenger;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.data.FeedItem;

/**
 * Created by MyConputer on 7/6/2015.
 */
public class Feedtinnhan_dialog extends ListActivity {

    ArrayList<Message> messages;
    AwesomeAdapter adapter;
    EditText text;
    TextView ten;
    static Random rand = new Random();
    static String sender;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tinnhan_canhan_dialog);

        text = (EditText) this.findViewById(R.id.edtcmtn);

        Intent intent = getIntent();
        FeedItem item = (FeedItem) intent.getSerializableExtra("Item");

        ten = (TextView) findViewById(R.id.txttennguoint);
        ten.setText(item.getName());


//        sender = Utility.sender[rand.nextInt( Utility.sender.length-1)];
        this.setTitle(sender);
        messages = new ArrayList<Message>();

        messages.add(new Message("Hello", false));
        messages.add(new Message("Hi!", true));
        messages.add(new Message("Wassup??", false));
        messages.add(new Message("nothing much, working on speech bubbles.", true));
        messages.add(new Message("you say!", true));
        messages.add(new Message("oh thats great. how are you showing them", false));


        adapter = new AwesomeAdapter(this, messages);
        setListAdapter(adapter);
        addNewMessage(new Message("mmm, well, using 9 patches png to show them.", true));

        final TextView txtDateTime = (TextView) findViewById(R.id.txtthoigianol);
        txtDateTime.setText(CurrentDateTime());

    }
    public void sendMessage(View v)
    {
        String newMessage = text.getText().toString().trim();
        if(newMessage.length() > 0)
        {
            text.setText("");
            addNewMessage(new Message(newMessage, true));
            new SendMessage().execute();
        }
    }
    private class SendMessage extends AsyncTask<Void, String, String>
    {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(2000); //simulate a network call
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

//            this.publishProgress(String.format("%s started writing", sender));
//            try {
//                Thread.sleep(2000); //simulate a network call
//            }catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            this.publishProgress(String.format("...", sender));
            try {
                Thread.sleep(3000);//simulate a network call
            }catch (InterruptedException e) {
                e.printStackTrace();
            }


            return Utility.messages[rand.nextInt(Utility.messages.length-1)];


        }
        @Override
        public void onProgressUpdate(String... v) {

            if(messages.get(messages.size()-1).isStatusMessage)//check wether we have already added a status message
            {
                messages.get(messages.size()-1).setMessage(v[0]); //update the status for that
                adapter.notifyDataSetChanged();
                getListView().setSelection(messages.size()-1);
            }
            else{
                addNewMessage(new Message(true,v[0])); //add new message, if there is no existing status message
            }
        }
        @Override
        protected void onPostExecute(String text) {
            if(messages.get(messages.size()-1).isStatusMessage)//check if there is any status message, now remove it.
            {
                messages.remove(messages.size()-1);
            }

            addNewMessage(new Message(text, false)); // add the orignal message from server.
        }


    }
    void addNewMessage(Message m)
    {
        messages.add(m);
        adapter.notifyDataSetChanged();
        getListView().setSelection(messages.size()-1);
    }

    public static String CurrentDateTime() {
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat timeFormat = new SimpleDateFormat("EEE, d MMM yyyy - K:mm a");
        String s = timeFormat.format(today.getTime());
        return s;
    }



}




