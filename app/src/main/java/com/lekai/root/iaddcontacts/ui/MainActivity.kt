package com.lekai.root.iaddcontacts.ui

import android.app.ActivityManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lekai.root.iaddcontacts.R
import com.lekai.root.iaddcontacts.models.ContactModel
import com.lekai.root.iaddcontacts.ui.adapter.ContactAdapter
import com.lekai.root.iaddcontacts.ui.viewModels.ContactViewModel
import com.lekai.root.iaddcontacts.ui.viewModels.ContactViewModelFactory
import com.lekai.root.iaddcontacts.util.CsvUtils
import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvMalformedLineException
import kotlinx.android.synthetic.main.activity_main.addContactsButton
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.importContactsButton
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.contact_recycler_view
import kotlinx.android.synthetic.main.content_main.contact_recycler_view2
import kotlinx.android.synthetic.main.content_main.contact_recycler_view3
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.io.FileReader
import java.lang.Exception


class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private lateinit var contactViewModel: ContactViewModel
    private val contactViewModelFactory: ContactViewModelFactory by instance()
    private lateinit var mAdapter: ContactAdapter
    lateinit var adapter2: ContactAdapter
    lateinit var manager2: LayoutManager
    lateinit var adapter3: ContactAdapter
    lateinit var manager3: LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "IAddContacts"
        contact_recycler_view.layoutManager = LinearLayoutManager(this)
        mAdapter = ContactAdapter(this, 10)
        adapter2 = ContactAdapter(this, 10)
        manager2 = LinearLayoutManager(this)
        adapter3 = ContactAdapter(this, 10)
        manager3 = LinearLayoutManager(this)
        contact_recycler_view2.layoutManager = manager2
        contact_recycler_view.adapter = mAdapter
        contact_recycler_view2.adapter = adapter2
        contact_recycler_view3.layoutManager = manager3
        contact_recycler_view3.adapter = adapter3
        contactViewModel = ViewModelProviders.of(this,
                contactViewModelFactory).get(ContactViewModel::class.java)

        contactViewModel.contactsObservable.observe(this, Observer { addContacts(it) })
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
            fab.visibility = View.VISIBLE

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
            fab.visibility = View.INVISIBLE

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
            if (contact != null) contactViewModel.addContacts(contact)
        }
        Snackbar.make(addContactsButton, "All Contacts saved", Snackbar.LENGTH_SHORT).show()
    }

    private fun openFileChooser() {
        val uri = Uri.parse(Environment.getExternalStorageDirectory().path)
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            setDataAndType(uri, "text/*")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivityForResult(Intent.createChooser(intent, "Open folder"), REQUEST_CSV_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CSV_CODE) {
            val uri: Uri? = data?.data
            if (uri != null) {
                contactViewModel.importContacts(uri) {
                    Snackbar.make(importContactsButton, "Error to read your CSV file.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Ok") { }
                            .show()
                }
            }
        }
    }

    fun addAllOfThem(v: View) = addToPhone()

    fun importContactsFromCsv(v: View) = openFileChooser()

    companion object {
        const val REQUEST_CSV_CODE = 100
    }

}
