package com.myinnovation.ai1manager.ui.PDF;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.myinnovation.ai1manager.databinding.FragmentPdfBinding;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PDFFragment extends Fragment {

    private FragmentPdfBinding binding;
    private ArrayList<File> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPdfBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        runTimePermissions();
        new Handler().postDelayed(() -> {
            binding.progressBar3.setVisibility(View.INVISIBLE);
        }, 1000);

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
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                disPlayPdfs();
                            }
                        }, 3000);

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

    private void disPlayPdfs() {
        binding.pdfrclv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.pdfrclv.setHasFixedSize(true);
        list = new ArrayList<>();
        list.addAll(findPdf(Environment.getExternalStorageDirectory()));
        binding.pdfrclv.setAdapter(new pdfAdapter(list, getContext()));
    }

    public ArrayList<File> findPdf(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        try{
            for(File singleFile : files){
                if(singleFile.isDirectory() && !singleFile.isHidden()){
                    arrayList.addAll(findPdf(singleFile));
                }
                else{
                    if(singleFile.getName().endsWith(".pdf")){
                        arrayList.add(singleFile);
                    }
                }
            }
        } catch (Exception e){
            Toast.makeText(getActivity(), "Empty Files", Toast.LENGTH_SHORT).show();
        }
        return arrayList;
    }

    private void search(String str) {
        ArrayList<File> arrayList = new ArrayList<>();
        for (File singleFile : list) {
            if (singleFile.getName().toLowerCase().contains(str.toLowerCase())) {
                arrayList.add(singleFile);
            }
        }

        pdfAdapter ad = new pdfAdapter(arrayList, getContext());
        binding.pdfrclv.setAdapter(ad);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}