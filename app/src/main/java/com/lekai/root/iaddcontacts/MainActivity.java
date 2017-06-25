package com.lekai.root.iaddcontacts;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lekai.root.iaddcontacts.Adapters.ContactAdapter;

import static android.support.v7.widget.RecyclerView.LayoutManager;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView mRecyclerView3;
    Toolbar toolbar ;
    ContactAdapter mAdapter;
    LayoutManager mLayoutManager;
    Context mContext;
    ContactAdapter adapter2;
    LayoutManager manager2;
    ContactAdapter adapter3;
    LayoutManager manager3;
    FloatingActionButton fab ;
    Button addAllAtOnceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("IAddContacts");
        mRecyclerView = (RecyclerView) findViewById(R.id.contact_recycler_view);
        mRecyclerView2 = (RecyclerView) findViewById(R.id.contact_recycler_view2);
        mRecyclerView3 = (RecyclerView) findViewById(R.id.contact_recycler_view3);
        mContext = getBaseContext();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactAdapter(this,10);
        adapter2 = new ContactAdapter(this,10);
        manager2 = new LinearLayoutManager(this);
        adapter3 = new ContactAdapter(this,10);
        manager3 = new LinearLayoutManager(this);
        mRecyclerView2.setLayoutManager(manager2);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView2.setAdapter(adapter2);
        mRecyclerView3.setLayoutManager(manager3);
        mRecyclerView3.setAdapter(adapter3);
        mRecyclerView2.setVisibility(View.GONE);
        mRecyclerView3.setVisibility(View.GONE);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        addAllAtOnceButton = (Button) findViewById(R.id.add_all_button);
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
            adapter2.notifyDataSetChanged();
            adapter3.notifyDataSetChanged();
            mRecyclerView2.setVisibility(View.GONE);
            mRecyclerView3.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
        if(id==R.id.action_help){
            startActivity(new Intent(this,HelpActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityManager.TaskDescription tDesc = null;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.fineiconphone);
            tDesc = new ActivityManager.TaskDescription("IAddContacts",bm,getResources().getColor(R.color.colorApp));
            setTaskDescription(tDesc);

        }

    }
    public void addMoreRecycle(View view){
        if(mRecyclerView2.getVisibility()==View.GONE){
            mRecyclerView2.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView3.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);

        }
    }
    public void addToPhone(){
        ContactModel[] firstArray = mAdapter.getAllContacts();
        ContactModel[] secondArray = adapter2.getAllContacts();
        ContactModel[] thirdArray = adapter3.getAllContacts();
        for(ContactModel c : firstArray){
            if(c != null){
                AddContacts.contactAdd(this,c.getName(),c.getPhone());
            }
        }
        if(mRecyclerView2.getVisibility() != View.GONE){
            for(ContactModel c : secondArray){
                if(c != null){
                    AddContacts.contactAdd(this,c.getName(),c.getPhone());
                }
            }
            if(mRecyclerView3.getVisibility() != View.GONE){
                for(ContactModel c : thirdArray){
                    if(c != null){
                        AddContacts.contactAdd(this,c.getName(),c.getPhone());
                    }
                }
            }
        }

    }
    public void addAllOfThem(View v){
        addToPhone();
        Snackbar.make(addAllAtOnceButton, "All Contacts saved", Snackbar.LENGTH_SHORT).show();
    }
}
