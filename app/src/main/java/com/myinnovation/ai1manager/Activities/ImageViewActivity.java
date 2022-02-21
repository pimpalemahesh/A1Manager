package com.myinnovation.ai1manager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.myinnovation.ai1manager.R;
import com.myinnovation.ai1manager.databinding.ActivityImageViewBinding;

public class ImageViewActivity extends AppCompatActivity {

    ActivityImageViewBinding binding;
    private String imageUri = " ";
    private String imageName = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        imageUri = intent.getStringExtra("URI");
        imageName = intent.getStringExtra("NAME");

        getSupportActionBar().setTitle(imageName);

        Uri uri = Uri.parse(imageUri);
        binding.image.setImageURI(uri);
    }
}