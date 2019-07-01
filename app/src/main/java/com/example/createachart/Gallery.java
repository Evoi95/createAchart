package com.example.createachart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class Gallery extends AppCompatActivity {

    ArrayList<File> list;
    GridView gridView;
    File file;
    boolean success = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = (GridView) findViewById(R.id.image_grid);
        // folder name

        File folder = new File(Environment.getExternalStorageDirectory() +
                "/" + Environment.DIRECTORY_DCIM + "/" + "ChartGallery");

        if (!folder.exists())
            {
            success = folder.mkdirs();
            }

        if (success)
            {
            file = new File(String.valueOf(folder));
;           }
        else
            {
            File folder2 = new File(Environment.getExternalStorageDirectory() +
                        "/" + Environment.DIRECTORY_DCIM + "/" + "ChartGallery");
            file = new File(String.valueOf(folder2));
            }

        list = imageReader(this.file);

        gridView.setAdapter(new gridAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                openActivity(i);
                }
            });
    }

    public class gridAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View convertView = null;

            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.row_layout,viewGroup,false);
                ImageView myImage = (ImageView) convertView.findViewById(R.id.myImage);
                myImage.setImageURI(Uri.parse(list.get(i).toString()));

            }
            return convertView;
        }
    }


    private ArrayList<File> imageReader(File exstarnalStorageDirectory)
    {
        ArrayList<File> b = new ArrayList<>();
        File[] files = exstarnalStorageDirectory.listFiles();
        for (int i =0; i<files.length;i++)
        {
            if (files[i].isDirectory() )
            {
                b.addAll(imageReader(files[i]));
            }
            else
            {
                if (files[i].getName().endsWith(".jpg"))
                {
                    b.add(files[i]);
                }
            }
        }

        return b;
    }

    private void openActivity(int i) {
        Intent intent = new Intent(this, Full_Image_Activity.class);
        intent.putExtra("img",list.get(i).toString());
        startActivity(intent);
    }
}
