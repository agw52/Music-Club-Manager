package com.example.floridaclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class StartActivity extends AppCompatActivity implements Page1.page1Reporter, Page2.page2Reporter, Page3.page3Reporter, Page4.page4Reporter, Page5.page5Reporter, Page6.page6Reporter, Page7.page7Reporter, Page8.page8Reporter, Page9.page9Reporter {
VideoView background;
Button button1, button2, exitGuide;
int songPosition, currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        background = findViewById(R.id.startbackground);
        Uri uri;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.startbackground);
        } else{
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.horizontalstartbackground);
        }
        background.setVideoURI(uri);
        background.start();
        background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            SoundPlayer.soundPool = new SoundPool.Builder().setMaxStreams(6).setAudioAttributes(audioAttributes).build();
        } else{
            SoundPlayer.soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }
        if(savedInstanceState == null || (savedInstanceState != null && getIntent().hasExtra("comingfromresults"))){
                SoundPlayer.playSong(this, R.raw.startmusic, 0);
                if(getIntent().hasExtra("comingfromresults")){
                    getIntent().removeExtra("comingfromresults");
                }
        }
        button1 = findViewById(R.id.startbutton1);
        button2 = findViewById(R.id.startbutton2);
        exitGuide = findViewById(R.id.exitguide);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.player.release();
                button1.setClickable(false);
                button2.setClickable(false);
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.guide).setVisibility(View.VISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                exitGuide.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.guide, new Page1());
                fragmentTransaction.commit();
            }
        });
        exitGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.guide).setVisibility(View.INVISIBLE);
                exitGuide.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(button1.isClickable() || findViewById(R.id.guide).getVisibility() == View.VISIBLE){
            songPosition = SoundPlayer.player.getCurrentPosition();
            SoundPlayer.player.release();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        background.start();
        background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        SoundPlayer.playSong(this, R.raw.startmusic, songPosition);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
outState.putInt("currentpage", currentPage);
outState.putInt("guidevisibility", findViewById(R.id.guide).getVisibility());
        outState.putInt("songposition", songPosition);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
currentPage = savedInstanceState.getInt("currentpage", 0);
songPosition = savedInstanceState.getInt("songposition", 0);
        SoundPlayer.playSong(this, R.raw.startmusic, songPosition);
if(savedInstanceState.getInt("guidevisibility", 0) == View.VISIBLE){
    findViewById(R.id.guide).setVisibility(View.VISIBLE);
    exitGuide.setVisibility(View.VISIBLE);
    button1.setVisibility(View.INVISIBLE);
    button2.setVisibility(View.INVISIBLE);
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    if(currentPage == 1){
fragmentTransaction.replace(R.id.guide, new Page1());
    } else if(currentPage == 2){
        fragmentTransaction.replace(R.id.guide, new Page2());
    } else if(currentPage == 3){
        fragmentTransaction.replace(R.id.guide, new Page3());
    } else if(currentPage == 4){
        fragmentTransaction.replace(R.id.guide, new Page4());
    } else if(currentPage == 5){
        fragmentTransaction.replace(R.id.guide, new Page5());
    } else if(currentPage == 6){
        fragmentTransaction.replace(R.id.guide, new Page6());
    } else if(currentPage == 7){
        fragmentTransaction.replace(R.id.guide, new Page7());
    } else if(currentPage == 8){
        fragmentTransaction.replace(R.id.guide, new Page8());
    } else{
        fragmentTransaction.replace(R.id.guide, new Page9());
    }
    fragmentTransaction.commit();
}
    }
    @Override
    public void passPage1() {
currentPage = 1;
    }

    @Override
    public void passPage2() {
currentPage = 2;
    }

    @Override
    public void passPage3() {
        currentPage = 3;
    }

    @Override
    public void passPage4() {
        currentPage = 4;
    }

    @Override
    public void passPage5() {
currentPage = 5;
    }

    @Override
    public void passPage6() {
currentPage = 6;
    }

    @Override
    public void passPage7() {
currentPage = 7;
    }

    @Override
    public void passPage8() {
currentPage = 8;
    }

    @Override
    public void passPage9() {
currentPage = 9;
    }
}