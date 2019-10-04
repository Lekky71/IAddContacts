package com.lekai.root.iaddcontacts;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Splashscreen extends AppCompatActivity {
    private int secs = 3000;
    final int MY_PERMISSION_REQUEST_WRITE_CONTACTS = 100;
    TextView begin_work;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        begin_work = (TextView) findViewById(R.id.start_button);
        begin_work.setBackgroundColor(0);
        begin_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUse();
            }
        });
    }

    public void askForContactPermission(Intent in){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(Splashscreen.this, Manifest.permission.WRITE_CONTACTS);
            if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Splashscreen.this,new String[]{Manifest.permission.WRITE_CONTACTS},MY_PERMISSION_REQUEST_WRITE_CONTACTS);

//                if(ActivityCompat.shouldShowRequestPermissionRationale(Splashscreen.this, android.Manifest.permission.WRITE_CONTACTS));
            }else{
                startActivity(in);
                finish();
            }
        }
        else{
            startActivity(in);
            finish();
        }
    }

    public void startUse(){
        askForContactPermission(new Intent(Splashscreen.this,MainActivity.class));

//        else{
//            ActivityCompat.requestPermissions(Splashscreen.this,new String[]{android.Manifest.permission.WRITE_CONTACTS},MY_PERMISSION_REQUEST_WRITE_CONTACTS);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_WRITE_CONTACTS:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Splashscreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(),"Sorry, This app cannot work on your device",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this,MainActivity.class));
                    finish();
                }
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription tDesc = null;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.fineiconphone);
            tDesc = new ActivityManager.TaskDescription("IAddContacts",bm,getResources().getColor(R.color.colorApp));
            setTaskDescription(tDesc);

        }

    }
}
