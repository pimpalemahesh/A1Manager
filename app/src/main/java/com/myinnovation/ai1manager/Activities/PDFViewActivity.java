package com.myinnovation.ai1manager.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.myinnovation.ai1manager.R;

import java.io.File;

public class PDFViewActivity extends AppCompatActivity {

    private String filepath = " ";
    private String fileName = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        filepath = getIntent().getStringExtra("path");
        fileName = getIntent().getStringExtra("name");

        getSupportActionBar().setTitle(fileName);

        PDFView pdfView = findViewById(R.id.pdfView);


        File file = new File(filepath);

        pdfView.fromFile(file)
                .load();
    }
}