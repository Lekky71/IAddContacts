package com.lekai.root.iaddcontacts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    String text = "This app lets you add a long list of contacts to your phone directly. " +
            "All you have to do is just type in the names and phone numbers and click the ADD BUTTON on the right. Kindly hit the " +
            "share button to share this useful app with your friends";
    TextView helpTextView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome to IAddContacts");
        setSupportActionBar(toolbar);
        helpTextView = (TextView) findViewById(R.id.help_message);
        helpTextView.setText(text);
    }

}
