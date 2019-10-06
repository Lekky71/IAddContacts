package com.lekai.root.iaddcontacts.ui

import android.app.ActivityManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ShareCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.lekai.root.iaddcontacts.R
import com.lekai.root.iaddcontacts.models.ContactModel
import com.lekai.root.iaddcontacts.ui.adapter.ContactAdapterV2
import com.lekai.root.iaddcontacts.ui.viewModels.ContactViewModel
import com.lekai.root.iaddcontacts.ui.viewModels.ContactViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private lateinit var contactViewModel: ContactViewModel
    private val contactViewModelFactory: ContactViewModelFactory by instance()
    lateinit var mAdapter: ContactAdapterV2
    lateinit var adapter2: ContactAdapterV2
    lateinit var manager2: LayoutManager
    lateinit var adapter3: ContactAdapterV2
    lateinit var manager3: LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "IAddContacts"
        contact_recycler_view.layoutManager = LinearLayoutManager(this)
        mAdapter = ContactAdapterV2(this, 10)
        adapter2 = ContactAdapterV2(this, 10)
        manager2 = LinearLayoutManager(this)
        adapter3 = ContactAdapterV2(this, 10)
        manager3 = LinearLayoutManager(this)
        contact_recycler_view2.layoutManager = manager2
        contact_recycler_view.adapter = mAdapter
        contact_recycler_view2.adapter = adapter2
        contact_recycler_view3.layoutManager = manager3
        contact_recycler_view3.adapter = adapter3
        contactViewModel = ViewModelProviders.of(this,
                contactViewModelFactory).get(ContactViewModel::class.java)
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
                    "save a long list of contacts directly to your phoneEditText at once " + "https://goo.gl/dPZLB6"
            ShareCompat.IntentBuilder.from(this)
                    .setType(mimeType)
                    .setChooserTitle(title)
                    .setText(text)
                    .startChooser()
            return true
        }
        if (id == R.id.action_reset) {
            mAdapter.notifyDataSetChanged()
            adapter2.notifyDataSetChanged()
            adapter3.notifyDataSetChanged()
            contact_recycler_view2.visibility = View.GONE
            contact_recycler_view3.visibility = View.GONE

        }
        if (id == R.id.action_help) {
            startActivity(Intent(this, HelpActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var tDesc: ActivityManager.TaskDescription? = null
            val bm = BitmapFactory.decodeResource(resources, R.mipmap.fineiconphone)
            tDesc = ActivityManager.TaskDescription("IAddContacts", bm, resources.getColor(R.color.colorApp))
            setTaskDescription(tDesc)

        }

    }

    fun addMoreRecycle(view: View) {
        if (contact_recycler_view2.visibility == View.GONE) {
            contact_recycler_view3.visibility = View.VISIBLE
        } else {
            contact_recycler_view3.visibility = View.VISIBLE
            fab.visibility = View.VISIBLE

        }
    }

    private fun addToPhone() {
        val firstArray = mAdapter.allContacts?.toList()
        val secondArray = adapter2.allContacts?.toList()
        val thirdArray = adapter3.allContacts?.toList()
        addContacts(firstArray)
        if (contact_recycler_view2.visibility != View.GONE)
            addContacts(secondArray!!)
        if (contact_recycler_view3.visibility != View.GONE)
            addContacts(thirdArray!!)

    }

    private fun addContacts(firstArray: List<ContactModel?>?) {
        for (contact in firstArray!!) {
            if (contact != null)
                contactViewModel.addContacts(contact)
        }
    }

    fun addAllOfThem(v: View) {
        addToPhone()
        Snackbar.make(add_all_button, "All Contacts saved", Snackbar.LENGTH_SHORT).show()
    }
}
