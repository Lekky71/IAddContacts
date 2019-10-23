package com.lekai.root.iaddcontacts.ui

import android.app.ActivityManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lekai.root.iaddcontacts.R
import com.lekai.root.iaddcontacts.databinding.ActivityMainBinding
import com.lekai.root.iaddcontacts.models.ContactModel
import com.lekai.root.iaddcontacts.ui.adapter.ContactAdapter
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
    private lateinit var binding: ActivityMainBinding

    private val contactViewModelFactory: ContactViewModelFactory by instance()

    private lateinit var adapter: ContactAdapter
    private lateinit var adapter2: ContactAdapter
    private lateinit var adapter3: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        contactViewModel = ViewModelProviders.of(this,
                contactViewModelFactory).get(ContactViewModel::class.java)

        setupViews()
    }

    private fun setupViews() {
        supportActionBar!!.title = getString(R.string.app_name)

        //contactRecyclerView
        binding.included.contactRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(this, 10)
        binding.included.contactRecyclerView.adapter = adapter

        //contactRecyclerView2
        binding.included.contactRecyclerView2.layoutManager = LinearLayoutManager(this)
        adapter2 = ContactAdapter(this, 10)
        binding.included.contactRecyclerView2.adapter = adapter2

        //contactRecyclerView3
        binding.included.contactRecyclerView3.layoutManager = LinearLayoutManager(this)
        adapter3 = ContactAdapter(this, 10)
        binding.included.contactRecyclerView3.adapter = adapter3
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
            adapter.notifyDataSetChanged()
            adapter2.notifyDataSetChanged()
            adapter3.notifyDataSetChanged()
            contact_recycler_view2.visibility = View.GONE
            contact_recycler_view3.visibility = View.GONE
            binding.fab.visibility = View.VISIBLE

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
            contact_recycler_view2.visibility = View.VISIBLE
        } else {
            contact_recycler_view3.visibility = View.VISIBLE
            binding.fab.visibility = View.INVISIBLE

        }
    }

    private fun addToPhone() {
        val firstArray = adapter.allContacts?.toList()
        val secondArray = adapter2.allContacts?.toList()
        val thirdArray = adapter3.allContacts?.toList()
        addContacts(firstArray)
        if (binding.included.contactRecyclerView2.visibility != View.GONE)
            addContacts(secondArray!!)
        if (binding.included.contactRecyclerView3.visibility != View.GONE)
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
