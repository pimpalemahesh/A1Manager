package com.myinnovation.ai1manager.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import com.myinnovation.ai1manager.R;
import com.myinnovation.ai1manager.databinding.ActivitySongPlayerBinding;

import java.io.File;
import java.util.ArrayList;

public class SongPlayerActivity extends AppCompatActivity {

    ActivitySongPlayerBinding binding;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> songs;
    Thread updateSeekBar;
    AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySongPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        songs = (ArrayList<File>) bundle.getSerializable("SONGS");
        position = bundle.getInt("POS", 0);
        binding.txtsongname.setSelected(true);
        Uri uri = Uri.parse(songs.get(position).toString());
        binding.txtsongname.setText(songs.get(position).getName());
        animatorSet = new AnimatorSet();

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        binding.seekbar.setMax(mediaPlayer.getDuration());
        binding.seekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
        binding.seekbar.getThumb().setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.SRC_IN);
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                musicTime();
            }
        });

        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int currentPosition = 0;
                try{
                    while(currentPosition<mediaPlayer.getDuration()){
                        currentPosition = mediaPlayer.getCurrentPosition();
                        binding.seekbar.setProgress(currentPosition);
                        sleep(800);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        updateSeekBar.start();

        mediaPlayer.setOnCompletionListener(mp -> binding.btnnext.performClick());

        int audiosessionId = mediaPlayer.getAudioSessionId();
        if (audiosessionId == -1) {
            binding.blast.setAudioSessionId(audiosessionId);
        }

        musicTime();
        if(mediaPlayer.isPlaying()){
            startAnimation(binding.songImg);
        } else{
            animatorSet.end();
        }

        binding.playbtn.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                binding.playbtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                animatorSet.cancel();
                mediaPlayer.pause();
            } else {
                binding.playbtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                animatorSet.start();
                mediaPlayer.start();
            }
            musicTime();
        });

        binding.btnnext.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % songs.size());
            Uri u = Uri.parse(songs.get(position).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
            binding.txtsongname.setText(songs.get(position).getName());
            binding.playbtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            int aid = mediaPlayer.getAudioSessionId();
            if (aid == -1) {
                binding.blast.setAudioSessionId(aid);
            }
            binding.seekbar.setProgress(0);
            musicTime();
            mediaPlayer.start();
        });

        binding.btnpre.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0) ? (songs.size() - 1) : (position - 1);
            Uri u = Uri.parse(songs.get(position).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
            binding.txtsongname.setText(songs.get(position).getName());
            mediaPlayer.start();
            binding.playbtn.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            int aid = mediaPlayer.getAudioSessionId();
            if (aid == -1) {
                binding.blast.setAudioSessionId(aid);
            }
            binding.seekbar.setProgress(0);
            musicTime();
            mediaPlayer.start();
        });

        binding.btnff.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                binding.seekbar.setProgress(mediaPlayer.getCurrentPosition());
                musicTime();
            }
        });

        binding.btnfr.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                binding.seekbar.setProgress(mediaPlayer.getCurrentPosition());
                musicTime();
            }
        });
    }

    public void startAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.songImg, "rotation", 0f, 360f);
        animator.setDuration(10000);
        animator.setRepeatCount(mediaPlayer.getDuration()/1000);

        animatorSet.playTogether(animator);
        animatorSet.start();
    }

    public String CreateTime(int duration) {
        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time += min + ":";
        if (sec < 10) {
            time += "0";
        }
        time += sec;
        return time;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mediaPlayer.stop();
            mediaPlayer.release();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        startAnimation(binding.songImg);
        mediaPlayer.release();
        Uri u = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this, u);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
        super.onResume();
    }

    private void musicTime(){
        binding.txtend.setText(CreateTime(mediaPlayer.getDuration()));
        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mediaPlayer.isPlaying()) {
                        String currentTime = CreateTime(mediaPlayer.getCurrentPosition());
                        binding.txtstart.setText(currentTime);
                        handler.postDelayed(this, delay);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, delay);
    }

    @Override
    protected void onDestroy() {
        if (binding.blast != null) {
            binding.blast.release();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}