package com.example.bajaao;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;



public class PlayerActivity extends AppCompatActivity {
Button playbtn ;
ImageView shufflebtn, nextbtn, prevbtn;
TextView txtsname, txtstart, txtstop;
SeekBar seekmusic;
ImageView imageView;
static boolean shuffleBoolean = false;

String sname;
public static final String EXTRA_NAME = "song_name";
static MediaPlayer mediaPlayer;
int position;
ArrayList<File> mySongs;
//Thread updateseekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        prevbtn = findViewById(R.id.prevbtn);
        nextbtn = findViewById(R.id.nextbtn);
        playbtn = findViewById(R.id.playbtn);
        shufflebtn = findViewById(R.id.shufflebtn);
//        txtstart = findViewById(R.id.txtstart);
//        txtstop = findViewById(R.id.txtstop);
        txtsname = findViewById(R.id.txtsname);
        imageView = findViewById(R.id.imageview);

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }



        Intent i =getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname");
        position = bundle.getInt("pos", 0);
        txtsname.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sname = mySongs.get(position).getName();
        txtsname.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

//        updateseekbar = new Thread()
//        {
//            @Override
//            public void run() {
//                int totalDuration = mediaPlayer.getDuration();
//                int currentposition = 0;
//
//                while (currentposition<totalDuration)
//                {
//                    try {
//                        sleep(500);
//                        currentposition = mediaPlayer.getCurrentPosition();
//                        seekmusic.setProgress(currentposition);
//                    }
//                    catch (InterruptedException | IllegalStateException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//
//        seekmusic.setMax(mediaPlayer.getDuration());
//        updateseekbar.start();
//        seekmusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.teal_200), PorterDuff.Mode.MULTIPLY);
//        seekmusic.getThumb().setColorFilter(getResources().getColor(R.color.teal_200), PorterDuff.Mode.SRC_IN);
//        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());
//            }
//        });

//        String endTime = createTime(mediaPlayer.getDuration());
//        txtstop.setText(endTime);
//
//        final Handler handler = new Handler();
//        final int delay = 1000;
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String currentTime = createTime(mediaPlayer.getCurrentPosition());
//                txtstart.setText(currentTime);
//                handler.postDelayed(this, delay);
//            }
//        }, delay);

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    playbtn.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }
                else{
                    playbtn.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });


        //automatically play next song
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                nextbtn.performClick();
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    if (shuffleBoolean){
                        position = getRandom(mySongs.size()-1);
                    }
                    else if(!shuffleBoolean){
                        position = ((position+1)%mySongs.size());

                    }
                    Uri u = Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    sname = mySongs.get(position).getName();
                    txtsname.setText(sname);
                    mediaPlayer.start();
                    playbtn.setBackgroundResource(R.drawable.ic_pause);
                    startAnimation(imageView);
                }else{
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    if (shuffleBoolean){
                        position = getRandom(mySongs.size()-1);
                    }
                    else if(!shuffleBoolean){
                        position = ((position+1)%mySongs.size());

                    }
                    Uri u = Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    sname = mySongs.get(position).getName();
                    txtsname.setText(sname);
                    mediaPlayer.start();
                    playbtn.setBackgroundResource(R.drawable.ic_pause);
                    startAnimation(imageView);

                }

            }
        });
        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    if (shuffleBoolean){
                        position = getRandom(mySongs.size()-1);
                    }
                    else if(!shuffleBoolean){
                        position = ((position-1)<0)?(mySongs.size()-1):(position-1);

                    }
                    Uri u =Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                    sname =mySongs.get(position).getName();
                    txtsname.setText(sname);
                    mediaPlayer.start();
                    playbtn.setBackgroundResource(R.drawable.ic_pause);
                    startAnimation(imageView);
                }else{
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    if (shuffleBoolean){
                        position = getRandom(mySongs.size()-1);
                    }
                    else if(!shuffleBoolean){
                        position = ((position-1)<0)?(mySongs.size()-1):(position-1);

                    }
                    Uri u =Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                    sname =mySongs.get(position).getName();
                    txtsname.setText(sname);
                    mediaPlayer.start();
                    playbtn.setBackgroundResource(R.drawable.ic_pause);
                    startAnimation(imageView);
                }

            }
        });
        shufflebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shuffleBoolean){
                    shuffleBoolean = false;
                    shufflebtn.setImageResource(R.drawable.ic_shuffle);
                }
                else{
                    shuffleBoolean = true;
                    shufflebtn.setImageResource(R.drawable.ic_shuffle_on);
                }
            }
        });
    }

    private int getRandom(int i) {
        Random random = new Random();
        return random.nextInt(i+1);
    }

    public void startAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView,"rotation",0f,360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }

//    public String createTime(int duration)
//    {
//        String time = "";
//        int min = duration/1000/60;
//        int sec = duration/1000%60;
//
//        time+=min+":";
//
//        if (sec<10)
//        {
//            time+="0";
//        }
//        time+=sec;
//
//        return  time;
//    }
}