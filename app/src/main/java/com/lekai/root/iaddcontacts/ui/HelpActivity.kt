package com.lekai.root.iaddcontacts.ui

import android.app.ActivityManager
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.app.NavUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.lekai.root.iaddcontacts.R

class HelpActivity : AppCompatActivity() {
    internal var text = "This app lets you add a long list of contacts to your phoneEditText directly. " +
            "All you have to do is just type in the names and phoneEditText numbers and click the ADD ALL BUTTON. \n \n" +
            "Click on the RESET button to clear the inputs \n\n" +
            "To add more input, click on the down arrow button \n\n " +
            "Kindly hit the " +
            "share button to share this useful app with your friends"
    lateinit var helpTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "Welcome to IAddContacts"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Help")
        helpTextView = findViewById(R.id.help_message)
        helpTextView.text = text
    }

    override fun onResume() {
        super.onResume()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            var tDesc: ActivityManager.TaskDescription? = null
            val bm = BitmapFactory.decodeResource(resources, R.mipmap.fineiconphone)
            tDesc = ActivityManager.TaskDescription("IAddContacts", bm, resources.getColor(R.color.colorApp))
            setTaskDescription(tDesc)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

}
