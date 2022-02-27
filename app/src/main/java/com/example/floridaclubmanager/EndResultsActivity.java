package com.example.floridaclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndResultsActivity extends AppCompatActivity {
Button button;
TextView lessonSales, giftCosts, staffCosts, profit;
int songPosition;
Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_end_results);
        button = findViewById(R.id.endgamebutton);
        lessonSales = findViewById(R.id.lessonsalesnumber);
        giftCosts = findViewById(R.id.giftcostsnumber);
        staffCosts = findViewById(R.id.staffcostsnumber);
        profit = findViewById(R.id.profitnumber);
        handler = new Handler();
        if(savedInstanceState == null){
            SoundPlayer.player.release();
            handler.postDelayed(showLessonSales, 2000);
        } else{
            if(savedInstanceState.getInt("buttonvisibility", 0) == View.INVISIBLE){
                handler.postDelayed(showLessonSales, 2000);
            }
        }
    }
    Runnable showLessonSales = new Runnable() {
        @Override
        public void run() {
            findViewById(R.id.endgamecontainer).setVisibility(View.VISIBLE);
            findViewById(R.id.firstline).setVisibility(View.VISIBLE);
            findViewById(R.id.lessonsalestext).setVisibility(View.VISIBLE);
            lessonSales.setText("$" + String.valueOf(getIntent().getIntExtra("earnings", 0)));
            SoundPlayer.player = MediaPlayer.create(EndResultsActivity.this, R.raw.moneycounter);
            SoundPlayer.player.start();
            SoundPlayer.player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    SoundPlayer.player.release();
                    SoundPlayer.playSong(EndResultsActivity.this, R.raw.endgamenoise, 0);
                }
            });
            handler.postDelayed(showGiftCosts, 2000);
        }
    };
    Runnable showGiftCosts = new Runnable() {
        @Override
        public void run() {
            findViewById(R.id.secondline).setVisibility(View.VISIBLE);
            findViewById(R.id.giftcoststext).setVisibility(View.VISIBLE);
            giftCosts.setText("-$" + String.valueOf(getIntent().getIntExtra("giftcosts", 0)));
            handler.postDelayed(showStaffCosts, 2000);
        }
    };
    Runnable showStaffCosts = new Runnable() {
        @Override
        public void run() {
            findViewById(R.id.thirdline).setVisibility(View.VISIBLE);
            findViewById(R.id.staffcoststext).setVisibility(View.VISIBLE);
            staffCosts.setText("-$15000");
            handler.postDelayed(showProfit, 3000);
        }
    };
    Runnable showProfit = new Runnable() {
        @Override
        public void run() {
            findViewById(R.id.fourthline).setVisibility(View.VISIBLE);
            findViewById(R.id.profittext).setVisibility(View.VISIBLE);
            if((getIntent().getIntExtra("earnings", 0) - getIntent().getIntExtra("giftcosts", 0) - 15000) < 0){
                int negativeToPositive = -(getIntent().getIntExtra("earnings", 0) - getIntent().getIntExtra("giftcosts", 0) - 15000);
                profit.setText("-$" + String.valueOf(negativeToPositive));
            } else{
                profit.setText("$" + String.valueOf(getIntent().getIntExtra("earnings", 0) - getIntent().getIntExtra("giftcosts", 0) - 15000));
            }
            handler.postDelayed(showButton, 2000);
        }
    };
    Runnable showButton = new Runnable() {
        @Override
        public void run() {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundPlayer.player.release();
                    button.setClickable(false);
                    Intent intent = new Intent(EndResultsActivity.this, StartActivity.class);
                    intent.putExtra("comingfromresults", true);
                    startActivity(intent);
                    finish();
                }
            });
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(findViewById(R.id.endgamecontainer).getVisibility() == View.VISIBLE && button.getVisibility() == View.INVISIBLE){
            SoundPlayer.player.release();
        } else if(button.getVisibility() == View.VISIBLE){
                songPosition = SoundPlayer.player.getCurrentPosition();
                SoundPlayer.player.release();
            }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(button.getVisibility() == View.VISIBLE){
            SoundPlayer.playSong(this, R.raw.endgamenoise, songPosition);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
int buttonVisibility = button.getVisibility();
outState.putInt("buttonvisibility", buttonVisibility);
if(button.getVisibility() == View.VISIBLE){
    int earnings = getIntent().getIntExtra("earnings", 0);
    int giftCosts = getIntent().getIntExtra("giftcosts", 0);
    outState.putInt("earnings", earnings);
    outState.putInt("giftcosts", giftCosts);
    outState.putInt("songposition", songPosition);
}
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
if(savedInstanceState.getInt("buttonvisibility", 0) == View.VISIBLE){
    songPosition = savedInstanceState.getInt("songposition", 0);
    SoundPlayer.playSong(this, R.raw.endgamenoise, songPosition);
    findViewById(R.id.endgamecontainer).setVisibility(View.VISIBLE);
    findViewById(R.id.lessonsalestext).setVisibility(View.VISIBLE);
    findViewById(R.id.firstline).setVisibility(View.VISIBLE);
    findViewById(R.id.giftcoststext).setVisibility(View.VISIBLE);
    findViewById(R.id.secondline).setVisibility(View.VISIBLE);
    findViewById(R.id.staffcoststext).setVisibility(View.VISIBLE);
    findViewById(R.id.thirdline).setVisibility(View.VISIBLE);
    findViewById(R.id.profittext).setVisibility(View.VISIBLE);
    findViewById(R.id.fourthline).setVisibility(View.VISIBLE);
    lessonSales.setText("$" + String.valueOf(savedInstanceState.getInt("earnings", 0)));
    giftCosts.setText("-$" + String.valueOf(savedInstanceState.getInt("giftcosts", 0)));
    staffCosts.setText("-$15000");
    profit.setText("$" + String.valueOf(savedInstanceState.getInt("earnings", 0) - savedInstanceState.getInt("giftcosts", 0) - 15000));
    button.setVisibility(View.VISIBLE);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundPlayer.player.release();
            button.setClickable(false);
            Intent intent = new Intent(EndResultsActivity.this, StartActivity.class);
            intent.putExtra("comingfromresults", true);
            startActivity(intent);
            finish();
        }
    });
}
    }
}