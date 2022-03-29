package com.myinnovation.ai1manager.ui.Video;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.myinnovation.ai1manager.databinding.FragmentMusicBinding;
import com.myinnovation.ai1manager.databinding.FragmentVideoBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding;
    private ArrayList<File> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVideoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
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


        return view;
    }

    private void runTimePermissions() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        disPlayVideos();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    void disPlayVideos(){
        binding.videorclv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.videorclv.setHasFixedSize(true);
        list = new ArrayList<>();
        list = findSong(Environment.getExternalStorageDirectory());
        binding.videorclv.setAdapter(new VideoAdapter(list, getContext()));


    }

    public ArrayList<File> findSong(File file){
        ArrayList<File> list = new ArrayList<>();
        File[] files = file.listFiles();

        try{
            for(File singleFile : files){
                if(singleFile.isDirectory() && !singleFile.isHidden()){
                    list.addAll(findSong(singleFile));
                }
                else{
                    if(singleFile.getName().endsWith(".mp4")){
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
        for (File singleFile : list) {
            if (singleFile.getName().toLowerCase().contains(str.toLowerCase())) {
                arrayList.add(singleFile);
            }
        }

        VideoAdapter ad = new VideoAdapter(arrayList, getContext());
        binding.videorclv.setAdapter(ad);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}