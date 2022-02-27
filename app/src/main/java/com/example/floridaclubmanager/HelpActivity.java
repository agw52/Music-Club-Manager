package com.example.floridaclubmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
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

import java.util.ArrayList;
import java.util.Arrays;

public class HelpActivity extends AppCompatActivity {
    ImageView tabletGif, studentArea, teacherArea, signal;
TextView wealth, totalEarningsText, roomEarningsText, partyLevel;
Room room;
VideoView background;
Button button1, button2, button3, button4;
ProgressBar partyBar, healthBar;
CountDownTimer helpCountDownTimer;
long TIME_LEFT_IN_MILLIS;
int answerTimer, songPosition;
int sounds[];
boolean satisfactionDecreased, lateAnswer, increasedToStoked, returningToMainActivity;
String correctButtonText;
Intent answerIntent;
ArrayList<String> answers;
ArrayList<Button> buttons, freeButtons;
Handler handler = new Handler();
Runnable wrongAnswerSwitchToMainActivity = new Runnable() {
    @Override
    public void run() {
  returningToMainActivity = true;
        setResult(RESULT_CANCELED, answerIntent);
  finish();
    }
};
    Runnable correctAnswerSwitchToMainActivity = new Runnable() {
        @Override
        public void run() {
            returningToMainActivity = true;
            setResult(RESULT_OK, answerIntent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_help);
        background = findViewById(R.id.helpbackground);
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
        Intent intent = getIntent();
        answerIntent = new Intent(HelpActivity.this, MainActivity.class);
        sounds = new int[]{SoundPlayer.soundPool.load(this, R.raw.correct, 1),
                SoundPlayer.soundPool.load(this, R.raw.negative, 1)};
        room = intent.getParcelableExtra("Room");
        room.setStudent(intent.getParcelableExtra("Student"));
        room.student.setTeacher(intent.getParcelableExtra("Teacher"));
        partyBar = findViewById(R.id.helppartybar);
        partyBar.setMax(intent.getIntExtra("partybarmax", 0));
        partyBar.setProgress(intent.getIntExtra("partybarprogress", 0));
        partyLevel = findViewById(R.id.helppartylevel);
        if(partyBar.getProgress() >= ((partyBar.getMax() / 3) * 2) && partyBar.getProgress() < partyBar.getMax()){
            partyLevel.setText("LvL. 2");
        } else if(partyBar.getProgress() == partyBar.getMax()){
            partyLevel.setText("LvL. 3");
        }
        tabletGif = findViewById(R.id.tabletgif);
        signal = findViewById(R.id.helpsignal);
        studentArea = findViewById(R.id.helpstudentarea);
        healthBar = findViewById(R.id.helphealthbar);
        healthBar.setMax(room.student.teacher.maxHealth);
        button1 = findViewById(R.id.helpactivitybutton1);
        button2 = findViewById(R.id.helpactivitybutton2);
        button3 = findViewById(R.id.helpactivitybutton3);
        button4 = findViewById(R.id.helpactivitybutton4);
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4));
        answers = new ArrayList<>(Arrays.asList("Fix Instrument", "Clean Instrument", "Adjust Microphone",
                "Increase Speaker Volume", "Provide Metronome", "Replace Sheet Music"));
        freeButtons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4));
        if(savedInstanceState == null){
            healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
            studentArea.setImageResource(room.student.studentImage);
            TIME_LEFT_IN_MILLIS = 9000;
            startTimer();
            assignTexts();
            assignCorrectAnswer();
        }
    }
