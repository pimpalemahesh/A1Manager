package com.myinnovation.ai1manager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;

import com.myinnovation.ai1manager.databinding.ActivityVideoViewBinding;
import com.myinnovation.ai1manager.ui.Video.VideoAdapter;

public class VideoViewActivity extends AppCompatActivity {

    ActivityVideoViewBinding binding;
    MediaController mediaController;
    private String VideoName = "";
    private String path = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        VideoName = intent.getStringExtra("NAME");
        path = intent.getStringExtra("PATH");

        getSupportActionBar().setTitle(VideoName);
        Uri uri = Uri.parse(path);
        mediaController = new MediaController(this);
        if(path != null && !uri.equals("")){
            binding.video.setMediaController(mediaController);
            binding.video.setVideoURI(uri);
            mediaController.show();
        } else{
            Toast.makeText(this, "Parsing Error!", Toast.LENGTH_LONG).show();
        }

    }
}