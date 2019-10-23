package com.lekai.root.iaddcontacts.ui

import android.app.ActivityManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import com.lekai.root.iaddcontacts.R
import com.lekai.root.iaddcontacts.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_help)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.help_screen_title)
        binding.included.helpMessage.text = getString(R.string.help_text)
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
