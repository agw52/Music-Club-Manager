package com.example.floridaclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import pl.droidsonroids.gif.GifImageView;

public class TroubleActivity extends AppCompatActivity {
Button button1, button2, button3, button4;
Button[] buttons;
ProgressBar partyBar, healthBar;
VideoView background;
Room room;
int earnings, fans, countDownTimeLeft, songPosition;
long TROUBLE_ACTIVITY_COUNTDOWN;
boolean leave, userDidntAnswer, returningToMainActivity, sentStudentHome;
Intent troubleIntent;
CountDownTimer troubleTimer;
ImageView teacherArea, studentArea;
TextView totalEarningsText, roomEarningsText, wealthText, signal, partyLevel;
Handler handler = new Handler();
Runnable studentStays = new Runnable() {
    @Override
    public void run() {
       returningToMainActivity = true;
        setResult(RESULT_OK, troubleIntent);
       finish();
    }
};
Runnable studentLeaves = new Runnable() {
    @Override
    public void run() {
  returningToMainActivity = true;
        setResult(RESULT_CANCELED, troubleIntent);
  finish();
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trouble);
        background = findViewById(R.id.troublebackground);
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
        studentArea = findViewById(R.id.troublestudentarea);
        troubleIntent = new Intent(TroubleActivity.this, MainActivity.class);
        signal = findViewById(R.id.troublesignal);
        button1 = findViewById(R.id.troublebutton1);
        button2 = findViewById(R.id.troublebutton2);
        button3 = findViewById(R.id.troublebutton3);
        button4 = findViewById(R.id.troublebutton4);
        buttons = new Button[]{button1, button2, button3, button4};
        partyBar = findViewById(R.id.troublepartybar);
        partyLevel = findViewById(R.id.troublepartylevel);
        Intent intent = getIntent();
        room = intent.getParcelableExtra("Room");
        room.setStudent(intent.getParcelableExtra("Student"));
        room.student.setTeacher(intent.getParcelableExtra("Teacher"));
        partyBar.setMax(intent.getIntExtra("partybarmax", 0));
        partyBar.setProgress(intent.getIntExtra("partybarprogress", 0));
        if(partyBar.getProgress() >= ((partyBar.getMax() / 3) * 2) && partyBar.getProgress() < partyBar.getMax()){
            partyLevel.setText("LvL. 2");
        } else if(partyBar.getProgress() == partyBar.getMax()){
            partyLevel.setText("LvL. 3");
        }
        if(savedInstanceState == null){
            fans = intent.getIntExtra("fans", 0);
            earnings = intent.getIntExtra("earnings", 0);
            healthBar = findViewById(R.id.troublehealthbar);
            healthBar.setMax(room.student.teacher.maxHealth);
            healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
            studentArea.setImageResource(room.student.studentImage);
            TROUBLE_ACTIVITY_COUNTDOWN = 5000;
            countDownTimeLeft = 5;
            startTimer();
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                troubleTimer.cancel();
                for(Button button : buttons){
                    button.setClickable(false);
                }
                troubleIntent.putExtra("healthdecrease", 15);
                if(room.student.teacher.health <= 15){
                    healthBar.incrementProgressBy(room.student.teacher.health);
                } else{
                    healthBar.incrementProgressBy(15);
                }
                if(room.student.satisfaction.equals("Unsatisfied")){
                    studentRemainChance(troubleIntent, 8);
                    if(leave == false){
                        studentArea.setImageResource(R.drawable.straightface);
                    }
                } else{
                    studentRemainChance(troubleIntent, 7);
                    if(leave == false){
                        studentArea.setImageResource(R.drawable.unhappyface);
                    }
                }
                signal.setText("");
                troubleIntent.putExtra("room", room.roomNumber);
                if(leave == false){
                    handler.postDelayed(studentStays, 3000);
                } else{
                    handler.postDelayed(studentLeaves, 3000);
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(earnings >= 870) {
                    troubleTimer.cancel();
                    for(Button button : buttons){
                        button.setClickable(false);
                    }
                    if (room.student.satisfaction.equals("Unsatisfied")) {
                        studentRemainChance(troubleIntent, 6);
                        if(leave == false){
                            studentArea.setImageResource(R.drawable.straightface);
                        }
                    } else{
                        studentRemainChance(troubleIntent, 5);
                        if(leave == false){
                            studentArea.setImageResource(R.drawable.unhappyface);
                        }
                    }
                    signal.setText("");
                    troubleIntent.putExtra("room", room.roomNumber);
                    troubleIntent.putExtra("giftcosts", 870);
                    if(leave == false){
                        handler.postDelayed(studentStays, 3000);
                    } else{
                        handler.postDelayed(studentLeaves, 3000);
                    }
                } else{
                    Toast.makeText(TroubleActivity.this, "You don't have enough money!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
troubleTimer.cancel();
                for(Button button : buttons){
                    button.setClickable(false);
                }
if(room.student.satisfaction.equals("Unsatisfied")){
    studentRemainChance(troubleIntent, 4);
    if(leave == false){
        studentArea.setImageResource(R.drawable.straightface);
    }
} else{
    studentRemainChance(troubleIntent, 3);
    if(leave == false){
        studentArea.setImageResource(R.drawable.unhappyface);
    }
}
signal.setText("");
if(leave == false){
    if((room.student.teacher.maxHealth - room.student.teacher.health) <= 15){
        room.student.teacher.health = room.student.teacher.maxHealth;
    } else{
        room.student.teacher.health += 15;
    }
    troubleIntent.putExtra("healthincrease", 15);
}
                healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
troubleIntent.putExtra("room", room.roomNumber);
if(leave == false){
    handler.postDelayed(studentStays, 3000);
} else{
    handler.postDelayed(studentLeaves, 3000);
}
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                troubleTimer.cancel();
                for(Button button : buttons){
                    button.setClickable(false);
                }
                leave = true;
                signal.setText("");
                signal.setBackgroundResource(R.drawable.singlecheck);
                int fansLossChance = (int) (Math.random() * 2) + 1;
if(room.student.satisfaction.equals("Unsatisfied")){
studentArea.setImageResource(R.drawable.pissedface);
}
if(fansLossChance != 1){
    if(fans <= 3){
        fans = 0;
    } else{
        fans -= 3;
    }
    troubleIntent.putExtra("fans", fans);
}
troubleIntent.putExtra("room", room.roomNumber);
handler.postDelayed(studentLeaves, 3000);
            }
        });
    }
    private void startTimer(){
        troubleTimer = new CountDownTimer(TROUBLE_ACTIVITY_COUNTDOWN, 1000) {
            @Override
            public void onTick(long l) {
                TROUBLE_ACTIVITY_COUNTDOWN = l;
                signal.setText(String.valueOf(countDownTimeLeft));
                countDownTimeLeft--;
            }

            @Override
            public void onFinish() {
                for(Button button : buttons){
                    button.setClickable(false);
                }
                if(room.student.satisfaction.equals("Unsatisfied")){
                    studentArea.setImageResource(R.drawable.pissedface);
                    sentStudentHome = true;
                }
                signal.setText("");
                signal.setBackgroundResource(R.drawable.redx);
                if(fans <= 5){
                    fans = 0;
                } else{
                    fans -= 5;
                }
                userDidntAnswer = true;
                leave = true;
                troubleIntent.putExtra("room", room.roomNumber);
                troubleIntent.putExtra("fans", fans);
                troubleIntent.putExtra("userDidntAnswer", userDidntAnswer);
                handler.postDelayed(studentLeaves, 3000);
            }
        }.start();
    }
    private void studentRemainChance(Intent intent, int odds){
        int remainChance = (int)(Math.random() * 10) + 1;
        int fanLossChance = (int)(Math.random() * 2) + 1;
            if (remainChance <= odds) {
                signal.setBackgroundResource(R.drawable.doublecheck);
                Toast.makeText(TroubleActivity.this, "Student's satisfaction improved slightly", Toast.LENGTH_SHORT).show();
            } else {
                leave = true;
                signal.setBackgroundResource(R.drawable.redx);
                if (fanLossChance != 1) {
                    if (fans <= 3) {
                        fans = 0;
                    } else {
                        fans -= 3;
                    }
                    Toast.makeText(TroubleActivity.this, "Student left a negative review", Toast.LENGTH_SHORT).show();
                    intent.putExtra("fans", fans);
                } else{
                    Toast.makeText(TroubleActivity.this, "Student left", Toast.LENGTH_SHORT).show();
                }
            }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(userDidntAnswer == false){
            troubleTimer.cancel();
        }
        if(returningToMainActivity == false){
            songPosition = SoundPlayer.player.getCurrentPosition();
            SoundPlayer.player.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalEarningsText = findViewById(R.id.troubletotalearnings);
        totalEarningsText.setText("$" + String.valueOf(earnings));
        roomEarningsText = findViewById(R.id.troubleroomearnings);
        roomEarningsText.setText("$" + String.valueOf(room.earnings));
        teacherArea = findViewById(R.id.troubleteacherarea);
        teacherArea.setBackgroundResource(room.student.teacher.teacherImage);
        wealthText = findViewById(R.id.troublewealth);
        wealthText.setText(room.student.wealth);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(userDidntAnswer == false){
            startTimer();
        }
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
        outState.putInt("fans", fans);
        outState.putInt("earnings", earnings);
        outState.putInt("countdowntimeleft", countDownTimeLeft);
        outState.putInt("health", room.student.teacher.health);
        outState.putLong("timeleft", TROUBLE_ACTIVITY_COUNTDOWN);
        outState.putBoolean("leave", leave);
        outState.putBoolean("userdidntanswer", userDidntAnswer);
        outState.putBoolean("returningtomainactivity", returningToMainActivity);
        outState.putBoolean("buttonclickability", button1.isClickable());
        outState.putBoolean("healthdropped", troubleIntent.hasExtra("healthdecrease"));
        outState.putBoolean("gavegift", troubleIntent.hasExtra("giftcosts"));
        outState.putBoolean("healthimproved", troubleIntent.hasExtra("healthincrease"));
        outState.putBoolean("sentunsatisfiedstudenthome", sentStudentHome);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fans = savedInstanceState.getInt("fans", 0);
        earnings = savedInstanceState.getInt("earnings", earnings);
        countDownTimeLeft = savedInstanceState.getInt("countdowntimeleft", 0);
        TROUBLE_ACTIVITY_COUNTDOWN = savedInstanceState.getLong("timeleft", 0);
        room.student.teacher.health = savedInstanceState.getInt("health", 0);
        leave = savedInstanceState.getBoolean("leave", false);
        userDidntAnswer = savedInstanceState.getBoolean("userdidntanswer", false);
        returningToMainActivity = savedInstanceState.getBoolean("returningtomainactivity", false);
        sentStudentHome = savedInstanceState.getBoolean("sentunsatisfiedstudenthome", false);
        healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
        if(getIntent().getIntExtra("Music", 0) == 2){
            SoundPlayer.playSong(this, R.raw.onfire, songPosition);
        } else{
            SoundPlayer.playSong(this, R.raw.straightfuse, songPosition);
        }
        if(savedInstanceState.getBoolean("buttonclickability", false)){
            startTimer();
        } else{
            troubleIntent.putExtra("fans", fans);
            troubleIntent.putExtra("room", room.roomNumber);
            signal.setText("");
            for(Button button : buttons){
                button.setClickable(false);
            }
            if(savedInstanceState.getBoolean("healthdropped", false)){
                troubleIntent.putExtra("healthdecrease", 15);
            } else if(savedInstanceState.getBoolean("healthimproved", false)){
                troubleIntent.putExtra("healthincrease", 15);
            } else if(savedInstanceState.getBoolean("gavegift", false)){
                troubleIntent.putExtra("giftcosts", 870);
            }
            if(leave == false && userDidntAnswer == false){
                if(room.student.satisfaction.equals("Pissed")){
                    studentArea.setImageResource(R.drawable.unhappyface);
                } else{
                    studentArea.setImageResource(R.drawable.straightface);
                }
                signal.setBackgroundResource(R.drawable.doublecheck);
                handler.postDelayed(studentStays, 2000);
            } else{
                if(sentStudentHome){
                    studentArea.setImageResource(R.drawable.pissedface);
                    signal.setBackgroundResource(R.drawable.singlecheck);
                } else{
                    signal.setBackgroundResource(R.drawable.redx);
                }
                if(userDidntAnswer && sentStudentHome == false){
                    troubleIntent.putExtra("userDidntAnswer", userDidntAnswer);
                }
                handler.postDelayed(studentLeaves, 2000);
            }
        }
    }
}