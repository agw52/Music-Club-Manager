package com.example.floridaclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class PartyActivity extends AppCompatActivity {
Button button1, button2, button3, button4;
Button[] buttons;
Room room;
ProgressBar partyBar, healthBar;
int earnings, fans, songPosition, buttonClicked;
boolean returningToMainActivity;
    ImageView teacherArea, studentArea, signal;
    VideoView background;
    TextView totalEarningsText, roomEarningsText, wealthText, partyLevel;
    Intent partyIntent;
    Handler handler = new Handler();
    Runnable switchToMainActivity = new Runnable(){

        @Override
        public void run() {
            returningToMainActivity = true;
            setResult(RESULT_OK, partyIntent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_party);
        background = findViewById(R.id.partybackground);
        Uri uri;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.otheractivitybackground);
        } else{
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.otheractivityhorizontalbackground);
        }
        background.setVideoURI(uri);
        background.start();
        background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        studentArea = findViewById(R.id.partystudentarea);
        partyIntent = new Intent(PartyActivity.this, MainActivity.class);
        Intent intent = getIntent();
        room = intent.getParcelableExtra("Room");
        room.setStudent(intent.getParcelableExtra("Student"));
        room.student.setTeacher(intent.getParcelableExtra("Teacher"));
        if(savedInstanceState == null){
            fans = intent.getIntExtra("fans", 0);
            earnings = intent.getIntExtra("earnings", 0);
            healthBar = findViewById(R.id.partyhealthbar);
            healthBar.setMax(room.student.teacher.maxHealth);
            healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
            studentArea.setImageResource(room.student.studentImage);
        }
        partyBar = findViewById(R.id.partypartybar);
        partyBar.setMax(intent.getIntExtra("partybarmax", 0));
        partyBar.setProgress(intent.getIntExtra("partybarprogress", 0));
        partyLevel = findViewById(R.id.partypartylevel);
        if(partyBar.getProgress() >= ((partyBar.getMax() / 3) * 2) && partyBar.getProgress() < partyBar.getMax()){
            partyLevel.setText("LvL. 2");
        } else if(partyBar.getProgress() == partyBar.getMax()){
            partyLevel.setText("LvL. 3");
        }
        signal = findViewById(R.id.partysignal);
        button1 = (Button) findViewById(R.id.partyactivitybutton1);
        button2 = (Button) findViewById(R.id.partyactivitybutton2);
        button3 = (Button) findViewById(R.id.partyactivitybutton3);
        button4 = (Button) findViewById(R.id.partyactivitybutton4);
        buttons = new Button[]{button1, button2, button3, button4};
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(earnings >= 4350) {
                    for(Button button : buttons){
                        button.setClickable(false);
                    }
                    buttonClicked = 1;
                    fans += 5;
                    Toast.makeText(PartyActivity.this, "You got more fans!", Toast.LENGTH_SHORT).show();
                    signal.setImageResource(R.drawable.doublecheck);
                    partyIntent.putExtra("fans", fans);
                    partyIntent.putExtra("giftcosts", 4350);
                    partyIntent.putExtra("room", room.roomNumber);
                    handler.postDelayed(switchToMainActivity, 3000);
                } else{
                    Toast.makeText(PartyActivity.this, "You don't have enough money!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Button button : buttons){
                    button.setClickable(false);
                }
                buttonClicked = 2;
                Toast.makeText(PartyActivity.this, "Student left satisfied", Toast.LENGTH_SHORT).show();
                fans += 3;
                Toast.makeText(PartyActivity.this, "You have slightly more fans", Toast.LENGTH_SHORT).show();
                signal.setImageResource(R.drawable.singlecheck);
                partyIntent.putExtra("room", room.roomNumber);
                partyIntent.putExtra("fans", fans);
                handler.postDelayed(switchToMainActivity, 3000);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(earnings >= 1740) {
                    for(Button button : buttons){
                        button.setClickable(false);
                    }
                    buttonClicked = 3;
                    partyIntent.putExtra("healthincrease", 31);
                    if ((room.student.teacher.maxHealth - room.student.teacher.health) <= 31) {
                        room.student.teacher.health = room.student.teacher.maxHealth;
                    } else {
                        room.student.teacher.health += 31;
                    }
                    healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
                    signal.setImageResource(R.drawable.doublecheck);
                    Toast.makeText(PartyActivity.this, "Teacher's health increased significantly", Toast.LENGTH_SHORT).show();
                    partyIntent.putExtra("room", room.roomNumber);
                    partyIntent.putExtra("giftcosts", 1740);
                    handler.postDelayed(switchToMainActivity, 3000);
                } else{
                    Toast.makeText(PartyActivity.this, "You don't have enough money!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Button button : buttons){
                    button.setClickable(false);
                }
                buttonClicked = 4;
                partyIntent.putExtra("healthincrease", 12);
                if((room.student.teacher.maxHealth - room.student.teacher.health) <= 12){
                    room.student.teacher.health = room.student.teacher.maxHealth;
                } else{
                    room.student.teacher.health += 12;
                }
                healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
                Toast.makeText(PartyActivity.this, "Teacher's health increased slightly", Toast.LENGTH_SHORT).show();
                signal.setImageResource(R.drawable.singlecheck);
                partyIntent.putExtra("room", room.roomNumber);
                handler.postDelayed(switchToMainActivity, 3000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(returningToMainActivity == false){
            songPosition = SoundPlayer.player.getCurrentPosition();
            SoundPlayer.player.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        teacherArea = findViewById(R.id.partyteacherarea);
        teacherArea.setBackgroundResource(room.student.teacher.teacherImage);
        totalEarningsText = findViewById(R.id.partytotalearnings);
        totalEarningsText.setText("$" + String.valueOf(earnings));
        roomEarningsText = findViewById(R.id.partyroomearnings);
        roomEarningsText.setText("$" + String.valueOf(room.earnings));
        wealthText = findViewById(R.id.partywealth);
        wealthText.setText(room.student.wealth);
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
        if(getIntent().getIntExtra("Music", 0) == 2){
            SoundPlayer.playSong(this, R.raw.onfire, songPosition);
        } else{
            SoundPlayer.playSong(this, R.raw.straightfuse, songPosition);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("earnings", earnings);
        outState.putInt("fans", fans);
        outState.putInt("songposition", songPosition);
        outState.putInt("buttonclicked", buttonClicked);
        outState.putInt("health", room.student.teacher.health);
        outState.putBoolean("buttonclickability", button1.isClickable());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        earnings = savedInstanceState.getInt("earnings", 0);
        fans = savedInstanceState.getInt("fans", 0);
        songPosition = savedInstanceState.getInt("songposition", 0);
        buttonClicked = savedInstanceState.getInt("buttonclicked", 0);
        room.student.teacher.health = savedInstanceState.getInt("health", 0);
        healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
        if(getIntent().getIntExtra("Music", 0) == 2){
            SoundPlayer.playSong(this, R.raw.onfire, songPosition);
        } else{
            SoundPlayer.playSong(this, R.raw.straightfuse, songPosition);
        }
        if(savedInstanceState.getBoolean("buttonclickability", false) == false){
            for(Button button : buttons){
                button.setClickable(false);
            }
            if(buttonClicked == 1){
                signal.setImageResource(R.drawable.doublecheck);
                partyIntent.putExtra("fans", fans);
                partyIntent.putExtra("giftcosts", 4350);signal.setImageResource(R.drawable.doublecheck);
            } else if(buttonClicked == 2){
                signal.setImageResource(R.drawable.singlecheck);
                partyIntent.putExtra("fans", fans);
            } else if(buttonClicked == 3){
                signal.setImageResource(R.drawable.doublecheck);
                partyIntent.putExtra("healthincrease", 31);
                partyIntent.putExtra("giftcosts", 1740);
            } else{
                signal.setImageResource(R.drawable.singlecheck);
                partyIntent.putExtra("healthincrease", 12);
            }
            partyIntent.putExtra("room", room.roomNumber);
            handler.postDelayed(switchToMainActivity, 2000);
        }
    }
}