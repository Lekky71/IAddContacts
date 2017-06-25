package com.lekai.root.iaddcontacts;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    String text = "This app lets you add a long list of contacts to your phone directly. " +
            "All you have to do is just type in the names and phone numbers and click the ADD ALL BUTTON. \n \n" +
            "Click on the RESET button to clear the inputs \n\n" +
            "To add more input, click on the down arrow button \n\n " +
            "Kindly hit the " +
            "share button to share this useful app with your friends";
    TextView helpTextView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome to IAddContacts");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Help");
        helpTextView = (TextView) findViewById(R.id.help_message);
        helpTextView.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription tDesc = null;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.fineiconphone);
            tDesc = new ActivityManager.TaskDescription("IAddContacts",bm,getResources().getColor(R.color.colorApp));
            setTaskDescription(tDesc);

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

}