private void startTimer(){
        helpCountDownTimer = new CountDownTimer(TIME_LEFT_IN_MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                TIME_LEFT_IN_MILLIS = l;
                answerTimer++;
                if(answerTimer == 5){
                    Toast.makeText(HelpActivity.this, correctButtonText + " Please", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish() {
                wrongAnswerAction(1000);
            }
        }.start();
}
    private void assignTexts(){
        for(int i = 0; i < 4; i++){
            int randomButton = (int)(Math.random() * freeButtons.size());
            int randomString = (int)(Math.random() * answers.size());
            freeButtons.get(randomButton).setText(answers.get(randomString));
            freeButtons.remove(randomButton);
            answers.remove(randomString);
        }
    }
    private void assignCorrectAnswer(){
        int randomCorrectButton = (int)(Math.random() * 4);
        int[] gifs = {R.drawable.fixinstrument, R.drawable.cleaninstrument, R.drawable.mic, R.drawable.metronome,
                R.drawable.speaker, R.drawable.sheetmusic};
        if(buttons.get(randomCorrectButton).getText().equals("Fix Instrument")){
            tabletGif.setBackgroundResource(gifs[0]);
        } else if(buttons.get(randomCorrectButton).getText().equals("Clean Instrument")){
            tabletGif.setBackgroundResource(gifs[1]);
        } else if(buttons.get(randomCorrectButton).getText().equals("Adjust Microphone")){
            tabletGif.setBackgroundResource(gifs[2]);
        } else if(buttons.get(randomCorrectButton).getText().equals("Provide Metronome")){
            tabletGif.setBackgroundResource(gifs[3]);
        } else if(buttons.get(randomCorrectButton).getText().equals("Increase Speaker Volume")){
            tabletGif.setBackgroundResource(gifs[4]);
        } else{
            tabletGif.setBackgroundResource(gifs[5]);
        }
        setCorrectAnswerAction(buttons.get(randomCorrectButton));
        correctButtonText = buttons.get(randomCorrectButton).getText().toString();
        setActionForWrongButtons(buttons.get(randomCorrectButton));
    }
private void adjustHealthForCorrectAnswer(){
        if(answerTimer <= 4){
            answerIntent.putExtra("healthincrease", 27);
            if((room.student.teacher.maxHealth - room.student.teacher.health) <= 27){
                room.student.teacher.health = room.student.teacher.maxHealth;
            } else{
                room.student.teacher.health += 27;
            }
            tabletGif.setBackgroundResource(android.R.color.white);
            signal.setImageResource(R.drawable.doublecheck);
            Toast.makeText(HelpActivity.this, "Teacher recovered significantly", Toast.LENGTH_SHORT).show();
        } else{
            answerIntent.putExtra("healthincrease", 15);
            if((room.student.teacher.maxHealth - room.student.teacher.health) <= 15){
                room.student.teacher.health = room.student.teacher.maxHealth;
            } else{
                room.student.teacher.health += 15;
            }
            Toast.makeText(HelpActivity.this, "Teacher recovered slightly", Toast.LENGTH_SHORT).show();
            lateAnswer = true;
            tabletGif.setBackgroundResource(android.R.color.white);
            signal.setImageResource(R.drawable.singlecheck);
        }
        healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
}
private void increaseSatisfaction(){
    if(lateAnswer == false) {
        if (room.student.satisfaction.equals("Unsatisfied")) {
            studentArea.setImageResource(R.drawable.straightface);
        } else if (room.student.satisfaction.equals("Mediocre")) {
            studentArea.setImageResource(R.drawable.happyface);
        } else if (room.student.satisfaction.equals("Happy")) {
            increasedToStoked = true;
            answerIntent.putExtra("increasedtostoked", increasedToStoked);
            studentArea.setImageResource(R.drawable.stokedface);
        }
    }
answerIntent.putExtra("lateanswer", lateAnswer);
answerIntent.putExtra("room", room.roomNumber);
    handler.postDelayed(correctAnswerSwitchToMainActivity, 3000);
}
    private void setCorrectAnswerAction(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adjustHealthForCorrectAnswer();
                helpCountDownTimer.cancel();
                SoundPlayer.soundPool.play(sounds[0], 1, 1, 0, 0, 1);
                increaseSatisfaction();
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);
                button4.setClickable(false);
            }
        });
    }
