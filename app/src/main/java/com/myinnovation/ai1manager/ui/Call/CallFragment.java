package com.myinnovation.ai1manager.ui.Call;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.myinnovation.ai1manager.databinding.FragmentCallBinding;

import java.util.ArrayList;
import java.util.List;

public class CallFragment extends Fragment {

    private FragmentCallBinding binding;
    ArrayList<String> nameList;
    ArrayList<String> numberList;
    ContactAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameList = new ArrayList<>();
        numberList = new ArrayList<>();

        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adapter = new ContactAdapter(nameList, numberList, getContext());
        layoutManager = new LinearLayoutManager(getContext());

        binding = FragmentCallBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        new Handler().postDelayed(() -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            displayContacts();
            binding.numberrclv.setAdapter(adapter);
            binding.numberrclv.setLayoutManager(layoutManager);
            binding.numberrclv.setHasFixedSize(true);
        }, 1000);

        binding.B0.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "0");
        });

        binding.B1.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "1");
        });

        binding.B2.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "2");
        });

        binding.B3.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "3");
        });

        binding.B4.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "4");
        });

        binding.B5.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "5");
        });

        binding.B6.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "6");
        });

        binding.B7.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "7");
        });

        binding.B8.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "8");
        });

        binding.B9.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "9");
        });

        binding.Bstar.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "*");
        });

        binding.Bhash.setOnClickListener(v -> {
            binding.tv.setText(binding.tv.getText() + "#");
        });

        binding.BCall.setOnClickListener(v -> {
            if(binding.tv.getText().length() <= 0){
                Toast.makeText(getContext(), "Number Field is Empty!", Toast.LENGTH_LONG).show();
            } else{
                makePhoneCall(binding.tv.getText().toString());
            }
        });

        binding.BBack.setOnClickListener(v -> {
            if (binding.tv.getText().length() > 0){
                binding.tv.setText(binding.tv.getText().toString().substring(0, binding.tv.getText().length()-1));
            }
        });


        return root;
    }

    private void makePhoneCall(String number) {
        String dial;

        if(number.length() == 100){
            dial = "tel:+91" + number;
        } else{
            dial = "tel:" + number;
        }


        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
    }

    private void displayContacts() {
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        nameList.add(name);
                        numberList.add(phoneNo);
                    }
                    pCur.close();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
