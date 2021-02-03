package com.shankar.imagerecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView imageRecycler;
    LinearLayoutManager imageLinearLayoutManager;
    Button openButton;
    ImagesAdapter imagesAdapter;
    ArrayList<Uri> imageList;
    List<Image> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageList = new ArrayList<>();

        imageRecycler = findViewById(R.id.imageRecycler);

        imageLinearLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.HORIZONTAL, false);

        imageRecycler.setLayoutManager(imageLinearLayoutManager);

        imagesAdapter = new ImagesAdapter(imageList, imageRecycler, imageLinearLayoutManager);

        imageRecycler.setAdapter(imagesAdapter);

        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(imageRecycler);
        openButton = findViewById(R.id.openButton);

        openButton.setOnClickListener(view ->
        {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

            } else {

                    ImagePicker.create(this) // Activity or Fragment
                        .limit(10)
                        .start();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            images = ImagePicker.getImages(data);


            imageList.clear();
            for (int i = 0; i < images.size(); i++) {
                imageList.add(images.get(i).getUri());

            }
            imagesAdapter.notifyDataSetChanged();
        }

    }

}