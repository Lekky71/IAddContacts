package com.lekai.root.iaddcontacts.ui

import android.Manifest
import android.app.ActivityManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lekai.root.iaddcontacts.R

class Splashscreen : AppCompatActivity() {
    private val secs = 3000
    private val MY_PERMISSION_REQUEST_WRITE_CONTACTS = 100
   lateinit var begin_work: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        begin_work = findViewById<View>(R.id.start_button) as TextView
        begin_work.setBackgroundColor(0)
        begin_work.setOnClickListener { startUse() }
    }

    private fun askForContactPermission(`in`: Intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val permissionCheck = ContextCompat.checkSelfPermission(this@Splashscreen, Manifest.permission.WRITE_CONTACTS)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@Splashscreen, arrayOf(Manifest.permission.WRITE_CONTACTS), MY_PERMISSION_REQUEST_WRITE_CONTACTS)

                //                if(ActivityCompat.shouldShowRequestPermissionRationale(Splashscreen.this, android.Manifest.permission.WRITE_CONTACTS));
            } else {
                startActivity(`in`)
                finish()
            }
        } else {
            startActivity(`in`)
            finish()
        }
    }

    private fun startUse() {
        askForContactPermission(Intent(this@Splashscreen, MainActivity::class.java))

        //        else{
        //            ActivityCompat.requestPermissions(Splashscreen.this,new String[]{android.Manifest.permission.WRITE_CONTACTS},MY_PERMISSION_REQUEST_WRITE_CONTACTS);
        //        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSION_REQUEST_WRITE_CONTACTS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(this@Splashscreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, "Sorry, This app cannot work on your device", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                return
            }
        }
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
}
