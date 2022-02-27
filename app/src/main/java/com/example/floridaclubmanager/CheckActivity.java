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

public class CheckActivity extends AppCompatActivity {
Button button1, button2, button3, button4;
Button[] buttons;
Room room;
ProgressBar partyBar, healthBar;
int earnings, fans, extensionOdds, correctSound, songPosition, buttonClicked;
boolean fansLost, returningToMainActivity;
ImageView teacherArea, studentArea, signal;
VideoView background;
TextView earningsText, roomEarningsText, wealthText, partyLevel;
Intent checkIntent;
Handler handler = new Handler();
Runnable switchToMainActivity = new Runnable(){

    @Override
    public void run() {
        returningToMainActivity = true;
        setResult(RESULT_CANCELED, checkIntent);
        finish();
    }
};
Runnable switchToMainActivityWithExtension = new Runnable() {
    @Override
    public void run() {
    returningToMainActivity = true;
        setResult(RESULT_OK, checkIntent);
    finish();
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_check);
        background = findViewById(R.id.checkbackground);
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
        signal = findViewById(R.id.checksignal);
        studentArea = findViewById(R.id.checkstudentarea);
        Intent intent = getIntent();
        checkIntent = new Intent(CheckActivity.this, MainActivity.class);
        room = intent.getParcelableExtra("Room");
        room.setStudent(intent.getParcelableExtra("Student"));
        room.student.setTeacher(intent.getParcelableExtra("Teacher"));
        if(savedInstanceState == null){
            earnings = intent.getIntExtra("earnings", 0);
            fans = intent.getIntExtra("fans", 0);
            studentArea.setImageResource(room.student.studentImage);
        }
        partyBar = findViewById(R.id.checkpartybar);
        partyBar.setMax(intent.getIntExtra("partybarmax", 0));
        partyBar.setProgress(intent.getIntExtra("partybarprogress", 0));
        partyLevel = findViewById(R.id.checkpartylevel);
        if(partyBar.getProgress() >= ((partyBar.getMax() / 3) * 2) && partyBar.getProgress() < partyBar.getMax()){
            partyLevel.setText("LvL. 2");
        } else if(partyBar.getProgress() == partyBar.getMax()){
            partyLevel.setText("LvL. 3");
        }
        correctSound = SoundPlayer.soundPool.load(this, R.raw.extensionsound, 1);
        button1 = (Button) findViewById(R.id.checkactivitybutton1);
        button2 = (Button) findViewById(R.id.checkactivitybutton2);
        button3 = (Button) findViewById(R.id.checkactivitybutton3);
        button4 = (Button) findViewById(R.id.checkactivitybutton4);
        buttons = new Button[]{button1, button2, button3, button4};
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Button button : buttons){
                    button.setClickable(false);
                }
                buttonClicked = 1;
                button1Action();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(earnings >= 870) {
                    for(Button button : buttons){
                        button.setClickable(false);
                    }
                    buttonClicked = 2;
                    if(room.student.satisfaction.equals("Stoked")){
                        fanIncreaseOdds(10, 6, 3, 5, 4, 3);
                        signal.setImageResource(R.drawable.doublecheck);
                    } else if(room.student.satisfaction.equals("Happy")){
                        fanIncreaseOdds(10, 5, 2, 4, 3, 3);
                        signal.setImageResource(R.drawable.doublecheck);
                        studentArea.setImageResource(R.drawable.stokedface);
                    } else if(room.student.satisfaction.equals("Mediocre")){
                        fanIncreaseOdds(8, 4, 0, 2, 2, 0);
                        signal.setImageResource(R.drawable.doublecheck);
                        studentArea.setImageResource(R.drawable.happyface);
                    } else{
                        fanLossOdds(2, 4, 2, 3);
                        if(fansLost == false){
                            signal.setImageResource(R.drawable.doublecheck);
                            if(room.student.satisfaction.equals("Unsatisfied")){
                                studentArea.setImageResource(R.drawable.straightface);
                            } else{
                                studentArea.setImageResource(R.drawable.unhappyface);
                            }
                        }
                    }
                    checkIntent.putExtra("giftcosts", 870);
                    checkIntent.putExtra("room", room.roomNumber);
                    handler.postDelayed(switchToMainActivity, 3000);
                } else{
                    Toast.makeText(CheckActivity.this, "You don't have enough money!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Button button : buttons){
                    button.setClickable(false);
                }
                buttonClicked = 3;
                if(room.student.satisfaction.equals("Stoked")){
                    signal.setImageResource(R.drawable.singlecheck);
                    fanIncreaseOdds(8, 5, 2, 3, 2, 2);
                } else if(room.student.satisfaction.equals("Happy")){
                    signal.setImageResource(R.drawable.singlecheck);
                    studentArea.setImageResource(R.drawable.stokedface);
                    fanIncreaseOdds(7, 4, 1, 3, 2, 2);
                } else if(room.student.satisfaction.equals("Mediocre")){
                    signal.setImageResource(R.drawable.singlecheck);
                    studentArea.setImageResource(R.drawable.happyface);
                    fanIncreaseOdds(4, 0, 0, 2, 0, 0);
                } else{
                    signal.setImageResource(R.drawable.redx);
                    fanLossOdds(5, 7, 2, 3);
                }
checkIntent.putExtra("room", room.roomNumber);
                handler.postDelayed(switchToMainActivity, 3000);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Button button : buttons){
                    button.setClickable(false);
                }
                buttonClicked = 4;
                if(!(room.student.satisfaction.equals("Unsatisfied")) && !(room.student.satisfaction.equals("Pissed"))){
                    signal.setImageResource(R.drawable.redx);
                } else{
                    Toast.makeText(CheckActivity.this, "Student left", Toast.LENGTH_SHORT).show();
                }
                fanLossOdds(3, 6, 2, 3);
                if(fansLost == false && (room.student.satisfaction.equals("Unsatisfied") || room.student.satisfaction.equals("Pissed"))){
                    signal.setImageResource(R.drawable.doublecheck);
                    if(room.student.satisfaction.equals("Unsatisfied")){
                        studentArea.setImageResource(R.drawable.straightface);
                    } else if(room.student.satisfaction.equals("Pissed")){
                        studentArea.setImageResource(R.drawable.unhappyface);
                    }
                }
checkIntent.putExtra("room", room.roomNumber);
                handler.postDelayed(switchToMainActivity, 3000);
            }
        });
    }
    private void button1Action(){
        int extensionChance = (int)(Math.random() * 10) + 1;
        if(room.student.satisfaction.equals("Happy") || room.student.satisfaction.equals("Stoked")){
            if (extensionChance <= adjustExtensionLikelihoodBasedOnPay() && room.partyCancelled == false && room.extensionCancelled == false) {
                Toast.makeText(CheckActivity.this, "Session Extended!", Toast.LENGTH_SHORT).show();
                SoundPlayer.soundPool.play(correctSound, 1, 1, 0, 0, 1);
                checkIntent.putExtra("room", room.roomNumber);
                signal.setImageResource(R.drawable.doublecheck);
                studentArea.setImageResource(R.drawable.partysatisfaction);
                findViewById(R.id.checkgradient).setVisibility(View.VISIBLE);
                handler.postDelayed(switchToMainActivityWithExtension, 3000);
            } else {
                Toast.makeText(CheckActivity.this, "Offer Declined", Toast.LENGTH_SHORT).show();
                int[] fanOdds, fansToEarn;
                if(room.student.satisfaction.equals("Stoked")){
                    fanOdds = new int[]{7, 4, 1};
                    fansToEarn = new int[]{5, 3, 2};
                } else{
                    fanOdds = new int[]{6, 3, 0};
                    fansToEarn = new int[]{3, 3, 0};
                }
                fanIncreaseOdds(fanOdds[0], fanOdds[1], fanOdds[2], fansToEarn[0], fansToEarn[1], fansToEarn[2]);
                signal.setImageResource(R.drawable.redx);
                checkIntent.putExtra("room", room.roomNumber);
                handler.postDelayed(switchToMainActivity, 3000);
            }
        } else{
            Toast.makeText(CheckActivity.this, "Offer Declined", Toast.LENGTH_SHORT).show();
            if(!(room.student.satisfaction.equals("Mediocre"))){
                fanLossOdds(3, 7, 2, 3);
            }
            signal.setImageResource(R.drawable.redx);
            checkIntent.putExtra("room", room.roomNumber);
            handler.postDelayed(switchToMainActivity, 3000);
        }
    }
    private int determinePayRequirements(){
        if(room.student.wealth.equals("Poor") && room.student.satisfaction.equals("Stoked")){
            if(room.student.sessionLength == 60){
                extensionOdds = 4;
                return 3480;
            } else{
                extensionOdds = 4;
                return 1740;
            }
        } else if(room.student.wealth.equals("Poor") && room.student.satisfaction.equals("Happy")){
            if(room.student.sessionLength == 60){
                extensionOdds = 3;
                return 2320;
            } else{
                extensionOdds = 3;
                return 1160;
            }
        } else if(room.student.wealth.equals("Poor") && room.student.satisfaction.equals("Mediocre")){
            if(room.student.sessionLength == 60){
                return 1840;
            } else{
              return 920;
            }
        } else if(room.student.wealth.equals("Average") && room.student.satisfaction.equals("Stoked")){
            if(room.student.sessionLength == 60){
                extensionOdds = 5;
                return 13920;
            } else{
                extensionOdds = 5;
                return 6960;
            }
        } else if(room.student.wealth.equals("Average") && room.student.satisfaction.equals("Happy")){
            if(room.student.sessionLength == 60){
                extensionOdds = 4;
                return 9280;
            } else{
                extensionOdds = 4;
                return 4640;
            }
        } else if(room.student.wealth.equals("Average") && room.student.satisfaction.equals("Mediocre")){
            if(room.student.sessionLength == 60){
                return 7400;
            } else{
                return 3700;
            }
        } else if(room.student.wealth.equals("Wealthy") && room.student.satisfaction.equals("Stoked")){
            if(room.student.sessionLength == 60){
                extensionOdds = 6;
                return 24400;
            } else{
                extensionOdds = 6;
                return 12200;
            }
        } else if(room.student.wealth.equals("Wealthy") && room.student.satisfaction.equals("Happy")){
            if(room.student.sessionLength == 60){
                extensionOdds = 5;
                return 16280;
            } else{
                extensionOdds = 5;
                return 8140;
            }
        } else if(room.student.wealth.equals("Wealthy") && room.student.satisfaction.equals("Mediocre")){
            if(room.student.sessionLength == 60){
                return 13000;
            } else{
                return 6500;
            }
        } else{
            return 0;
        }
    }
    private int adjustExtensionLikelihoodBasedOnPay(){
        if(room.earnings > determinePayRequirements()){
            return extensionOdds;
        } else if(room.earnings >= (determinePayRequirements() / 2) && room.earnings < determinePayRequirements()){
            extensionOdds--;
            return extensionOdds;
        } else{
            extensionOdds -= 2;
            return extensionOdds;
        }
    }
    private void fanIncreaseOdds(int odd1, int odd2, int odd3, int fansToEarn1, int fansToEarn2, int fansToEarn3){
        int positiveReviewChance = (int)(Math.random() * 10) + 1;
        if(room.earnings > determinePayRequirements()){
            if(positiveReviewChance <= odd1){
                fans += fansToEarn1;
                checkIntent.putExtra("fans", fans);
                Toast.makeText(CheckActivity.this, "Student left a positive review", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(CheckActivity.this, "Student left satisfied", Toast.LENGTH_SHORT).show();
            }
        } else if(room.earnings >= (determinePayRequirements() / 2) && room.earnings < determinePayRequirements()){
            if(positiveReviewChance <= odd2){
                fans += fansToEarn2;
                checkIntent.putExtra("fans", fans);
                Toast.makeText(CheckActivity.this, "Student left a positive review", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(CheckActivity.this, "Student left satisfied", Toast.LENGTH_SHORT).show();
            }
        } else{
            if(positiveReviewChance <= odd3){
                fans += fansToEarn3;
                checkIntent.putExtra("fans", fans);
                Toast.makeText(CheckActivity.this, "Student left a positive review", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(CheckActivity.this, "Student left satisfied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void fanLossOdds(int odds1, int odds2, int fansToLose1, int fansToLose2){
        int negativeReviewChance = (int)(Math.random() * 10) + 1;
        if(room.student.satisfaction.equals("Unsatisfied")){
            if(negativeReviewChance <= odds1){
                if(fans <= fansToLose1){
                    fans = 0;
                    fansLost = true;
                    signal.setImageResource(R.drawable.redx);
                    Toast.makeText(CheckActivity.this, "Student left a negative review", Toast.LENGTH_SHORT).show();
                } else{
                    fans -= fansToLose1;
                    fansLost = true;
                    signal.setImageResource(R.drawable.redx);
                    Toast.makeText(CheckActivity.this, "Student left a negative review", Toast.LENGTH_SHORT).show();
                }
                checkIntent.putExtra("fans", fans);
            } else{
                Toast.makeText(CheckActivity.this, "Student's satisfaction improved slightly", Toast.LENGTH_SHORT).show();
            }
        } else if(room.student.satisfaction.equals("Pissed")){
            if(negativeReviewChance <= odds2){
                if(fans <= fansToLose2){
                    fans = 0;
                    fansLost = true;
                    signal.setImageResource(R.drawable.redx);
                    Toast.makeText(CheckActivity.this, "Student left a negative review", Toast.LENGTH_SHORT).show();
                } else{
                    fans -= fansToLose2;
                    fansLost = true;
                    signal.setImageResource(R.drawable.redx);
                    Toast.makeText(CheckActivity.this, "Student left a negative review", Toast.LENGTH_SHORT).show();
                }
                checkIntent.putExtra("fans", fans);
            } else{
                Toast.makeText(CheckActivity.this, "Student left unsatisfied", Toast.LENGTH_SHORT).show();
            }
        }
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
        healthBar = findViewById(R.id.checkhealthbar);
        healthBar.setMax(room.student.teacher.maxHealth);
        healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
        teacherArea = findViewById(R.id.checkteacherarea);
        teacherArea.setBackgroundResource(room.student.teacher.teacherImage);
        wealthText = findViewById(R.id.checkwealth);
        wealthText.setText(room.student.wealth);
        earningsText = findViewById(R.id.checktotalearnings);
        earningsText.setText("$" + String.valueOf(earnings));
        roomEarningsText = findViewById(R.id.checkroomearnings);
        roomEarningsText.setText("$" + String.valueOf(room.earnings));
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
        outState.putBoolean("fanslost", fansLost);
        outState.putBoolean("buttonclickability", button1.isClickable());
        outState.putInt("gradientvisibility", findViewById(R.id.checkgradient).getVisibility());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        earnings = savedInstanceState.getInt("earnings", 0);
        fans = savedInstanceState.getInt("fans", 0);
        songPosition = savedInstanceState.getInt("songposition", songPosition);
        buttonClicked = savedInstanceState.getInt("buttonclicked", 0);
        fansLost = savedInstanceState.getBoolean("fanslost", false);
        if(getIntent().getIntExtra("Music", 0) == 2){
            SoundPlayer.playSong(this, R.raw.onfire, songPosition);
        } else{
            SoundPlayer.playSong(this, R.raw.straightfuse, songPosition);
        }
        if(savedInstanceState.getInt("gradientvisibility", 0) == View.VISIBLE){
            signal.setImageResource(R.drawable.doublecheck);
            studentArea.setImageResource(R.drawable.partysatisfaction);
            findViewById(R.id.checkgradient).setVisibility(View.VISIBLE);
            checkIntent.putExtra("room", room.roomNumber);
            handler.postDelayed(switchToMainActivityWithExtension, 2000);
        } else if(savedInstanceState.getInt("gradientvisibility", 0) == View.INVISIBLE && savedInstanceState.getBoolean("buttonclickability", false) == false){
            for(Button button : buttons){
                button.setClickable(false);
            }
            if(buttonClicked == 1){
signal.setImageResource(R.drawable.redx);
            } else if(buttonClicked == 2){
                if(room.student.satisfaction.equals("Stoked")){
                    signal.setImageResource(R.drawable.doublecheck);
                } else if(room.student.satisfaction.equals("Happy")){
                    signal.setImageResource(R.drawable.doublecheck);
                    studentArea.setImageResource(R.drawable.stokedface);
                } else if(room.student.satisfaction.equals("Mediocre")){
                    signal.setImageResource(R.drawable.doublecheck);
                    studentArea.setImageResource(R.drawable.happyface);
                } else{
                    if(fansLost == false){
                        signal.setImageResource(R.drawable.doublecheck);
                        if(room.student.satisfaction.equals("Unsatisfied")){
                            studentArea.setImageResource(R.drawable.straightface);
                        } else{
                            studentArea.setImageResource(R.drawable.unhappyface);
                        }
                    } else{
                        signal.setImageResource(R.drawable.redx);
                    }
                }
                checkIntent.putExtra("giftcosts", 870);
            } else if(buttonClicked == 3){
                if(room.student.satisfaction.equals("Stoked")){
                    signal.setImageResource(R.drawable.singlecheck);
                } else if(room.student.satisfaction.equals("Happy")){
                    signal.setImageResource(R.drawable.singlecheck);
                    studentArea.setImageResource(R.drawable.stokedface);
                } else if(room.student.satisfaction.equals("Mediocre")){
                    signal.setImageResource(R.drawable.singlecheck);
                    studentArea.setImageResource(R.drawable.happyface);
                } else{
                    signal.setImageResource(R.drawable.redx);
                }
            } else{
                if(!(room.student.satisfaction.equals("Unsatisfied")) && !(room.student.satisfaction.equals("Pissed"))){
                    signal.setImageResource(R.drawable.redx);
                }
                if(fansLost == false && (room.student.satisfaction.equals("Unsatisfied") || room.student.satisfaction.equals("Pissed"))){
                    signal.setImageResource(R.drawable.doublecheck);
                    if(room.student.satisfaction.equals("Unsatisfied")){
                        studentArea.setImageResource(R.drawable.straightface);
                    } else if(room.student.satisfaction.equals("Pissed")){
                        studentArea.setImageResource(R.drawable.unhappyface);
                    }
                } else if(fansLost){
                    signal.setImageResource(R.drawable.redx);
                }
            }
            checkIntent.putExtra("fans", fans);
            checkIntent.putExtra("room", room.roomNumber);
            handler.postDelayed(switchToMainActivity, 2000);
        }
    }
}