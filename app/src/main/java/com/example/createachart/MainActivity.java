package com.example.createachart;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
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

import com.github.mikephil.charting.data.Entry;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{


    public static  ArrayList<Entry> DataE;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            final Button openGallery = findViewById(R.id.gallery_button);
            openGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        openActivity(false,false,"Gallery");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });

            Button createLineChart = findViewById(R.id.Create_Line_Chart);
            createLineChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    try {
                        openActivity(false,true,"LineChart_Activity");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });

            Button createBarChart = findViewById(R.id.BarChartButton);
            createBarChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    try {
                        openActivity(true,false,"BarChart_Activity");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
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


    private void openActivity(boolean B, boolean L,String activity) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Intent intent = new Intent();
        String myString = "com.example.createachart."+activity;

        Class<?> myClass;
        myClass = Class.forName(myString);
        Activity obj = (Activity) myClass.newInstance();
        boolean x = false;
        intent = new Intent(this, myClass);
        x = true;

        if (B)
        {
            ArrayList<Integer> Data = data();
            intent.putExtra("dataVal",Data);
            x = true;
        }
        else if(L)
        {
            ArrayList<Entry> DataE = data2();;
            x = true;
        }
        if (x)
        {
            startActivity(intent);
        }
        else
        {
            openActivity(B,L,activity);
        }
    }


    private ArrayList<Integer> data()
    {
        ArrayList<Integer> valList = new ArrayList<Integer>();
        for (int i = 0; i<15; i++)
        {
            valList.add(i+1);
        }
        return valList;
    }
    private ArrayList<Entry> data2() {

        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        for (int i=0;i<15; i++)
        {
            dataVals.add(new Entry(i, i+1));
        }
        return dataVals;
    }

}






