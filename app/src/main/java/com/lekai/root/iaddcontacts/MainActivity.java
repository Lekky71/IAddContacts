package com.lekai.root.iaddcontacts;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lekai.root.iaddcontacts.Adapters.ContactAdapter;

import static android.support.v7.widget.RecyclerView.LayoutManager;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    Toolbar toolbar ;
    ContactAdapter mAdapter;
    LayoutManager mLayoutManager;
    Context mContext;
    int MY_PERMISSION_REQUEST_WRITE_CONTACTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.contact_recycler_view);
        mContext = getBaseContext();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactAdapter(this,20);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            String mimeType="text/plain";
            String title ="share";
            String text = "Check out this app that helps you " +
                    "save a long list of contacts directly to your phone at once " + "https://goo.gl/dPZLB6";
            ShareCompat.IntentBuilder.from(this)
                    .setType(mimeType)
                    .setChooserTitle(title)
                    .setText(text)
                    .startChooser();
            return true;
        }
        if(id==R.id.action_reset){
            mAdapter.notifyDataSetChanged();
        }
        if(id==R.id.action_help){
            startActivity(new Intent(this,HelpActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription tDesc = null;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.iadd_contacts_icon);
            tDesc = new ActivityManager.TaskDescription("IAddContacts",bm,getResources().getColor(R.color.colorApp));
            setTaskDescription(tDesc);

        }

    }
}
