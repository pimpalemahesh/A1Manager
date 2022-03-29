package com.myinnovation.ai1manager.ui.Video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myinnovation.ai1manager.Activities.SongPlayerActivity;
import com.myinnovation.ai1manager.R;
import com.myinnovation.ai1manager.databinding.SingleMusicListLayoutBinding;
import com.myinnovation.ai1manager.databinding.SingleVideoLayoutBinding;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.videoViewHolder>{
    private ArrayList<File> Videos;
    private Context context;

    public VideoAdapter(ArrayList<File> Videos, Context context) {
        this.Videos = Videos;
        this.context = context;
    }

    @NonNull
    @Override
    public videoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new videoViewHolder(LayoutInflater.from(context).inflate(R.layout.single_video_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull videoViewHolder holder, int position) {
        File f = Videos.get(position);
        String name;
        if(f.getName().endsWith(".mp3")){
            name = f.getName().replace(".mp4", "");
        } else{
            name = f.getName();
        }


        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, SongPlayerActivity.class)
                    .putExtra("PATH", f.getAbsolutePath())
                    .putExtra("NAME", name));
        });
    }

    @Override
    public int getItemCount() {
        return Videos.size();
    }

    public static class videoViewHolder extends RecyclerView.ViewHolder{
        SingleVideoLayoutBinding binding;
        public videoViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleVideoLayoutBinding.bind(itemView);
        }
    }
}
