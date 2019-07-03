package com.example.createachart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import java.util.Calendar;

public class BarChart_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BarChart mpBarChart = findViewById(R.id.mp_BarChart);
        
        setSupportActionBar(toolbar);
        ArrayList<Integer> listInteger = getIntent().getIntegerArrayListExtra("dataVal");


        mpBarChart = createBarChar(mpBarChart, listInteger);
        final BarChart finalMpBarChart = mpBarChart;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    saveImage(finalMpBarChart, "provaBarChat");
                    Snackbar.make(view, "Chart saved in your gallery", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Log.i("ERR", "NO PERMISION");
                    }
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });
    }

    private ArrayList<BarEntry> convertData (ArrayList<Integer> arrayEntry) {

        ArrayList<BarEntry> dataVals = new ArrayList<>();
        for(int i=0; i<arrayEntry.size(); i++)
        {
            dataVals.add(new BarEntry(i+1,arrayEntry.get(i)));
        }

        return dataVals;
    }
    private BarChart createBarChar(BarChart bChart, ArrayList<Integer> entryVall) {

        // Cero e imposto il set di dati che inserisco nel grafico a barre
        ArrayList<BarEntry> dataVal1 = convertData(entryVall);
        BarDataSet dataBarSet1 = new BarDataSet(dataVal1, "Val1");

        // imposto il colore
        dataBarSet1.setColor(Color.BLUE);


        BarData bar_Data = new BarData(); //creso il data Base per i dati
        bar_Data.addDataSet(dataBarSet1); //aggiungo il set di dati a data base

        bChart.setData(bar_Data); //aggiungo il database
        bChart.invalidate(); // chiudo il grafino così che non posso apportare modiche
        // bChart.animateX(40);

        return bChart; // restituisco il grafivo
    }


    private void saveImage(BarChart chart, String image_name) {

        //FUNZIONA NON CHIEDETE COME
        //so che creo un oggetto bitmap dove salvare i dati del grafico
        //ingresso gli ho messo il brafico che va salvato
        // e una stringa che dovrà essere poi il nome dell'argomento di cui faccianmo il grafico
        // se il file esiste gia NON LO SOVRASCRIVE
        // QUINDI TOCCA METTERE UN IF EXSIST DELETE

        Bitmap finalBitmap;

        int width = chart.getWidth();
        int height = chart.getHeight();
        Bitmap cBitmap = chart.getChartBitmap();
        finalBitmap = Bitmap.createScaledBitmap(cBitmap, width, height, true);

        // For save file in internal directpory
        String path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/" + "ChartGallery"+"/" ;
        String fname = "Bar chart of-" + image_name + ".png";
        File file = new File(path, fname);
        Log.i("LOAD", path + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            // Funzione che crea l'iimmagine
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
