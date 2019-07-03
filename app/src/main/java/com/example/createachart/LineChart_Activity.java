package com.example.createachart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class LineChart_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Entry> listEntry = MainActivity.DataE;
        final LineChart mpLineChart = createLineChart(listEntry);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveImage(mpLineChart, "prova2");
                    Snackbar.make(view, "Chart saved in your gallery", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Log.i("ERR", "NO PERMISION");
                    }
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });
    }
    private LineChart createLineChart(ArrayList<Entry> dataVal1) {

        LineDataSet lineDataSet1 = new LineDataSet(dataVal1,"data"); //dataVal1, "Data Set 1");

        final LineChart mpLineChart;
        mpLineChart = findViewById(R.id.mp_Line_Chart);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        return mpLineChart;
    }

    private void saveImage(LineChart chart, String image_name)
    {

        // Permission is not granted

        Bitmap finalBitmap;
        int width = chart.getWidth();
        int height = chart.getHeight();
        Bitmap cBitmap = chart.getChartBitmap();
        finalBitmap = Bitmap.createScaledBitmap(cBitmap, width, height, true);
        //File path =  getApplicationContext().getFilesDir();
        // For save file in internal directpory
        String path = Environment.getExternalStorageDirectory()+"/"+ Environment.DIRECTORY_DCIM+"/";
        String fname = "Image-" + image_name + ".png";
        File file = new File(path, fname);
        Log.i("LOAD", path + fname);
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}

