package com.myinnovation.ai1manager.ui.Music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myinnovation.ai1manager.Activities.PDFViewActivity;
import com.myinnovation.ai1manager.Activities.SongPlayerActivity;
import com.myinnovation.ai1manager.R;
import com.myinnovation.ai1manager.databinding.SingleMusicListLayoutBinding;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.musicViewHolder>{
    private ArrayList<File> Songs;
    private Context context;

    public MusicAdapter(ArrayList<File> Songs, Context context) {
        this.Songs = Songs;
        this.context = context;
    }

    @NonNull
    @Override
    public MusicAdapter.musicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicAdapter.musicViewHolder(LayoutInflater.from(context).inflate(R.layout.single_music_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.musicViewHolder holder, int position) {
        File f = Songs.get(position);
        String name;
        if(f.getName().endsWith(".mp3")){
            name = f.getName().replace(".mp3", "");
        } else if(f.getName().endsWith(".wav")) {
            name = f.getName().replace( ".wav","");
        } else{
            name = f.getName();
        }

        holder.binding.songName.setText(name);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SONGS", Songs);
        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(context,com.myinnovation.ai1manager.Activities.SongPlayerActivity.class)
                    .putExtra("BUNDLE", bundle)
                    .putExtra("POS", position));
        });
    }

    @Override
    public int getItemCount() {
        return Songs.size();
    }

    public static class musicViewHolder extends RecyclerView.ViewHolder{
        SingleMusicListLayoutBinding binding;
        public musicViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleMusicListLayoutBinding.bind(itemView);
        }
    }
}