private void wrongAnswerAction(long returnToMainDelay){
    button1.setClickable(false);
    button2.setClickable(false);
    button3.setClickable(false);
    button4.setClickable(false);
    int satisfactionDecreaseChance = (int) (Math.random() * 10) + 1;
    if(satisfactionDecreaseChance <= 7){
        answerIntent.putExtra("healthdecrease", 15);
        if(room.student.teacher.health <= 15){
             room.student.teacher.health = 0;
        } else{
            room.student.teacher.health -= 15;
        }
        healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
        if(satisfactionDecreaseChance <= 3 && !(room.student.satisfaction.equals("Unsatisfied"))){
            satisfactionDecreased = true;
            if(room.student.satisfaction.equals("Mediocre")){
                studentArea.setImageResource(R.drawable.unhappyface);
            } else if(room.student.satisfaction.equals("Happy")){
                studentArea.setImageResource(R.drawable.straightface);
            } else{
                studentArea.setImageResource(R.drawable.happyface);
            }
        }
    }
    tabletGif.setBackgroundResource(android.R.color.white);
    signal.setImageResource(R.drawable.redx);
    answerIntent.putExtra("satisfactiondecrease", satisfactionDecreased);
    answerIntent.putExtra("room", room.roomNumber);
    handler.postDelayed(wrongAnswerSwitchToMainActivity, returnToMainDelay);
}
private void setActionForWrongButtons(Button correctbutton){
buttons.remove(correctbutton);
for(int i = 0; i < buttons.size(); i++){
    buttons.get(i).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(HelpActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
            SoundPlayer.soundPool.play(sounds[1], 1, 1, 0, 0, 1);
            helpCountDownTimer.cancel();
            wrongAnswerAction(3000);
        }
    });
}
}

    @Override
    protected void onPause() {
        super.onPause();
        if(answerTimer != 9){
            helpCountDownTimer.cancel();
        }
        if(returningToMainActivity == false){
            songPosition = SoundPlayer.player.getCurrentPosition();
            SoundPlayer.player.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        teacherArea = findViewById(R.id.helpteacherarea);
        teacherArea.setBackgroundResource(room.student.teacher.teacherImage);
        totalEarningsText = findViewById(R.id.helptotalearnings);
        totalEarningsText.setText("$" + String.valueOf(getIntent().getIntExtra("earnings", 0)));
        roomEarningsText = findViewById(R.id.helproomearnings);
        roomEarningsText.setText("$" + String.valueOf(room.earnings));
        wealth = findViewById(R.id.helpwealth);
        wealth.setText(room.student.wealth);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(answerTimer != 9){
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
        outState.putLong("timeleft", TIME_LEFT_IN_MILLIS);
        outState.putInt("answertimer", answerTimer);
        outState.putInt("songposition", songPosition);
        outState.putString("correctbuttontext", correctButtonText);
        outState.putBoolean("buttonisclickable", button1.isClickable());
        outState.putBoolean("lateanswer", lateAnswer);
        outState.putBoolean("satisfactiondrop", satisfactionDecreased);
        outState.putBoolean("returningtomainactivity", returningToMainActivity);
        outState.putBoolean("improvedtostoked", increasedToStoked);
        outState.putCharSequence("button1text", button1.getText());
        outState.putCharSequence("button2text", button2.getText());
        outState.putCharSequence("button3text", button3.getText());
        outState.putCharSequence("button4text", button4.getText());
        if(answerIntent.hasExtra("healthincrease")){
            outState.putBoolean("answercorrect", true);
        } else{
            outState.putBoolean("answercorrect", false);
        }
        if(answerIntent.hasExtra("satisfactiondecrease")){
            outState.putBoolean("answerwrong", true);
            if(answerIntent.hasExtra("healthdecrease")){
                outState.putBoolean("healthdropped", true);
            } else{
                outState.putBoolean("healthdropped", false);
            }
        } else{
            outState.putBoolean("answerwrong", false);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TIME_LEFT_IN_MILLIS = savedInstanceState.getLong("timeleft", 0);
        answerTimer = savedInstanceState.getInt("answertimer", 0);
        correctButtonText = savedInstanceState.getString("correctbuttontext");
        lateAnswer = savedInstanceState.getBoolean("answercorrect", false);
        satisfactionDecreased = savedInstanceState.getBoolean("satisfactiondrop", satisfactionDecreased);
        returningToMainActivity = savedInstanceState.getBoolean("returningtomainactivity", false);
        increasedToStoked = savedInstanceState.getBoolean("improvedtostoked", false);
        button1.setText(savedInstanceState.getCharSequence("button1text"));
        button2.setText(savedInstanceState.getCharSequence("button2text"));
        button3.setText(savedInstanceState.getCharSequence("button3text"));
        button4.setText(savedInstanceState.getCharSequence("button4text"));
        if(getIntent().getIntExtra("Music", 0) == 2){
            SoundPlayer.playSong(this, R.raw.onfire, songPosition);
        } else{
            SoundPlayer.playSong(this, R.raw.straightfuse, songPosition);
        }
        if(answerTimer < 9 && savedInstanceState.getBoolean("buttonisclickable", false)){
            if(button1.getText().toString().equals(correctButtonText)){
                setCorrectAnswerAction(button1);
                setActionForWrongButtons(button1);
            } else if(button2.getText().toString().equals(correctButtonText)){
                setCorrectAnswerAction(button2);
                setActionForWrongButtons(button2);
            } else if(button3.getText().toString().equals(correctButtonText)){
                setCorrectAnswerAction(button3);
                setActionForWrongButtons(button3);
            } else{
                setCorrectAnswerAction(button4);
                setActionForWrongButtons(button4);
            }
            if(correctButtonText.equals("Fix Instrument")){
                tabletGif.setBackgroundResource(R.drawable.fixinstrument);
            } else if(correctButtonText.equals("Clean Instrument")){
                tabletGif.setBackgroundResource(R.drawable.cleaninstrument);
            } else if(correctButtonText.equals("Adjust Microphone")){
                tabletGif.setBackgroundResource(R.drawable.mic);
            } else if(correctButtonText.equals("Provide Metronome")){
                tabletGif.setBackgroundResource(R.drawable.metronome);
            } else if(correctButtonText.equals("Increase Speaker Volume")){
                tabletGif.setBackgroundResource(R.drawable.speaker);
            } else{
                tabletGif.setBackgroundResource(R.drawable.sheetmusic);
            }
            startTimer();
        } else if(savedInstanceState.getBoolean("answercorrect", false)){
            adjustHealthForCorrectAnswer();
            increaseSatisfaction();
            button1.setClickable(false);
            button2.setClickable(false);
            button3.setClickable(false);
            button4.setClickable(false);
        } else{
            tabletGif.setBackgroundResource(android.R.color.white);
            signal.setImageResource(R.drawable.redx);
            button1.setClickable(false);
            button2.setClickable(false);
            button3.setClickable(false);
            button4.setClickable(false);
            if(savedInstanceState.getBoolean("healthdropped", false)){
                answerIntent.putExtra("healthdecrease", 15);
                if(room.student.teacher.health <= 15){
                    room.student.teacher.health = 0;
                } else{
                    room.student.teacher.health -= 15;
                }
                healthBar.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
                if(satisfactionDecreased){
                    if(room.student.satisfaction.equals("Mediocre")){
                        studentArea.setImageResource(R.drawable.unhappyface);
                    } else if(room.student.satisfaction.equals("Happy")){
                        studentArea.setImageResource(R.drawable.straightface);
                    } else{
                        studentArea.setImageResource(R.drawable.happyface);
                    }
                }
            }
            answerIntent.putExtra("satisfactiondecrease", satisfactionDecreased);
            answerIntent.putExtra("room", room.roomNumber);
            if(answerTimer == 9){
                handler.postDelayed(wrongAnswerSwitchToMainActivity, 1000);
            } else{
                handler.postDelayed(wrongAnswerSwitchToMainActivity, 2000);
            }
        }
    }
}