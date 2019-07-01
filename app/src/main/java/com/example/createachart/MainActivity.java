package com.example.createachart;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Button openGallery = findViewById(R.id.gallery_button);

            openGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity();
                }
            });

        }
        else{
            if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i("ERR","NO PERMISION");
            }
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }


    private void openActivity() {
        Intent intent = new Intent(this, Gallery.class);
        startActivity(intent);
    }


}






