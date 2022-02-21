package com.myinnovation.ai1manager.ui.Music;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.myinnovation.ai1manager.R;
import com.myinnovation.ai1manager.databinding.FragmentMusicBinding;
import com.myinnovation.ai1manager.ui.PDF.pdfAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {

    private FragmentMusicBinding binding;
    private ArrayList<File> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMusicBinding.inflate(inflater, container, false);
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
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        new Handler().postDelayed(() -> {
                            binding.progressBar2.setVisibility(View.INVISIBLE);
                            disPlaySongs();
                        }, 1000);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    void disPlaySongs(){
        binding.songrclv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.songrclv.setHasFixedSize(true);
        list = new ArrayList<>();
        list = findSong(Environment.getExternalStorageDirectory());
        binding.songrclv.setAdapter(new MusicAdapter(list, getContext()));


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
                    if(singleFile.getName().contains("Call@")){
                    }
                    else if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
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

        MusicAdapter ad = new MusicAdapter(arrayList, getContext());
        binding.songrclv.setAdapter(ad);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}