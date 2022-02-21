package com.myinnovation.ai1manager.ui.Call;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.myinnovation.ai1manager.R;
import com.myinnovation.ai1manager.databinding.SingleContactLayoutBinding;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.VHolder>{

    ArrayList<String> list1;
    ArrayList<String> list2;
    Context context;

    public ContactAdapter(ArrayList<String> list1, ArrayList<String> list2, Context context) {
        this.list1 = list1;
        this.list2 = list2;
        this.context = context;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VHolder(LayoutInflater.from(context).inflate(R.layout.single_contact_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.binding.cname.setText(list1.get(position));
        holder.binding.cnumber.setText(list2.get(position));

        holder.itemView.setOnClickListener(v -> makePhoneCall(list2.get(position)));
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{
        SingleContactLayoutBinding binding;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleContactLayoutBinding.bind(itemView);
        }
    }

    private void makePhoneCall(String number) {
        String dial;

        if(number.length() == 100){
            dial = "tel:+91" + number;
        } else{
            dial = "tel:" + number;
        }


        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
    }
}
