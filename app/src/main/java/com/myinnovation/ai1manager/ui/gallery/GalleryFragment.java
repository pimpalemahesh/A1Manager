package com.myinnovation.ai1manager.ui.gallery;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.myinnovation.ai1manager.databinding.FragmentGalleryBinding;
import com.myinnovation.ai1manager.ui.Music.MusicAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private ArrayList<File> imageList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        runTimePermissions();

        binding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });

        return binding.getRoot();
    }

    private void runTimePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        new Handler().postDelayed(() -> {
                            disPlayImages();
                            binding.progressBar2.setVisibility(View.INVISIBLE);
                        }, 2000);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void disPlayImages() {
        binding.imagerclv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.imagerclv.setHasFixedSize(true);
        imageList = new ArrayList<>();
        imageList = findImages(Environment.getExternalStorageDirectory());
        binding.imagerclv.setAdapter(new ImageAdapter(imageList, getContext()));
    }

    public ArrayList<File> findImages(File file){
        ArrayList<File> list = new ArrayList<>();
        File[] files = file.listFiles();

        try{
            for(File singleFile : files){
                if(singleFile.isDirectory() && !singleFile.isHidden()){
                    list.addAll(findImages(singleFile));
                }
                else{
                    if(singleFile.getName().endsWith(".jpg") || singleFile.getName().endsWith(".png") || singleFile.getName().endsWith(".jpeg")){
                        list.add(singleFile);
                    }
                }
            }
        } catch (Exception e){
            Toast.makeText(getActivity(), "Empty Files", Toast.LENGTH_SHORT).show();
        }

        return list;
    }

    private void search(String str) {
        ArrayList<File> arrayList = new ArrayList<>();
        for (File singleFile : imageList) {
            if (singleFile.getName().toLowerCase().contains(str.toLowerCase())) {
                arrayList.add(singleFile);
            }
        }

        MusicAdapter ad = new MusicAdapter(arrayList, getContext());
        binding.imagerclv.setAdapter(ad);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}