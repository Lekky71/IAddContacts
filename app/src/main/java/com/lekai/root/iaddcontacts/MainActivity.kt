package com.lekai.root.iaddcontacts

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.ShareCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button

import com.lekai.root.iaddcontacts.Adapters.ContactAdapter

import android.support.v7.widget.RecyclerView.LayoutManager

abstract class MainActivity : AppCompatActivity() {
    internal abstract var mRecyclerView: RecyclerView
    internal abstract var mRecyclerView2: RecyclerView
    internal abstract var mRecyclerView3: RecyclerView
    internal abstract var toolbar: Toolbar
    internal abstract var mAdapter: ContactAdapter
    internal abstract var mLayoutManager: LayoutManager
    internal abstract var mContext: Context
    internal var adapter2: ContactAdapter? = null
    internal abstract var manager2: LayoutManager
    internal var adapter3: ContactAdapter? = null
    internal abstract var manager3: LayoutManager
    internal abstract var fab: FloatingActionButton
    internal abstract var addAllAtOnceButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("IAddContacts")
        mRecyclerView = findViewById(R.id.contact_recycler_view) as RecyclerView
        mRecyclerView2 = findViewById(R.id.contact_recycler_view2) as RecyclerView
        mRecyclerView3 = findViewById(R.id.contact_recycler_view3) as RecyclerView
        mContext = baseContext
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = ContactAdapter(this, 10)
        adapter2 = ContactAdapter(this, 10)
        manager2 = LinearLayoutManager(this)
        adapter3 = ContactAdapter(this, 10)
        manager3 = LinearLayoutManager(this)
        mRecyclerView2.layoutManager = manager2
        mRecyclerView.adapter = mAdapter
        mRecyclerView2.adapter = adapter2
        mRecyclerView3.layoutManager = manager3
        mRecyclerView3.adapter = adapter3
        mRecyclerView2.visibility = View.GONE
        mRecyclerView3.visibility = View.GONE
        fab = findViewById(R.id.fab) as FloatingActionButton
        addAllAtOnceButton = findViewById(R.id.add_all_button) as Button
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_contacts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_share) {
            val mimeType = "text/plain"
            val title = "share"
            val text = "Check out this app that helps you " +
                    "save a long list of contacts directly to your phone at once " + "https://goo.gl/dPZLB6"
            ShareCompat.IntentBuilder.from(this)
                    .setType(mimeType)
                    .setChooserTitle(title)
                    .setText(text)
                    .startChooser()
            return true
        }
        if (id == R.id.action_reset) {
            mAdapter?.notifyDataSetChanged()
            adapter2?.notifyDataSetChanged()
            adapter3?.notifyDataSetChanged()
            mRecyclerView2.visibility = View.GONE
            mRecyclerView3.visibility = View.GONE
            fab.visibility = View.VISIBLE
        }
        if (id == R.id.action_help) {
            startActivity(Intent(this, HelpActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var tDesc: ActivityManager.TaskDescription? = null
            val bm = BitmapFactory.decodeResource(resources, R.mipmap.fineiconphone)
            tDesc = ActivityManager.TaskDescription("IAddContacts", bm, resources.getColor(R.color.colorApp))
            setTaskDescription(tDesc)

        }

    }

    fun addMoreRecycle(view: View) {
        if (mRecyclerView2.visibility == View.GONE) {
            mRecyclerView2.visibility = View.VISIBLE
        } else {
            mRecyclerView3.visibility = View.VISIBLE
            fab.visibility = View.GONE

        }
    }

    fun addToPhone() {
        val firstArray = mAdapter.allContacts
        val secondArray = adapter2?.allContacts
        val thirdArray = adapter3?.allContacts
        for (c in firstArray) {
            if (c != null) {
                AddContacts.contactAdd(this, c.name!!, c.phone!!)
            }
        }
        if (mRecyclerView2.visibility != View.GONE) {
            for (c in secondArray!!) {
                if (c != null) {
                    AddContacts.contactAdd(this, c.name!!, c.phone!!)
                }
            }
            if (mRecyclerView3.visibility != View.GONE) {
                for (c in thirdArray!!) {
                    if (c != null) {
                        AddContacts.contactAdd(this, c.name!!, c.phone!!)
                    }
                }
            }
        }

    }

    fun addAllOfThem(v: View) {
        addToPhone()
        Snackbar.make(addAllAtOnceButton, "All Contacts saved", Snackbar.LENGTH_SHORT).show()
    }
}
