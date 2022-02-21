package com.myinnovation.ai1manager.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myinnovation.ai1manager.Activities.ImageViewActivity;
import com.myinnovation.ai1manager.R;
import com.myinnovation.ai1manager.databinding.SingleImageLayoutBinding;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<File> list;
    private Context context;

    public ImageAdapter(ArrayList<File> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.single_image_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        File file = list.get(position);
        Uri uri = Uri.parse(file.getAbsolutePath());
        holder.binding.image.setImageURI(uri);
        holder.binding.imageName.setText(file.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ImageViewActivity.class);
            intent.putExtra("URI", uri);
            intent.putExtra("NAME", file.getName());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        SingleImageLayoutBinding binding;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleImageLayoutBinding.bind(itemView);
        }
    }

}
