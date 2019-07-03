package com.example.createachart;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class Full_Image_Activity extends AppCompatActivity {

    ImageView fullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__image_);

        fullImage = (ImageView) findViewById(R.id.full_image);

        String data = getIntent().getExtras().getString("png");

        fullImage.setImageURI(Uri.parse(data));
    }
}
