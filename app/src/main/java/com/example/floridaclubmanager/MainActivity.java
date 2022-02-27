package com.example.floridaclubmanager;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;



import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    Button partyButton;
    Teacher tracy, eddie, seth, helga, beth, olivia, sarah, rodrick;
    Teacher[] teachers;
    ConstraintLayout mainConstraintLayout, teacherBackgroundLayout;
    View teacherSection;
    VideoView background;
    ProgressBar partyBar, rockProgressBar, classicalProgressBar, grooveProgressBar, jazzProgressBar, healthProgressBar;
    CountDownTimer mainCountdownTimer, postLeaveTimer;
    static long CONSTANT_HELP_DISAPPEAR_TIME, CONSTANT_CHECK_DISAPPEAR_TIME, CONSTANT_EXTENSION_TIME, CONSTANT_SPAWN_TIME, CONSTANT_DESPAWN_TIME;
    TextView countDownView, partyLevel;
    long GAME_TIME, TIME_UNTIL_APPEARANCE, QUICK_APPEARANCE_TIME;
    int earnings, fans, gameTimeInSeconds, giftCosts, songPosition;
    int[] sounds;
    boolean studentQuicklyIncoming, goingIntoActivity, returningFromActivity, activityDestroyed;
    Room room1, room2, room3, room4, room5, room6;
    Room[] rooms;
    ArrayList<Room> partyEligibleRooms, emptyRooms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        teacherSection = findViewById(R.id.teacherbox);
        countDownView = findViewById(R.id.text_view_countdown);
        partyBar = findViewById(R.id.partybar);
        partyButton = findViewById(R.id.partybutton);
        background = findViewById(R.id.gamebackground);
        Uri uri;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mainbackground);
        } else{
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.horizontalmainbackground);
        }
        background.setVideoURI(uri);
        background.start();
        background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
sounds = new int[]{SoundPlayer.soundPool.load(this, R.raw.buzzer, 1), SoundPlayer.soundPool.load(this, R.raw.doublebell, 1),
        SoundPlayer.soundPool.load(this, R.raw.bell, 1), SoundPlayer.soundPool.load(this, R.raw.alert, 1),
        SoundPlayer.soundPool.load(this, R.raw.spawn, 1), SoundPlayer.soundPool.load(this, R.raw.partybuttonsound, 1)};
        CONSTANT_HELP_DISAPPEAR_TIME = 4000;
        CONSTANT_CHECK_DISAPPEAR_TIME = 6000;
        CONSTANT_EXTENSION_TIME = 30000;
        CONSTANT_SPAWN_TIME = 3000;
        CONSTANT_DESPAWN_TIME = 8000;
        if(savedInstanceState == null){
            SoundPlayer.playSong(this, R.raw.straightfuse, 0);
            room1 = new Room(1, findViewById(R.id.teacherarea1), findViewById(R.id.studentarea1), findViewById(R.id.space1), findViewById(R.id.wealth1),
                    findViewById(R.id.room1earnings), findViewById(R.id.room1gradient), findViewById(R.id.countdown1), findViewById(R.id.progressbar1),
                    findViewById(R.id.room1health), findViewById(R.id.extensionprogressbar), findViewById(R.id.normalprogressbar), findViewById(R.id.button1),
                    findViewById(R.id.toswap1), findViewById(R.id.swap1));
            room2 = new Room(2, findViewById(R.id.teacherarea2), findViewById(R.id.studentarea2), findViewById(R.id.space2), findViewById(R.id.wealth2),
                    findViewById(R.id.room2earnings), findViewById(R.id.room2gradient), findViewById(R.id.countdown2), findViewById(R.id.progressbar2),
                    findViewById(R.id.room2health), findViewById(R.id.extensionprogressbar2), findViewById(R.id.normalprogressbar2), findViewById(R.id.button2),
                    findViewById(R.id.toswap2), findViewById(R.id.swap2));
            room3 = new Room(3, findViewById(R.id.teacherarea3), findViewById(R.id.studentarea3), findViewById(R.id.space3), findViewById(R.id.wealth3),
                    findViewById(R.id.room3earnings), findViewById(R.id.room3gradient), findViewById(R.id.countdown3), findViewById(R.id.progressbar3),
                    findViewById(R.id.room3health), findViewById(R.id.extensionprogressbar3), findViewById(R.id.normalprogressbar3), findViewById(R.id.button3),
                    findViewById(R.id.toswap3), findViewById(R.id.swap3));
            room4 = new Room(4, findViewById(R.id.teacherarea4), findViewById(R.id.studentarea4), findViewById(R.id.space4), findViewById(R.id.wealth4),
                    findViewById(R.id.room4earnings), findViewById(R.id.room4gradient), findViewById(R.id.countdown4), findViewById(R.id.progressbar4),
                    findViewById(R.id.room4health), findViewById(R.id.extensionprogressbar4), findViewById(R.id.normalprogressbar4), findViewById(R.id.button4),
                    findViewById(R.id.toswap4), findViewById(R.id.swap4));
            room5 = new Room(5, findViewById(R.id.teacherarea5), findViewById(R.id.studentarea5), findViewById(R.id.space5), findViewById(R.id.wealth5),
                    findViewById(R.id.room5earnings), findViewById(R.id.room5gradient), findViewById(R.id.countdown5), findViewById(R.id.progressbar5),
                    findViewById(R.id.room5health), findViewById(R.id.extensionprogressbar5), findViewById(R.id.normalprogressbar5), findViewById(R.id.button5),
                    findViewById(R.id.toswap5), findViewById(R.id.swap5));
            room6 = new Room(6, findViewById(R.id.teacherarea6), findViewById(R.id.studentarea6), findViewById(R.id.space6), findViewById(R.id.wealth6),
                    findViewById(R.id.room6earnings), findViewById(R.id.room6gradient), findViewById(R.id.countdown6), findViewById(R.id.progressbar6),
                    findViewById(R.id.room6health), findViewById(R.id.extensionprogressbar6), findViewById(R.id.normalprogressbar6), findViewById(R.id.button6),
                    findViewById(R.id.toswap6), findViewById(R.id.swap6));
            rooms = new Room[]{room1, room2, room3, room4, room5, room6};
            partyEligibleRooms = new ArrayList<>();
            emptyRooms = new ArrayList<>(Arrays.asList(room1, room2, room3, room4, room5, room6));
            fans = 10;
            GAME_TIME = 210000;
            gameTimeInSeconds = 210;
            for(Room room : rooms){
                room.HELP_DISAPPEAR_TIME = CONSTANT_HELP_DISAPPEAR_TIME;
                room.CHECK_DISAPPEAR_LEFT_IN_MILLIS = CONSTANT_CHECK_DISAPPEAR_TIME;
            }
            tracy = new Teacher("Tracy", 'D', 'A', 'B', 'B',
                    90, 70, 60, 50, 110, R.drawable.tracy, findViewById(R.id.tracyimage));
            eddie = new Teacher("Eddie", 'D', 'D', 'C', 'A',
                    90, 50, 40, 80, 135, R.drawable.eddie, findViewById(R.id.eddieimage));
            seth = new Teacher("Seth", 'B', 'A', 'D', 'C',
                    40, 40, 70, 90, 130, R.drawable.seth, findViewById(R.id.sethimage));
            helga = new Teacher("Helga", 'B', 'D', 'A', 'C',
                    50, 70, 70, 90, 120, R.drawable.helga, findViewById(R.id.helgaimage));
            beth = new Teacher("Beth", 'C', 'B', 'C', 'A',
                    70, 90, 50, 40, 125, R.drawable.beth, findViewById(R.id.bethimage));
            olivia = new Teacher("Olivia", 'A', 'C', 'B', 'D',
                    70, 90, 80, 70, 110, R.drawable.olivia, findViewById(R.id.oliviaimage));
            sarah = new Teacher("Sarah", 'C', 'B', 'A', 'D',
                    80, 60, 90, 60, 125, R.drawable.sarah, findViewById(R.id.sarahimage));
            rodrick = new Teacher("Rodrick", 'A', 'C', 'D', 'B',
                    60, 80, 90, 70, 130, R.drawable.rodrick, findViewById(R.id.rodrickimage));
            teachers = new Teacher[]{tracy, eddie, seth, helga, beth, olivia, sarah, rodrick};
            int randomFirstSpawnTime = (int)(Math.random() * 3) + 2;
            int randomRoom = pickRandomRoomForSpawn();
            emptyRooms.get(randomRoom).SPAWN_TIME_LEFT_IN_MILLIS = (randomFirstSpawnTime * 1000);
            emptyRooms.get(randomRoom).studentIncoming = true;
            randomNextStudent(emptyRooms.get(randomRoom));
            mainTimer();
        }
    }
    private void mainTimer(){
        mainCountdownTimer = new CountDownTimer(GAME_TIME, 1000) {
            @Override
            public void onTick(long l) {
                if((TimeUnit.MILLISECONDS.toSeconds(GAME_TIME) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(GAME_TIME))) < 10){
                    countDownView.setText("" + String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes(GAME_TIME), TimeUnit.MILLISECONDS.toSeconds(GAME_TIME)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(GAME_TIME))));
                } else{
                    countDownView.setText("" + String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(GAME_TIME), TimeUnit.MILLISECONDS.toSeconds(GAME_TIME) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(GAME_TIME))));
                }
                GAME_TIME = l;
                gameTimeInSeconds--;
                if((gameTimeInSeconds % 2) == 0){
                    for(Teacher teacher : teachers){
                        if(teacher.health != teacher.maxHealth && teacher.isAssigned == false){
                            teacher.increaseHealth(2);
                        }
                    }
                }
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, EndResultsActivity.class);
                SoundPlayer.soundPool.play(sounds[0], 1, 1, 0, 0, 1);
                countDownView.setText("0:00");
                intent.putExtra("earnings", earnings);
                intent.putExtra("giftcosts", giftCosts);
                startActivity(intent);
                finish();
            }
        }.start();
    }

   private final class OnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if ((motionEvent.getAction() == MotionEvent.ACTION_DOWN) && ((ImageView) view).getDrawable() != null) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }

        }
    }

    private class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            GifImageView[] highlights = {findViewById(R.id.hornshighlight), findViewById(R.id.stringshighlight),
                    findViewById(R.id.pianohighlight), findViewById(R.id.percussionhighlight), findViewById(R.id.groovehighlight),
                    findViewById(R.id.jazzhighlight), findViewById(R.id.classicalhighlight), findViewById(R.id.rockhighlight)};
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    ImageView draggedImage = (ImageView) dragEvent.getLocalState();
                    mainConstraintLayout.setVisibility(View.VISIBLE);
                    teacherBackgroundLayout.setVisibility(View.INVISIBLE);
                    for(Teacher teacher : teachers){
                        teacher.imageView.setVisibility(View.INVISIBLE);
                    }
                    enterDragAction(getDraggedTeacher(draggedImage), view);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    mainConstraintLayout.setVisibility(View.INVISIBLE);
teacherBackgroundLayout.setVisibility(View.VISIBLE);
for(GifImageView highlight : highlights){
    highlight.setVisibility(View.INVISIBLE);
}
                    if(MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                        teacherSection.setBackgroundResource(R.drawable.blankyellow);
                    } else{
                        teacherSection.setBackgroundResource(R.drawable.yellowwide);
                    }
                    teacherReturnsToTeacherArea();
                    getTargetRoom(view).studentArea.setImageResource(getTargetRoom(view).student.studentImage);
                    break;
                case DragEvent.ACTION_DROP:
                    for(Teacher teacher : teachers){
                        teacher.imageView.setEnabled(false);
                    }
                    mainConstraintLayout.setVisibility(View.INVISIBLE);
                    teacherBackgroundLayout.setVisibility(View.VISIBLE);
                    for(GifImageView highlight : highlights){
                        highlight.setVisibility(View.INVISIBLE);
                    }
                    if(MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                        teacherSection.setBackgroundResource(R.drawable.blankpurple);
                    } else{
                        teacherSection.setBackgroundResource(R.drawable.purplewide);
                    }
                    if(getTargetRoom(view).partyActivated){
                        getTargetRoom(view).partyTimer.cancel();
                        getTargetRoom(view).partyActivated = false;
                        if(room1.partyActivated == false && room2.partyActivated == false && room3.partyActivated == false && room4.partyActivated == false &&
                                room5.partyActivated == false && room6.partyActivated == false){
                            SoundPlayer.player.release();
                            SoundPlayer.playSong(MainActivity.this, R.raw.straightfuse, 0);
                        }
                        getTargetRoom(view).SESSION_TIME_LEFT_IN_MILLIS = getTargetRoom(view).PARTY_LEFT_IN_MILLIS;
                        getTargetRoom(view).gradient.setVisibility(View.INVISIBLE);
                        getTargetRoom(view).timerRunning = true;
                        getTargetRoom(view).partyCancelled = true;
                        partyEligibleRooms.add(getTargetRoom(view));
                    }
                    if(getTargetRoom(view).extensionRunning){
                        getTargetRoom(view).extensionTimer.cancel();
                        getTargetRoom(view).extensionRunning = false;
                        getTargetRoom(view).SESSION_TIME_LEFT_IN_MILLIS = room1.EXTENSION_LEFT_IN_MILLIS;
                        getTargetRoom(view).EXTENSION_LEFT_IN_MILLIS = CONSTANT_EXTENSION_TIME;
                        getTargetRoom(view).gradient.setVisibility(View.INVISIBLE);
                        getTargetRoom(view).timerRunning = true;
                        getTargetRoom(view).extensionCancelled = true;
                        partyEligibleRooms.add(getTargetRoom(view));
                    }
                    resumeGame();
                    ImageView target = (ImageView) view;
                    ImageView draggedImageView = (ImageView) dragEvent.getLocalState();
                    Drawable draggedDrawawble = draggedImageView.getDrawable();
                    target.setImageDrawable(draggedDrawawble);
                    draggedImageView.setImageResource(android.R.color.transparent);
                    dropAction(getDraggedTeacher(draggedImageView), target);
                    teacherReturnsToTeacherArea();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    break;
            }
            return true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Teacher getDraggedTeacher(ImageView teacherImage){
        if(teacherImage == findViewById(R.id.tracyimage)){
            return tracy;
        } else if(teacherImage == findViewById(R.id.eddieimage)){
            return eddie;
        } else if(teacherImage == findViewById(R.id.sethimage)){
            return seth;
        } else if(teacherImage == findViewById(R.id.sarahimage)){
            return sarah;
        } else if(teacherImage == findViewById(R.id.bethimage)){
            return beth;
        } else if(teacherImage == findViewById(R.id.rodrickimage)){
            return rodrick;
        } else if(teacherImage == findViewById(R.id.oliviaimage)){
            return olivia;
        } else{
            return helga;
        }
    }
    private Room getTargetRoom(View room){
        if(room == room1.teacherArea){
            return room1;
        } else if(room == room2.teacherArea){
            return room2;
        } else if(room == room3.teacherArea){
            return room3;
        } else if(room == room4.teacherArea){
            return room4;
        } else if(room == room5.teacherArea){
            return room5;
        } else{
            return room6;
        }
    }
    private void highlightInstrumentAndStyle(Student student) {
        if (student.instrumentOfChoice.equals("Horns")) {
            findViewById(R.id.hornshighlight).setVisibility(View.VISIBLE);
        } else if(student.instrumentOfChoice.equals("Piano")){
            findViewById(R.id.pianohighlight).setVisibility(View.VISIBLE);
        } else if(student.instrumentOfChoice.equals("Strings")){
            findViewById(R.id.stringshighlight).setVisibility(View.VISIBLE);
        } else{
            findViewById(R.id.percussionhighlight).setVisibility(View.VISIBLE);
        }
        if(student.preferredMusicStyle.equals("Classical")){
            findViewById(R.id.classicalhighlight).setVisibility(View.VISIBLE);
        } else if(student.preferredMusicStyle.equals("Groove")){
            findViewById(R.id.groovehighlight).setVisibility(View.VISIBLE);
        } else if(student.preferredMusicStyle.equals("Jazz")){
            findViewById(R.id.jazzhighlight).setVisibility(View.VISIBLE);
        } else{
            findViewById(R.id.rockhighlight).setVisibility(View.VISIBLE);
        }
    }
private static int determineStudentSpawnTime(int fans){
        if(fans == 0){
        return 8000;
    } else if(fans > 0 && fans < 20){
        return 7000;
    } else if(fans >= 20 && fans < 30){
        return 6000;
    } else{
        return 5000;
    }
}
private static int determineQuickSpawnTime(int fans){
    if(fans >= 0 && fans < 10){
        return  4000;
    } else if(fans >= 10 && fans < 50){
        return 3000;
    } else{
        return  2000;
    }
}
    private int pickRandomRoomForSpawn(){
        int randomRoom = (int)(Math.random() * emptyRooms.size());
        return randomRoom;
    }
    private void randomNextStudent(Room room){
        room.incomingStudentTimer = new CountDownTimer(room.SPAWN_TIME_LEFT_IN_MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                room.SPAWN_TIME_LEFT_IN_MILLIS = l;
            }

            @Override
            public void onFinish() {
                TIME_UNTIL_APPEARANCE = CONSTANT_SPAWN_TIME;
                room.studentIncoming = false;
                if(GAME_TIME > 5000){
                    studentSpawns(room);
                }
            }
        }.start();
    }
    private void studentSpawns(Room room){
       int randomStudent = (int)(Math.random() * 3);
       int randomInstrument = (int)(Math.random() * 4);
        int randomStyle = (int)(Math.random()*4);
        String[] instruments = {"Horns", "Percussions", "Strings", "Piano"};
        String[] styles = {"Classical", "Jazz", "Groove", "Rock"};
        Student[] studentArray = {new PoorStudent(R.drawable.straightface, "Poor", instruments[randomInstrument], styles[randomStyle]),
                new AverageStudent(R.drawable.straightface, "Average", instruments[randomInstrument], styles[randomStyle]),
                new WealthyStudent(R.drawable.straightface, "Wealthy", instruments[randomInstrument], styles[randomStyle])};
        if(room.studentAppearing == false){
            emptyRooms.remove(room);
            room.studentAppearing = true;
            room.spawnCountdown = 3;
            room.countdown.setVisibility(View.VISIBLE);
            if(emptyRooms.size() >= 1){
                int randomRoom = pickRandomRoomForSpawn();
                emptyRooms.get(randomRoom).SPAWN_TIME_LEFT_IN_MILLIS = determineStudentSpawnTime(fans);
                emptyRooms.get(randomRoom).studentIncoming = true;
                randomNextStudent(emptyRooms.get(randomRoom));
            }
        }
        room.studentAppearTimer = new CountDownTimer(TIME_UNTIL_APPEARANCE, 1000) {
            @Override
            public void onTick(long l) {
                TIME_UNTIL_APPEARANCE = l;
                if(room.spawnCountdown > 0){
                    room.countdown.setText(String.valueOf(room.spawnCountdown));
                }
                room.spawnCountdown--;
            }

            @Override
            public void onFinish() {
                room.studentAppearing = false;
                room.setStudent(studentArray[randomStudent]);
                room.studentArea.setImageResource(room.student.studentImage);
                room.wealth.setVisibility(View.VISIBLE);
                room.wealth.setText(room.student.wealth);
                room.roomEarnings.setVisibility(View.VISIBLE);
                room.student.displayPreferredStyle(room);
                SoundPlayer.soundPool.play(sounds[4], 1, 1, 0, 0, 1);
                room.teacherArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        teacherAreaAction(room);
                    }
                });
                studentStartsToLeave(room);
            }
        }.start();
    }
    private void assignStudentAtVariedRate(){
        QUICK_APPEARANCE_TIME = determineQuickSpawnTime(fans);
        studentQuicklyIncoming = true;
        nextQuicklyIncoming();
    }
    private void nextQuicklyIncoming(){
        postLeaveTimer = new CountDownTimer(QUICK_APPEARANCE_TIME, 1000) {
            @Override
            public void onTick(long l) {
                QUICK_APPEARANCE_TIME = l;
            }

            @Override
            public void onFinish() {
                TIME_UNTIL_APPEARANCE = CONSTANT_SPAWN_TIME;
                if(GAME_TIME > 5000){
                    studentSpawns(emptyRooms.get(pickRandomRoomForSpawn()));
                }
                studentQuicklyIncoming = false;
            }
        }.start();
    }
    private void studentStartsToLeave(Room room){
        room.studentDespawning = true;
        room.student.leaveCountdown = 8;
        room.DESPAWN_TIME_LEFT_IN_MILLIS = CONSTANT_DESPAWN_TIME;
        studentDespawns(room);
    }
    private void studentDespawns(Room room){
        room.studentDespawnTimer = new CountDownTimer(room.DESPAWN_TIME_LEFT_IN_MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                room.DESPAWN_TIME_LEFT_IN_MILLIS = l;
                if(room.student.leaveCountdown > 0){
                    room.countdown.setText(String.valueOf(room.student.leaveCountdown));
                }
                room.student.leaveCountdown--;
                if(room.DESPAWN_TIME_LEFT_IN_MILLIS <= 1000){
                    room.studentArea.setImageResource(R.drawable.pissedface);
                    room.teacherArea.setClickable(false);
                }
            }

            @Override
            public void onFinish() {
                room.studentDespawning = false;
                room.countdown.setVisibility(View.INVISIBLE);
                room.space.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9FCAFF")));
                clearRoom(room);
                if(fans <= 5){
                    fans = 0;
                } else{
                    fans -= 5;
                }
            }
        }.start();
    }
    private void pauseGame(){
            mainCountdownTimer.cancel();
        if(studentQuicklyIncoming){
            postLeaveTimer.cancel();
        }
            for(Room room : rooms){
                if(room.timerRunning){
                    room.sessionTimer.cancel();
                }
                if(room.helpRunning){
                    room.helpTimer.cancel();
                }
                if(room.helpDisappearing){
                    room.helpDisappear.cancel();
                }
                if(room.checkDisappearing){
                    room.checkDisappear.cancel();
                }
                if(room.troubleRunning){
                    room.troubleTimer.cancel();
                }
                if(room.partyActivated){
                    room.partyTimer.cancel();
                }
                if(room.extensionRunning){
                    room.extensionTimer.cancel();
                }
                if(room.studentAppearing){
                    room.studentAppearTimer.cancel();
                }
                if(room.studentDespawning){
                    room.studentDespawnTimer.cancel();
                }
                if(room.studentIncoming){
                    room.incomingStudentTimer.cancel();
                }
                if(room.teacherHealthIsRapidlyDecreasing){
                    room.rapidHealthDecrease.cancel();
                }
            }
    }
    private void resumeGame(){
        mainTimer();
        if(studentQuicklyIncoming){
            nextQuicklyIncoming();
        }
        for (Room room : rooms){
            if(room.teacherAreaClicked){
                room.teacherAreaClicked = false;
                room.space.setBackgroundResource(R.drawable.alternativetable);
                room.space.setScaleX(0.77f);
                room.space.setScaleY(0.64f);
            }
            if(room.timerRunning){
                startTimer(room);
            }
            if(room.helpRunning){
                helpTimer(room);
            }
            if(room.helpDisappearing){
                makeHelpDisappear(room);
            }
            if(room.checkDisappearing){
                makeCheckDisappear(room);
            }
            if(room.troubleRunning){
                startTrouble(room);
            }
            if(room.partyActivated){
                partyMode(room);
            }
            if(room.extensionRunning){
                startExtensionTimer(room);
            }
            if(room.studentAppearing){
                studentSpawns(room);
            }
            if(room.studentDespawning){
                studentDespawns(room);
            }
            if(room.studentIncoming){
                randomNextStudent(room);
            }
            if(room.previewed){
                room.studentArea.setImageResource(room.student.studentImage);
                room.previewed = false;
            }
            if(room.teacherHealthIsRapidlyDecreasing){
                makeHealthRapidlyDecrease(room);
            }
            room.teacherArea.setOnDragListener(null);
            if((room.button.getVisibility() == View.VISIBLE) && room.button.isClickable() == false){
                room.button.setClickable(true);
            }
            room.toSwap.setVisibility(View.INVISIBLE);
            room.swap.setVisibility(View.INVISIBLE);
        }
        if(partyBar.getProgress() >= (partyBar.getMax() / 3) && partyButton.isClickable() == false){
            partyButton.setClickable(true);
        }
    }
    private Room[] returnAllOtherRooms(Room room){
        Room[] roomArray;
        if(room == room1){
            roomArray = new Room[]{room2, room3, room4, room5, room6};
        } else if(room == room2){
            roomArray = new Room[]{room1, room3, room4, room5, room6};
        } else if(room == room3){
            roomArray = new Room[]{room1, room2, room4, room5, room6};
        } else if(room == room4){
            roomArray = new Room[]{room1, room2, room3, room5, room6};
        } else if(room == room5){
            roomArray = new Room[]{room1, room2, room3, room4, room6};
        } else{
            roomArray = new Room[]{room1, room2, room3, room4, room5};
        }
        return roomArray;
    }
    private void teacherAreaAction(Room room){
        if(room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false && room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false){
            room.teacherAreaClicked = true;
            for(Room area : rooms){
                if(area.button.getVisibility() == View.VISIBLE){
                    area.button.setClickable(false);
                }
            }
            if(partyButton.isClickable()){
                partyButton.setClickable(false);
            }
            for(int i = 0; i < returnAllOtherRooms(room).length; i++){
                if(returnAllOtherRooms(room)[i].hasTeacher && returnAllOtherRooms(room)[i].checkDisappearing == false &&
                        (!(returnAllOtherRooms(room)[i].button.getVisibility() == View.VISIBLE && returnAllOtherRooms(room)[i].button.getText().equals("TROUBLE")))){
                    if(room.hasTeacher){
                        returnAllOtherRooms(room)[i].toSwap.setVisibility(View.VISIBLE);
                        returnAllOtherRooms(room)[i].toSwap.bringToFront();
                        int otherRoomNumber = i;
                        returnAllOtherRooms(room)[i].toSwap.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                toSwapButtonAction(returnAllOtherRooms(room)[otherRoomNumber], room);
                            }
                        });
                    }
                }
            }
            room.teacherArea.setOnDragListener(new ChoiceDragListener());
            for(Teacher teacher : teachers){
                if(teacher.isAssigned == false){
                    teacher.imageView.setEnabled(true);
                    teacher.imageView.setOnTouchListener(new OnTouchListener());
                }
            }
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                teacherSection.setBackgroundResource(R.drawable.blankyellow);
            } else{
                teacherSection.setBackgroundResource(R.drawable.yellowwide);
            }
            room.space.setBackgroundTintList(null);
            room.space.setBackgroundResource(R.drawable.semitransparentyellowtable);
            room.space.setScaleX(0.82f);
            room.space.setScaleY(0.73f);
            pauseGame();
        } else{
            for(Teacher teacher : teachers){
                teacher.imageView.setEnabled(false);
            }
            for(Room area : rooms){
                area.toSwap.setVisibility(View.INVISIBLE);
                area.swap.setVisibility(View.INVISIBLE);
            }
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                teacherSection.setBackgroundResource(R.drawable.blankpurple);
            } else{
                teacherSection.setBackgroundResource(R.drawable.purplewide);
            }
            if(room.hasTeacher == false){
                room.student.displayPreferredStyle(room);
            }
            resumeGame();
        }
    }
    private void enterDragAction(Teacher teacher, View teacherArea){
        healthProgressBar.getLayoutParams().width = (int)((teacher.maxHealth - 10) * (((float) this.getResources().getDisplayMetrics()
                .densityDpi) / 160.0f));
        healthProgressBar.requestLayout();
        healthProgressBar.setMax(teacher.maxHealth);
        healthProgressBar.setProgress(teacher.maxHealth - teacher.health);
        grooveProgressBar.getLayoutParams().width = (int)((teacher.maxGroove + 40) * (((float) this.getResources()
                .getDisplayMetrics().densityDpi) / 160.0f));
        grooveProgressBar.requestLayout();
        grooveProgressBar.setMax(teacher.maxGroove);
        grooveProgressBar.setProgress(teacher.maxGroove - teacher.groove);
        jazzProgressBar.getLayoutParams().width = (int)((teacher.maxJazz + 40) * (((float) this.getResources().getDisplayMetrics()
                .densityDpi) / 160.0f));
        jazzProgressBar.requestLayout();
        jazzProgressBar.setMax(teacher.maxJazz);
        jazzProgressBar.setProgress(teacher.maxJazz - teacher.jazz);
        rockProgressBar.getLayoutParams().width = (int)((teacher.maxRock + 40) * (((float) this.getResources().getDisplayMetrics()
                .densityDpi) / 160.0f));
        rockProgressBar.requestLayout();
        rockProgressBar.setMax(teacher.maxRock);
        rockProgressBar.setProgress(teacher.maxRock - teacher.rock);
        classicalProgressBar.getLayoutParams().width = (int)((teacher.maxClassical + 40) * (((float) this.getResources()
                .getDisplayMetrics().densityDpi) / 160.0f));
        classicalProgressBar.requestLayout();
        classicalProgressBar.setMax(teacher.maxClassical);
        classicalProgressBar.setProgress(teacher.maxClassical - teacher.classical);
        TextView hornsText = findViewById(R.id.hornstext);
        hornsText.setText(String.valueOf(teacher.horns));
        TextView pianoText = findViewById(R.id.pianotext);
        pianoText.setText(String.valueOf(teacher.piano));
        TextView percussionsText = findViewById(R.id.percussionstext);
        percussionsText.setText(String.valueOf(teacher.percussions));
        TextView stringsText = findViewById(R.id.stringstext);
        stringsText.setText(String.valueOf(teacher.strings));
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            teacherSection.setBackgroundResource(R.drawable.blankwhitestatbox);
        } else{
            teacherSection.setBackgroundResource(R.drawable.smallerwhitebox);
        }
        TextView teacherName = findViewById(R.id.teachername);
        teacherName.setText(teacher.name.toUpperCase(Locale.ROOT));
        getTargetRoom(teacherArea).studentArea.setImageResource(getTargetRoom(teacherArea).student.previewStudentImage(teacher));
        highlightInstrumentAndStyle(getTargetRoom(teacherArea).student);
    }
    private void dropAction(Teacher teacher, View teacherArea){
        int sessionDuration = (int)(Math.random() * 10) + 1;
        getTargetRoom(teacherArea).teacherHealth.setVisibility(View.VISIBLE);
        getTargetRoom(teacherArea).teacherHealth.setMax(teacher.maxHealth);
        getTargetRoom(teacherArea).teacherHealth.setProgress(teacher.maxHealth - teacher.health);
        if(getTargetRoom(teacherArea).timerRunning == false){
            if(sessionDuration <= 6){
                getTargetRoom(teacherArea).SESSION_TIME_LEFT_IN_MILLIS = 30000;
                getTargetRoom(teacherArea).student.sessionLength = 30;
                getTargetRoom(teacherArea).progressBar.setProgress(30);
            } else{
                getTargetRoom(teacherArea).SESSION_TIME_LEFT_IN_MILLIS = 60000;
                getTargetRoom(teacherArea).student.sessionLength = 60;
                getTargetRoom(teacherArea).progressBar.setProgress(0);
            }
            startTimer(getTargetRoom(teacherArea));
            firstDragAction(getTargetRoom(teacherArea), teacher);
        } else{
            secondDragAction(getTargetRoom(teacherArea), teacher);
        }
    }
    private void firstDragAction(Room room, Teacher teacher){
        partyEligibleRooms.add(room);
        room.studentDespawnTimer.cancel();
        room.studentDespawning = false;
        room.space.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9FCAFF")));
        room.student.setTeacher(teacher);
        room.hasTeacher = true;
        teacher.isAssigned = true;
        room.progressBar.setVisibility(View.VISIBLE);
        room.countdown.setVisibility(View.INVISIBLE);
        room.student.setStudentSatisfaction(room.student);
        room.studentArea.setImageResource(room.student.previewStudentImage(room.student.teacher));
        room.student.setStudentImage(room.student.previewStudentImage(room.student.teacher));
        int randomHelpSpawn = (int)(Math.random() * 17) + 5;
        helpOdds(room, (randomHelpSpawn * 1000));
        if(room.student.satisfaction.equals("Unsatisfied") || (room.student.satisfaction.equals("Pissed"))){
            troubleChance(room);
        }
    }
    private void secondDragAction(Room room, Teacher teacher){
        if(room.helpRunning){
            room.helpTimer.cancel();
            room.helpRunning = false;
        }
        if(room.helpDisappearing){
            room.helpDisappear.cancel();
            room.helpDisappearing = false;
            room.HELP_DISAPPEAR_TIME = CONSTANT_HELP_DISAPPEAR_TIME;
        }
        if(room.button.getVisibility() == View.VISIBLE){
            room.button.setVisibility(View.INVISIBLE);
            if (room.teacherHealthIsRapidlyDecreasing) {
                room.rapidHealthDecrease.cancel();
                room.teacherHealthIsRapidlyDecreasing = false;
            }
        }
        if(room.student.wasServed){
            room.student.wasServed = false;
        }
        if(room.student.improvedToUnsatisfied){
            room.student.improvedToUnsatisfied = false;
        }
        if(room.student.improvedToMediocre){
            room.student.improvedToMediocre = false;
        }
        if(room.student.satisfactionDecreaseFromWrongAnswer){
            room.student.satisfactionDecreaseFromWrongAnswer = false;
        }
        room.student.teacher.isAssigned = false;
        room.student.setTeacher(teacher);
        teacher.isAssigned = true;
        room.student.setStudentSatisfaction(room.student);
        determineHelp(room);
        if(room.troubleRunning && !(room.student.satisfaction.equals("Unsatisfied")) && !(room.student.satisfaction.equals("Pissed"))){
            room.troubleTimer.cancel();
            room.troubleRunning = false;
        }
        if(room.troubleRunning == false){
            troubleChance(room);
        }
        room.studentArea.setImageResource(room.student.previewStudentImage(room.student.teacher));
        room.student.setStudentImage(room.student.previewStudentImage(room.student.teacher));
    }
    private void helpButtonAction(Room room){
        room.roomEarnings.setVisibility(View.VISIBLE);
        room.button.setVisibility(View.INVISIBLE);
        room.helpDisappear.cancel();
        room.helpDisappearing = false;
        room.HELP_DISAPPEAR_TIME = CONSTANT_HELP_DISAPPEAR_TIME;
        Intent intent = new Intent(MainActivity.this, HelpActivity.class);
        sendDataToOtherActivity(intent, room);
        startActivityForResult(intent, 1);
    }
    private void checkButtonAction(Room room){
        room.button.setVisibility(View.INVISIBLE);
        room.checkDisappear.cancel();
        room.checkDisappearing = false;
        room.CHECK_DISAPPEAR_LEFT_IN_MILLIS = CONSTANT_CHECK_DISAPPEAR_TIME;
        Intent intent = new Intent(MainActivity.this, CheckActivity.class);
        sendDataToOtherActivity(intent, room);
        startActivityForResult(intent, 2);
    }
    private void makeCheckDisappear(Room room){
        int negativeReviewChance = (int)(Math.random() * 10) + 1;
            room.checkDisappearing = true;
            room.checkDisappear = new CountDownTimer(room.CHECK_DISAPPEAR_LEFT_IN_MILLIS, 1000) {
                @Override
                public void onTick(long l) {
room.CHECK_DISAPPEAR_LEFT_IN_MILLIS = l;
                }

                @Override
                public void onFinish() {
                    room.button.setVisibility(View.INVISIBLE);
room.checkDisappearing = false;
room.CHECK_DISAPPEAR_LEFT_IN_MILLIS = CONSTANT_CHECK_DISAPPEAR_TIME;
if(room.gradient.getVisibility() == View.VISIBLE){
    room.gradient.setVisibility(View.INVISIBLE);
}
if(room.student.satisfaction.equals("Unsatisfied")){
    if(negativeReviewChance <= 5){
        if(fans <= 2){
            fans = 0;
        } else{
            fans -= 2;
        }
    }
} else if(room.student.satisfaction.equals("Pissed")){
    if(negativeReviewChance <= 7){
        if(fans <= 3){
            fans = 0;
        } else{
            fans -= 3;
        }
    }
}
                    clearRoom(room);
room.progressBar.setProgressDrawable(room.normalProgressBar.getProgressDrawable());
                }
            }.start();
    }
    private void troubleButtonAction(Room room){
        room.roomEarnings.setVisibility(View.VISIBLE);
        room.button.setVisibility(View.INVISIBLE);
        room.rapidHealthDecrease.cancel();
        room.teacherHealthIsRapidlyDecreasing = false;
        Intent intent = new Intent(MainActivity.this, TroubleActivity.class);
        sendDataToOtherActivity(intent, room);
        startActivityForResult(intent, 3);
    }
    private void partyButtonAction(Room room){
        room.button.setVisibility(View.INVISIBLE);
        room.checkDisappear.cancel();
        room.checkDisappearing = false;
        room.CHECK_DISAPPEAR_LEFT_IN_MILLIS = CONSTANT_CHECK_DISAPPEAR_TIME;
        Intent intent = new Intent(MainActivity.this, PartyActivity.class);
        sendDataToOtherActivity(intent, room);
        startActivityForResult(intent, 4);
    }
    private void sendDataToOtherActivity(Intent intent, Room room){
        goingIntoActivity = true;
        if(room1.partyActivated || room2.partyActivated || room3.partyActivated || room4.partyActivated || room5.partyActivated || room6.partyActivated){
            intent.putExtra("Music", 2);
        } else{
            intent.putExtra("Music", 1);
        }
        intent.putExtra("Room", room);
        intent.putExtra("Student", room.student);
        intent.putExtra("Teacher", room.student.teacher);
        intent.putExtra("earnings", earnings);
        intent.putExtra("fans", fans);
        intent.putExtra("partybarprogress", partyBar.getProgress());
        intent.putExtra("partybarmax", partyBar.getMax());
    }
    private void toSwapButtonAction(Room room1, Room room2){
        room1.swap.setVisibility(View.VISIBLE);
        room1.swap.bringToFront();
        room1.studentArea.setImageResource(room1.student.previewStudentImage(room2.student.teacher));
        room2.studentArea.setImageResource(room2.student.previewStudentImage(room1.student.teacher));
        room1.previewed = true;
        room2.previewed = true;
        for(Room area : rooms){
            if(room1 != area && area.swap.getVisibility() == View.VISIBLE){
                area.swap.setVisibility(View.INVISIBLE);
                area.studentArea.setImageResource(area.student.studentImage);
                area.previewed = false;
            }
        }
        room1.swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapButtonAction(room1);
            }
        });
    }
    private void swapButtonAction(Room room){
        Teacher swapTeacher = room.student.teacher;
        for(Teacher teacher : teachers){
            teacher.imageView.setEnabled(false);
        }
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            teacherSection.setBackgroundResource(R.drawable.blankpurple);
        } else{
            teacherSection.setBackgroundResource(R.drawable.purplewide);
        }
        if(room.student.satisfactionDecreaseFromWrongAnswer){
            room.student.satisfactionDecreaseFromWrongAnswer = false;
        }
        if(room.student.wasServed){
            room.student.wasServed = false;
        }
            if(room.student.improvedToUnsatisfied){
                room.student.improvedToUnsatisfied = false;
            }
            if(room.student.improvedToMediocre){
                room.student.improvedToMediocre = false;
            }
        if (room.helpRunning) {
            room.helpTimer.cancel();
            room.helpRunning = false;
        }
        if(room.helpDisappearing){
            room.helpDisappear.cancel();
            room.helpDisappearing = false;
            room.HELP_DISAPPEAR_TIME = CONSTANT_HELP_DISAPPEAR_TIME;
        }
        if(room.button.getVisibility() == View.VISIBLE){
            room.button.setVisibility(View.INVISIBLE);
            if (room.teacherHealthIsRapidlyDecreasing) {
                room.rapidHealthDecrease.cancel();
                room.teacherHealthIsRapidlyDecreasing = false;
            }
        }
        if(room.partyActivated){
            room.partyTimer.cancel();
            room.partyActivated = false;
            if(room1.partyActivated == false && room2.partyActivated == false && room3.partyActivated == false && room4.partyActivated == false
                    && room5.partyActivated == false && room6.partyActivated == false){
                SoundPlayer.player.release();
                SoundPlayer.playSong(this, R.raw.straightfuse, 0);
            }
            room.SESSION_TIME_LEFT_IN_MILLIS = room.PARTY_LEFT_IN_MILLIS;
            room.gradient.setVisibility(View.INVISIBLE);
            room.timerRunning = true;
            room.partyCancelled = true;
            partyEligibleRooms.add(room);
        }
        if(room.extensionRunning){
            room.extensionTimer.cancel();
            room.extensionRunning = false;
            room.SESSION_TIME_LEFT_IN_MILLIS = room.EXTENSION_LEFT_IN_MILLIS;
            room.EXTENSION_LEFT_IN_MILLIS = CONSTANT_EXTENSION_TIME;
            room.gradient.setVisibility(View.INVISIBLE);
            room.timerRunning = true;
            room.extensionCancelled = true;
            partyEligibleRooms.add(room);
        }
        resumeGame();
        for(int i = 0; i < returnAllOtherRooms(room).length; i++){
            if(returnAllOtherRooms(room)[i].teacherAreaClicked){
                returnAllOtherRooms(room)[i].teacherArea.setImageResource(room.student.teacher.teacherImage);
                room.teacherArea.setImageResource(returnAllOtherRooms(room)[i].student.teacher.teacherImage);
                room.student.setTeacher(returnAllOtherRooms(room)[i].student.teacher);
                room.student.setStudentImage(room.student.previewStudentImage(room.student.teacher));
                room.studentArea.setImageResource(room.student.studentImage);
                room.teacherHealth.setMax(room.student.teacher.maxHealth);
                room.teacherHealth.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
                returnAllOtherRooms(room)[i].student.setTeacher(swapTeacher);
                returnAllOtherRooms(room)[i].student.setStudentImage(returnAllOtherRooms(room)[i].student
                        .previewStudentImage(returnAllOtherRooms(room)[i].student.teacher));
                returnAllOtherRooms(room)[i].studentArea.setImageResource(returnAllOtherRooms(room)[i].student.studentImage);
                returnAllOtherRooms(room)[i].teacherHealth.setMax(returnAllOtherRooms(room)[i].student.teacher.maxHealth);
                returnAllOtherRooms(room)[i].teacherHealth.setProgress(returnAllOtherRooms(room)[i].student.teacher.maxHealth
                        - returnAllOtherRooms(room)[i].student.teacher.health);
                if(returnAllOtherRooms(room)[i].student.improvedToUnsatisfied){
                    returnAllOtherRooms(room)[i].student.improvedToUnsatisfied = false;
                }
                if(returnAllOtherRooms(room)[i].student.improvedToMediocre){
                    returnAllOtherRooms(room)[i].student.improvedToMediocre = false;
                }
                if(returnAllOtherRooms(room)[i].student.wasServed){
                    returnAllOtherRooms(room)[i].student.wasServed = false;
                }
                if(returnAllOtherRooms(room)[i].student.satisfactionDecreaseFromWrongAnswer){
                    returnAllOtherRooms(room)[i].student.satisfactionDecreaseFromWrongAnswer = false;
                }
                returnAllOtherRooms(room)[i].student.setStudentSatisfaction(returnAllOtherRooms(room)[i].student);
                room.student.setStudentSatisfaction(room.student);
                if(returnAllOtherRooms(room)[i].troubleRunning && !(returnAllOtherRooms(room)[i].student.satisfaction
                        .equals("Unsatisfied")) && !(returnAllOtherRooms(room)[i].student.satisfaction.equals("Pissed"))){
                    returnAllOtherRooms(room)[i].troubleTimer.cancel();
                    returnAllOtherRooms(room)[i].troubleRunning = false;
                }
                if(returnAllOtherRooms(room)[i].troubleRunning == false){
                    troubleChance(returnAllOtherRooms(room)[i]);
                }
                if (returnAllOtherRooms(room)[i].helpRunning) {
                    returnAllOtherRooms(room)[i].helpTimer.cancel();
                    returnAllOtherRooms(room)[i].helpRunning = false;
                }
                if(returnAllOtherRooms(room)[i].helpDisappearing){
                    returnAllOtherRooms(room)[i].helpDisappear.cancel();
                    returnAllOtherRooms(room)[i].helpDisappearing = false;
                    returnAllOtherRooms(room)[i].HELP_DISAPPEAR_TIME = CONSTANT_HELP_DISAPPEAR_TIME;
                }
                if(returnAllOtherRooms(room)[i].button.getVisibility() == View.VISIBLE){
                    returnAllOtherRooms(room)[i].button.setVisibility(View.INVISIBLE);
                    if (returnAllOtherRooms(room)[i].teacherHealthIsRapidlyDecreasing) {
                        returnAllOtherRooms(room)[i].rapidHealthDecrease.cancel();
                        returnAllOtherRooms(room)[i].teacherHealthIsRapidlyDecreasing = false;
                    }
                }
                if(returnAllOtherRooms(room)[i].partyActivated){
                    returnAllOtherRooms(room)[i].partyTimer.cancel();
                    returnAllOtherRooms(room)[i].partyActivated = false;
                    if(room1.partyActivated == false && room2.partyActivated == false && room3.partyActivated == false && room4.partyActivated == false
                            && room5.partyActivated == false && room6.partyActivated == false){
                        SoundPlayer.player.release();
                        SoundPlayer.playSong(this, R.raw.straightfuse, 0);
                    }
                    returnAllOtherRooms(room)[i].SESSION_TIME_LEFT_IN_MILLIS = returnAllOtherRooms(room)[i].PARTY_LEFT_IN_MILLIS;
                    returnAllOtherRooms(room)[i].gradient.setVisibility(View.INVISIBLE);
                    returnAllOtherRooms(room)[i].timerRunning = true;
                    startTimer(returnAllOtherRooms(room)[i]);
                    returnAllOtherRooms(room)[i].partyCancelled = true;
                    partyEligibleRooms.add(returnAllOtherRooms(room)[i]);
                }
                if(returnAllOtherRooms(room)[i].extensionRunning){
                    returnAllOtherRooms(room)[i].extensionTimer.cancel();
                    returnAllOtherRooms(room)[i].extensionRunning = false;
                    returnAllOtherRooms(room)[i].SESSION_TIME_LEFT_IN_MILLIS = returnAllOtherRooms(room)[i].EXTENSION_LEFT_IN_MILLIS;
                    returnAllOtherRooms(room)[i].EXTENSION_LEFT_IN_MILLIS = CONSTANT_EXTENSION_TIME;
                    returnAllOtherRooms(room)[i].gradient.setVisibility(View.INVISIBLE);
                    returnAllOtherRooms(room)[i].timerRunning = true;
                    startTimer(returnAllOtherRooms(room)[i]);
                    returnAllOtherRooms(room)[i].extensionCancelled = true;
                    partyEligibleRooms.add(returnAllOtherRooms(room)[i]);
                }
                determineHelp(returnAllOtherRooms(room)[i]);
            }
        }
        if(room.troubleRunning && !(room.student.satisfaction.equals("Unsatisfied")) && !(room.student.satisfaction.equals("Pissed"))){
            room.troubleTimer.cancel();
            room.troubleRunning = false;
        }
        determineHelp(room);
        if(room.troubleRunning == false){
            troubleChance(room);
        }
    }
    private void helpOdds(Room room, long HELP_MILLISECONDS){
        int eventChooser = (int)(Math.random() * 10) + 1;
        if(room.student.satisfaction.equals("Mediocre")){
            if(eventChooser <= 5 && room.extensionCancelled == false){
                room.TIME_UNTIL_HELP = HELP_MILLISECONDS;
                helpTimer(room);
            }
        } else if(room.student.satisfaction.equals("Happy")){
            if(eventChooser <= 7 && room.extensionCancelled == false){
                room.TIME_UNTIL_HELP = HELP_MILLISECONDS;
                helpTimer(room);
            }
        } else if(room.student.satisfaction.equals("Stoked")){
            if(eventChooser <= 8 && room.extensionCancelled == false){
                room.TIME_UNTIL_HELP = HELP_MILLISECONDS;
                helpTimer(room);
            }
        }
    }
    private void determineHelp(Room room){
        int timeUntilHelp = (int) (Math.random() * 7) + 5;
        if (room.student.sessionLength == 30) {
            if (room.SESSION_TIME_LEFT_IN_MILLIS >= 20000 && room.student.helpRequests == 0) {
                helpOdds(room, (timeUntilHelp * 1000));
            }
        } else {
            if (room.SESSION_TIME_LEFT_IN_MILLIS >= 50000 && room.student.helpRequests == 0) {
                helpOdds(room, (timeUntilHelp * 1000));
            } else if (room.SESSION_TIME_LEFT_IN_MILLIS >= 50000 && room.student.helpRequests == 1) {
                long helpTimer = (long) ((Math.random() * 22) * 1000) + (room.SESSION_TIME_LEFT_IN_MILLIS - 29000);
                helpOdds(room, helpTimer);
            } else if (room.SESSION_TIME_LEFT_IN_MILLIS < 50000 && room.SESSION_TIME_LEFT_IN_MILLIS >= 20000 && (room.student.helpRequests == 0 || room.student.helpRequests == 1)) {
                long helpTimer = (long) ((Math.random() * 22) * 1000) + (room.SESSION_TIME_LEFT_IN_MILLIS - 29000);
                helpOdds(room, helpTimer);
            }
        }
    }
    private void helpTimer(Room room){
        room.helpRunning = true;
        room.helpTimer = new CountDownTimer(room.TIME_UNTIL_HELP, 1000) {
            @Override
            public void onTick(long l) {
                room.TIME_UNTIL_HELP = l;
            }

            @Override
            public void onFinish() {
room.roomEarnings.setVisibility(View.INVISIBLE);
                room.button.setVisibility(View.VISIBLE);
room.button.setText("HELP");
room.button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        helpButtonAction(room);
    }
});
SoundPlayer.soundPool.play(sounds[1], 1, 1, 0, 0, 1);
room.helpRunning = false;
makeHelpDisappear(room);
room.HEALTH_DECREASE_TIME_IN_MILLIS = CONSTANT_HELP_DISAPPEAR_TIME;
makeHealthRapidlyDecrease(room);
            }
        }.start();
    }
    private void correctAnswerAction(Room room, int healthIncrease, boolean lateAnswer){
        room.student.helpRequests++;
        if(lateAnswer == false){
            room.student.increaseSatisfaction(room);
            if(room.student.satisfaction.equals("Mediocre")){
                room.student.wasServed = true;
                if(room.troubleRunning){
                    room.troubleTimer.cancel();
                    room.troubleRunning = false;
                }
            } else if(room.student.satisfaction.equals("Happy")){
                room.student.wasServed = true;
            }
        }
        room.student.teacher.increaseHealth(healthIncrease);
        room.teacherHealth.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
        returnFromHelpActivityHelpOdds(room);
    }
    private void wrongAnswerAction(Room room, boolean satisfactionDecrease){
        room.student.helpRequests++;
        if(satisfactionDecrease){
            room.student.satisfactionDecreaseFromWrongAnswer = true;
            room.student.decreaseSatisfaction(room);
            if(room.student.satisfaction.equals("Unsatisfied")){
                troubleChance(room);
            }
        }
        room.teacherHealth.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
        returnFromHelpActivityHelpOdds(room);
    }
    private void returnFromHelpActivityHelpOdds(Room room){
        if(room.SESSION_TIME_LEFT_IN_MILLIS > 30000 && room.student.sessionLength == 60 && room.student.helpRequests != 2){
            long helpTimer = (long)((Math.random() * 22) * 1000) + (room.SESSION_TIME_LEFT_IN_MILLIS - 29000);
            helpOdds(room, helpTimer);
        }
    }
    private void makeHelpDisappear(Room room){
        room.helpDisappearing = true;
        room.helpDisappear = new CountDownTimer(room.HELP_DISAPPEAR_TIME, 1000) {
            @Override
            public void onTick(long l) {
                room.HELP_DISAPPEAR_TIME = l;
            }

            @Override
            public void onFinish() {
room.roomEarnings.setVisibility(View.VISIBLE);
                room.button.setVisibility(View.INVISIBLE);
room.HELP_DISAPPEAR_TIME = CONSTANT_HELP_DISAPPEAR_TIME;
room.helpDisappearing = false;
                int satisfactionDecreaseChance = (int)(Math.random() * 10) + 1;
if(satisfactionDecreaseChance <= 3){
    room.student.satisfactionDecreaseFromWrongAnswer = true;
    room.student.decreaseSatisfaction(room);
    if(room.student.satisfaction.equals("Unsatisfied")){
        troubleChance(room);
    }
}
if(room.SESSION_TIME_LEFT_IN_MILLIS > 30000 && room.student.sessionLength == 60 && (room.student.helpRequests + 1) != 2){
    long helpTimer = (long)((Math.random() * 22) * 1000) + (room.SESSION_TIME_LEFT_IN_MILLIS - 29000);
   helpOdds(room, helpTimer);
}
room.student.helpRequests++;
            }
        }.start();
    }
    private void troubleChance(Room room){
        int troubleChance = (int)(Math.random() * 10) + 1;
        if(room.student.satisfaction.equals("Unsatisfied")){
            if(troubleChance <= 4){
                startTrouble(room);
            }
        } else if(room.student.satisfaction.equals("Pissed")){
            if(troubleChance <= 8){
                startTrouble(room);
            }
        }
    }
    private void startTrouble(Room room){
        if(room.troubleRunning == false){
            if(room.student.satisfaction.equals("Unsatisfied")){
                int troubleTimer = (int)(Math.random() * 5) + 10;
                room.TROUBLE_TIME_IN_MILLIS = (troubleTimer * 1000);
            } else{
                int troubleTimer = (int)(Math.random() * 5) + 5;
                room.TROUBLE_TIME_IN_MILLIS = (troubleTimer * 1000);
            }
        }
        room.troubleRunning = true;
        room.troubleTimer = new CountDownTimer(room.TROUBLE_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                room.TROUBLE_TIME_IN_MILLIS = l;
            }

            @Override
            public void onFinish() {
                room.roomEarnings.setVisibility(View.INVISIBLE);
                room.button.setVisibility(View.VISIBLE);
                room.button.setText("TROUBLE");
                room.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        troubleButtonAction(room);
                    }
                });
                room.troubleRunning = false;
                if(room.helpRunning){
                    room.helpTimer.cancel();
                    room.helpRunning = false;
                } else if(room.button.getVisibility() == View.VISIBLE && room.button.getText().equals("HELP")){
                    room.button.setVisibility(View.INVISIBLE);
                    room.helpDisappear.cancel();
                    room.helpDisappearing = false;
                    room.HELP_DISAPPEAR_TIME = CONSTANT_HELP_DISAPPEAR_TIME;
                }
                SoundPlayer.soundPool.play(sounds[3], 1, 1, 0, 0, 1);
                room.HEALTH_DECREASE_TIME_IN_MILLIS = room.SESSION_TIME_LEFT_IN_MILLIS;
                makeHealthRapidlyDecrease(room);
                room.teacherArea.setClickable(false);
            }
        }.start();
    }
    private void makeHealthRapidlyDecrease(Room room){
        if(room.teacherHealthIsRapidlyDecreasing == false){
            room.teacherHealthIsRapidlyDecreasing = true;
        }
room.rapidHealthDecrease = new CountDownTimer(room.HEALTH_DECREASE_TIME_IN_MILLIS, 1000) {
    @Override
    public void onTick(long l) {
        room.HEALTH_DECREASE_TIME_IN_MILLIS = l;
        room.student.teacher.decreaseHealth(1);
    }

    @Override
    public void onFinish() {
room.teacherHealthIsRapidlyDecreasing = false;
    }
}.start();
    }
    private void startTimer(Room room){
        room.sessionTimer = new CountDownTimer(room.SESSION_TIME_LEFT_IN_MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                int progress;
                if(room.student.sessionLength == 60){
                    progress = (int) ((60000 - l) / 1000);
                } else{
                    progress = (int)((30000 - l) / 1000) + 30;
                }
                room.progressBar.setProgress(progress);
                room.roomEarnings.setText("$" + String.valueOf(room.earnings));
                if(room.student.sessionLength == 60 && room.extensionCancelled == false && room.partyCancelled == false){
                    if(room.SESSION_TIME_LEFT_IN_MILLIS <= 30000 && room.SESSION_TIME_LEFT_IN_MILLIS >= 29000 && room.student.helpRequests == 0){
                        determineHelp(room);
                    }
                }
                room.earnings += room.student.pay(room.student);
                earnings += room.student.pay(room.student);
                partyBar.incrementProgressBy(room.student.pay(room.student));
                tryActivatingPartyButton();
                room.student.teacher.decreaseHealth(1);
                room.teacherHealth.incrementProgressBy(1);
                if(room.student.satisfaction.equals("Mediocre") && room.student.improvedToMediocre == false && room.student.wasServed == false){
                    if(room.student.getStudentSatisfaction(room.student.teacher, room.student).equals("Unsatisfied")){
                        int troubleChance = (int)(Math.random() * 10) + 1;
                        if(troubleChance <= 4){
                            startTrouble(room);
                        }
                    }
                }
                if(room.student.satisfaction.equals("Unsatisfied") && room.student.improvedToUnsatisfied == false){
                    if(room.student.getStudentSatisfaction(room.student.teacher, room.student).equals("Pissed") && room.troubleRunning == false){
                        int troubleChance = (int)(Math.random() * 10) + 1;
                        if(troubleChance <= 8){
                            startTrouble(room);
                        }
                    }
                }
                if(room.student.improvedToUnsatisfied == false && room.student.helpRequests != 2 && ((room.student.sessionLength == 30 && room.SESSION_TIME_LEFT_IN_MILLIS <= 26000)
                        || (room.student.sessionLength == 60 && room.SESSION_TIME_LEFT_IN_MILLIS <= 56000))){
                    if(room.student.wasServed == false && room.student.satisfactionDecreaseFromWrongAnswer == false && room.student.improvedToMediocre == false){
                        room.student.setStudentSatisfaction(room.student);
                        room.studentArea.setImageResource(room.student.previewStudentImage(room.student.teacher));
                        room.student.setStudentImage(room.student.previewStudentImage(room.student.teacher));
                    }
                    if(room.student.wasServed && room.student.satisfactionDecreaseFromWrongAnswer == false){
                        room.student.customerServedAction(room);
                    } else if(room.student.wasServed == false && room.student.satisfactionDecreaseFromWrongAnswer){
                        room.student.satisfactionDecreaseAction(room);
                    }
                    if(room.student.improvedToMediocre){
                        room.student.increaseToMediocreAction(room);
                    }
                }
                room.SESSION_TIME_LEFT_IN_MILLIS = l;
            }

            @Override
            public void onFinish() {
                room.roomEarnings.setVisibility(View.INVISIBLE);
                room.button.setVisibility(View.VISIBLE);
                room.button.setText("CHECK");
                room.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkButtonAction(room);
                    }
                });
                makeCheckDisappear(room);
                room.teacherArea.setClickable(false);
                if(room.troubleRunning){
                    room.troubleTimer.cancel();
                    room.troubleRunning = false;
                }
                if(room.button.getVisibility() == View.VISIBLE && room.button.getText().equals("TROUBLE")){
                    room.button.setVisibility(View.INVISIBLE);
                    if(room.teacherHealthIsRapidlyDecreasing){
                        room.rapidHealthDecrease.cancel();
                        room.teacherHealthIsRapidlyDecreasing = false;
                    }
                }
                SoundPlayer.soundPool.play(sounds[2], 1, 1, 0, 0, 1);
                room.timerRunning = false;
                partyEligibleRooms.remove(room);
            }
        }.start();
        room.timerRunning = true;
    }
    private void startExtensionTimer(Room room){
        room.teacherHealth.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
        room.roomEarnings.setVisibility(View.VISIBLE);
        room.extensionRunning = true;
        room.progressBar.setProgressDrawable(room.extensionTimerProgressBar.getProgressDrawable());
        room.progressBar.setProgress(room.progressBar.getProgress());
        room.extensionTimer = new CountDownTimer(room.EXTENSION_LEFT_IN_MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                int progress = (int) ((30000 - l) / 1000) + 30;
                room.progressBar.setProgress(progress);
                room.roomEarnings.setText("$" + String.valueOf(room.earnings));
                room.student.teacher.decreaseHealth(1);
                room.teacherHealth.incrementProgressBy(1);
                room.earnings += room.student.partyModePay(room.student);
                earnings += room.student.partyModePay(room.student);
                partyBar.incrementProgressBy(room.student.partyModePay(room.student));
                tryActivatingPartyButton();
                room.EXTENSION_LEFT_IN_MILLIS = l;
            }

            @Override
            public void onFinish() {
room.roomEarnings.setVisibility(View.INVISIBLE);
                room.button.setVisibility(View.VISIBLE);
room.button.setText("CHECK");
SoundPlayer.soundPool.play(sounds[2], 1, 1, 0, 0, 1);
room.button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        partyButtonAction(room);
    }
});
makeCheckDisappear(room);
room.teacherArea.setClickable(false);
room.extensionRunning = false;
room.EXTENSION_LEFT_IN_MILLIS = CONSTANT_EXTENSION_TIME;
            }
        }.start();
        room.gradient.setVisibility(View.VISIBLE);
        room.studentArea.setImageResource(R.drawable.partysatisfaction);
        room.student.setStudentImage(R.drawable.partysatisfaction);
    }
    private void tryActivatingPartyButton(){
        if(partyBar.getProgress() >= (partyBar.getMax() / 3) && partyBar.getProgress() < ((partyBar.getMax() / 3) * 2) && partyButton.isClickable() == false){
            partyButton.setBackgroundResource(R.drawable.yellowparty);
            partyButton.setClickable(true);
        } else if(partyBar.getProgress() >= (((partyBar.getMax()) / 3) * 2) && partyBar.getProgress() < (partyBar.getMax())){
            partyLevel.setText("LvL. 2");
        } else if(partyBar.getProgress() == partyBar.getMax()){
            partyLevel.setText("LvL. 3");
        }
    }
    private void pickPartyRooms(){
        if(partyBar.getProgress() >= (partyBar.getMax()/3) && partyBar.getProgress() < ((partyBar.getMax() / 3) * 2)){
            if(partyEligibleRooms.size() == 1){
                partyMode(partyEligibleRooms.get(0));
            } else if(partyEligibleRooms.size() == 2){
                for(int i = 0; i < 2; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            } else if(partyEligibleRooms.size() > 2){
                int randomRoom = (int) (Math.random() * partyEligibleRooms.size());
                partyMode(partyEligibleRooms.get(randomRoom));
                randomRoom = (int) (Math.random() * partyEligibleRooms.size());
                partyMode(partyEligibleRooms.get(randomRoom));
            }
        } else if(partyBar.getProgress() >= ((partyBar.getMax() / 3) * 2) && partyBar.getProgress() < partyBar.getMax()){
            if(partyEligibleRooms.size() == 1){
                partyMode(partyEligibleRooms.get(0));
            } else if(partyEligibleRooms.size() == 2){
                for(int i = 0; i < 2; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            } else if(partyEligibleRooms.size() == 3){
                for(int i = 0; i < 3; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            } else if(partyEligibleRooms.size() == 4){
                for(int i = 0; i < 4; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            } else if(partyEligibleRooms.size() > 4){
                int randomRoom = (int) (Math.random() * partyEligibleRooms.size());
                partyMode(partyEligibleRooms.get(randomRoom));
                randomRoom = (int) (Math.random() * partyEligibleRooms.size());
                partyMode(partyEligibleRooms.get(randomRoom));
                randomRoom = (int) (Math.random() * partyEligibleRooms.size());
                partyMode(partyEligibleRooms.get(randomRoom));
                randomRoom = (int) (Math.random() * partyEligibleRooms.size());
                partyMode(partyEligibleRooms.get(randomRoom));
            }
        } else if(partyBar.getProgress() == partyBar.getMax()){
            if(partyEligibleRooms.size() == 1){
                partyMode(partyEligibleRooms.get(0));
            } else if(partyEligibleRooms.size() == 2){
                for(int i = 0; i < 2; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            } else if(partyEligibleRooms.size() == 3){
                for(int i = 0; i < 3; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            } else if(partyEligibleRooms.size() == 4){
                for(int i = 0; i < 4; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            } else if(partyEligibleRooms.size() == 5){
                for(int i = 0; i < 5; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            } else if(partyEligibleRooms.size() == 6){
                for(int i = 0; i < 6; i++){
                    partyMode(partyEligibleRooms.get(0));
                }
            }
        }
    }
    private void partyMode(Room room){
        if(room.partyActivated == false) {
            partyEligibleRooms.remove(room);
            room.sessionTimer.cancel();
            if(room1.partyActivated == false && room2.partyActivated == false && room3.partyActivated == false && room4.partyActivated == false
                    && room5.partyActivated == false && room6.partyActivated == false){
                SoundPlayer.player.release();
                SoundPlayer.playSong(this, R.raw.onfire, 0);
            }
            if (room.teacherHealthIsRapidlyDecreasing) {
                room.rapidHealthDecrease.cancel();
                room.teacherHealthIsRapidlyDecreasing = false;
            }
            room.timerRunning = false;
            room.partyActivated = true;
            if (room.helpRunning) {
                room.helpTimer.cancel();
                room.helpRunning = false;
            }
            if (room.button.getVisibility() == View.VISIBLE) {
                room.roomEarnings.setVisibility(View.VISIBLE);
                room.button.setVisibility(View.INVISIBLE);
                if (room.button.getText().equals("HELP")) {
                    room.helpDisappear.cancel();
                    room.helpDisappearing = false;
                    room.HELP_DISAPPEAR_TIME = CONSTANT_HELP_DISAPPEAR_TIME;
                }
            }
            if (room.troubleRunning) {
                room.troubleTimer.cancel();
                room.troubleRunning = false;
            }
            if (room.SESSION_TIME_LEFT_IN_MILLIS < 10000) {
                room.PARTY_LEFT_IN_MILLIS = 10000;
                room.progressBar.setProgress(50);
            } else {
                room.PARTY_LEFT_IN_MILLIS = room.SESSION_TIME_LEFT_IN_MILLIS;
            }
            room.student.teacher.increaseHealth(20);
            room.teacherHealth.setProgress(room.student.teacher.maxHealth - room.student.teacher.health);
            room.progressBar.setProgressDrawable(room.extensionTimerProgressBar.getProgressDrawable());
            room.progressBar.setProgress(room.progressBar.getProgress());
        }
        room.partyTimer = new CountDownTimer(room.PARTY_LEFT_IN_MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                int progress;
                if(room.student.sessionLength == 60){
                    progress = (int) ((60000 - l) / 1000);
                } else{
                    progress = (int)((30000 - l) / 1000) + 30;
                }
                room.progressBar.setProgress(progress);
                room.roomEarnings.setText("$" + String.valueOf(room.earnings));
                room.student.teacher.decreaseHealth(1);
                room.teacherHealth.incrementProgressBy(1);
                room.earnings += room.student.partyModePay(room.student);
                earnings += room.student.partyModePay(room.student);
                partyBar.incrementProgressBy(room.student.partyModePay(room.student));
                tryActivatingPartyButton();
                room.PARTY_LEFT_IN_MILLIS = l;
            }

            @Override
            public void onFinish() {
room.roomEarnings.setVisibility(View.INVISIBLE);
                room.button.setVisibility(View.VISIBLE);
room.button.setText("CHECK");
SoundPlayer.soundPool.play(sounds[2], 1, 1, 0, 0, 1);
room.button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        partyButtonAction(room);
    }
});
                room.partyActivated = false;
                if(room1.partyActivated == false && room2.partyActivated == false && room3.partyActivated == false && room4.partyActivated == false
                        && room5.partyActivated == false && room6.partyActivated == false){
                    SoundPlayer.player.release();
                    SoundPlayer.playSong(MainActivity.this, R.raw.straightfuse, 0);
                }
makeCheckDisappear(room);
                room.teacherArea.setClickable(false);
            }
        }.start();
        room.gradient.setVisibility(View.VISIBLE);
        room.studentArea.setImageResource(R.drawable.partysatisfaction);
        room.student.setStudentImage(R.drawable.partysatisfaction);
    }
    private void earlyLeaveAction(Room room){
        partyEligibleRooms.remove(room);
        room.sessionTimer.cancel();
        room.timerRunning = false;
        clearRoom(room);
    }
private void clearRoom(Room room){
    if(room.hasTeacher){
room.student.teacher.isAssigned = false;
room.hasTeacher = false;
    }
    teacherReturnsToTeacherArea();
    room.studentArea.setImageResource(android.R.color.transparent);
    room.teacherArea.setImageResource(android.R.color.transparent);
    emptyRooms.add(room);
    room.student.setStudentImage(android.R.color.transparent);
    if (room.partyCancelled) {
        room.partyCancelled = false;
        room.progressBar.setProgressDrawable(room.normalProgressBar.getProgressDrawable());
    }
    if (room.extensionCancelled) {
        room.extensionCancelled = false;
        room.progressBar.setProgressDrawable(room.normalProgressBar.getProgressDrawable());
    }
    room.setEarnings(0);
    room.roomEarnings.setVisibility(View.INVISIBLE);
    room.progressBar.setVisibility(View.INVISIBLE);
    room.wealth.setVisibility(View.INVISIBLE);
    room.teacherHealth.setVisibility(View.INVISIBLE);
    room.teacherArea.setClickable(false);
    if(emptyRooms.size() == 1){
        assignStudentAtVariedRate();
    }
}
    private void teacherReturnsToTeacherArea(){
        for(Teacher teacher : teachers){
            if(teacher.isAssigned == false){
                teacher.imageView.setVisibility(View.VISIBLE);
                teacher.imageView.setImageResource(teacher.teacherImage);
            }
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if(room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false
                && room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false
                && goingIntoActivity == false){
            pauseGame();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false
                && room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false
                && goingIntoActivity){
            pauseGame();
        }
        if(goingIntoActivity == false){
            songPosition = SoundPlayer.player.getCurrentPosition();
            SoundPlayer.player.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        teachers = new Teacher[]{tracy, eddie, seth, helga, beth, olivia, sarah, rodrick};
        mainConstraintLayout = findViewById(R.id.mainConstraintLayout);
        teacherBackgroundLayout = findViewById(R.id.teacherlayout);
        rockProgressBar = findViewById(R.id.rocktext);
        grooveProgressBar = findViewById(R.id.groovetext);
        classicalProgressBar = findViewById(R.id.classicaltext);
        jazzProgressBar = findViewById(R.id.jazztext);
        healthProgressBar = findViewById(R.id.healthtext);
        partyLevel = findViewById(R.id.partylevel);
        partyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(room1.partyActivated || room2.partyActivated || room3.partyActivated || room4.partyActivated
                        || room5.partyActivated || room6.partyActivated){
                    //Sound from zapsplat.com
                    SoundPlayer.soundPool.play(sounds[5], 1, 1, 0, 0, 1);
                }
                if(partyEligibleRooms.size() >= 1){
                    pickPartyRooms();
                }
                partyButton.setBackgroundResource(R.drawable.greyparty);
                partyButton.setClickable(false);
                partyLevel.setText("LvL. 1");
                if(partyBar.getProgress() > (partyBar.getMax() / 3) && partyBar.getProgress() < (2 * (partyBar.getMax() / 3))){
                    partyBar.setProgress(partyBar.getProgress() - (partyBar.getMax() / 3));
                } else if(partyBar.getProgress() > (2 * (partyBar.getMax() / 3)) && partyBar.getProgress() < partyBar.getMax()){
                    partyBar.setProgress(partyBar.getProgress() - (2 * (partyBar.getMax() / 3)));
                } else{
                    partyBar.setProgress(0);
                }
                partyBar.setMax((partyBar.getMax() / 3) + partyBar.getMax());
            }
        });
        if(partyBar.getProgress() < (partyBar.getMax() / 3)){
            partyButton.setClickable(false);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(returningFromActivity){
            returningFromActivity = false;
        } else{
            resumeGame();
            background.start();
            background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                }
            });
            if(room1.partyActivated || room2.partyActivated || room3.partyActivated || room4.partyActivated || room5.partyActivated || room6.partyActivated){
                SoundPlayer.playSong(this, R.raw.onfire, songPosition);
            } else{
                SoundPlayer.playSong(this, R.raw.straightfuse, songPosition);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityDestroyed = true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("gametime", GAME_TIME);
        outState.putLong("quickappearancetime", QUICK_APPEARANCE_TIME);
        outState.putInt("songposition", songPosition);
        outState.putLong("timeuntilappearance", TIME_UNTIL_APPEARANCE);
        outState.putInt("earnings", earnings);
        outState.putInt("giftcosts", giftCosts);
        outState.putInt("fans", fans);
        outState.putInt("gametimeinseconds", gameTimeInSeconds);
        outState.putBoolean("studentquicklyincoming", studentQuicklyIncoming);
        outState.putBoolean("returningFromActivity", returningFromActivity);
        outState.putBoolean("goingIntoActivity", goingIntoActivity);
        outState.putParcelableArrayList("partyeligiblerooms", partyEligibleRooms);
        outState.putParcelableArrayList("emptyrooms", emptyRooms);
        outState.putParcelable("Tracy", tracy);
        outState.putParcelable("Eddie", eddie);
        outState.putParcelable("Seth", seth);
        outState.putParcelable("Beth", beth);
        outState.putParcelable("Sarah", sarah);
        outState.putParcelable("Olivia", olivia);
        outState.putParcelable("Helga", helga);
        outState.putParcelable("Rodrick", rodrick);
        for(Room room : rooms){
            outState.putParcelable("Room" + String.valueOf(room.roomNumber), room);
            if(room.studentDespawning){
                outState.putParcelable("Student" + String.valueOf(room.roomNumber), room.student);
            } else if(room.timerRunning || room.partyActivated || room.extensionRunning){
                outState.putParcelable("Student" + String.valueOf(room.roomNumber), room.student);
                outState.putParcelable("Teacher" + String.valueOf(room.roomNumber), room.student.teacher);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        GAME_TIME = savedInstanceState.getLong("gametime", 0);
        TIME_UNTIL_APPEARANCE = savedInstanceState.getLong("timeuntilappearance", 0);
        QUICK_APPEARANCE_TIME = savedInstanceState.getLong("quickappearancetime", 0);
        songPosition = savedInstanceState.getInt("songposition", 0);
        earnings = savedInstanceState.getInt("earnings", 0);
        giftCosts = savedInstanceState.getInt("giftcosts", 0);
        fans = savedInstanceState.getInt("fans", 0);
        gameTimeInSeconds = savedInstanceState.getInt("gametimeinseconds", 0);
        studentQuicklyIncoming = savedInstanceState.getBoolean("studentquicklyincoming", false);
        returningFromActivity = savedInstanceState.getBoolean("returningFromActivity", false);
        goingIntoActivity = savedInstanceState.getBoolean("goingIntoActivity", false);
        partyEligibleRooms = savedInstanceState.getParcelableArrayList("partyeligiblerooms");
        emptyRooms = savedInstanceState.getParcelableArrayList("emptyrooms");
        partyBar = findViewById(R.id.partybar);
        partyButton = findViewById(R.id.partybutton);
            if (partyBar.getProgress() >= (partyBar.getMax() / 3)) {
                partyButton.setBackgroundResource(R.drawable.yellowparty);
                partyButton.setClickable(true);
            }
            tracy = savedInstanceState.getParcelable("Tracy");
            tracy.imageView = findViewById(R.id.tracyimage);
            eddie = savedInstanceState.getParcelable("Eddie");
            eddie.imageView = findViewById(R.id.eddieimage);
            seth = savedInstanceState.getParcelable("Seth");
            seth.imageView = findViewById(R.id.sethimage);
            beth = savedInstanceState.getParcelable("Beth");
            beth.imageView = findViewById(R.id.bethimage);
            olivia = savedInstanceState.getParcelable("Olivia");
            olivia.imageView = findViewById(R.id.oliviaimage);
            rodrick = savedInstanceState.getParcelable("Rodrick");
            rodrick.imageView = findViewById(R.id.rodrickimage);
            helga = savedInstanceState.getParcelable("Helga");
            helga.imageView = findViewById(R.id.helgaimage);
            sarah = savedInstanceState.getParcelable("Sarah");
            sarah.imageView = findViewById(R.id.sarahimage);
            teachers = new Teacher[]{tracy, eddie, seth, helga, beth, olivia, sarah, rodrick};
            room1 = savedInstanceState.getParcelable("Room1");
            room2 = savedInstanceState.getParcelable("Room2");
            room3 = savedInstanceState.getParcelable("Room3");
            room4 = savedInstanceState.getParcelable("Room4");
            room5 = savedInstanceState.getParcelable("Room5");
            room6 = savedInstanceState.getParcelable("Room6");
            room1.teacherArea = findViewById(R.id.teacherarea1);
            room1.studentArea = findViewById(R.id.studentarea1);
            room1.progressBar = findViewById(R.id.progressbar1);
            room1.space = findViewById(R.id.space1);
            room1.gradient = findViewById(R.id.room1gradient);
            room1.countdown = findViewById(R.id.countdown1);
            room1.wealth = findViewById(R.id.wealth1);
            room1.roomEarnings = findViewById(R.id.room1earnings);
            room1.normalProgressBar = findViewById(R.id.normalprogressbar);
            room1.extensionTimerProgressBar = findViewById(R.id.extensionprogressbar);
            room1.teacherHealth = findViewById(R.id.room1health);
            room1.button = findViewById(R.id.button1);
            room1.toSwap = findViewById(R.id.toswap1);
            room1.swap = findViewById(R.id.swap1);
            room2.teacherArea = findViewById(R.id.teacherarea2);
            room2.studentArea = findViewById(R.id.studentarea2);
            room2.progressBar = findViewById(R.id.progressbar2);
            room2.space = findViewById(R.id.space2);
            room2.gradient = findViewById(R.id.room2gradient);
            room2.countdown = findViewById(R.id.countdown2);
            room2.wealth = findViewById(R.id.wealth2);
            room2.roomEarnings = findViewById(R.id.room2earnings);
            room2.normalProgressBar = findViewById(R.id.normalprogressbar2);
            room2.extensionTimerProgressBar = findViewById(R.id.extensionprogressbar2);
            room2.teacherHealth = findViewById(R.id.room2health);
            room2.button = findViewById(R.id.button2);
            room2.toSwap = findViewById(R.id.toswap2);
            room2.swap = findViewById(R.id.swap2);
            room3.teacherArea = findViewById(R.id.teacherarea3);
            room3.studentArea = findViewById(R.id.studentarea3);
            room3.progressBar = findViewById(R.id.progressbar3);
            room3.space = findViewById(R.id.space3);
            room3.gradient = findViewById(R.id.room3gradient);
            room3.countdown = findViewById(R.id.countdown3);
            room3.wealth = findViewById(R.id.wealth3);
            room3.roomEarnings = findViewById(R.id.room3earnings);
            room3.normalProgressBar = findViewById(R.id.normalprogressbar3);
            room3.extensionTimerProgressBar = findViewById(R.id.extensionprogressbar3);
            room3.teacherHealth = findViewById(R.id.room3health);
            room3.button = findViewById(R.id.button3);
            room3.toSwap = findViewById(R.id.toswap3);
            room3.swap = findViewById(R.id.swap3);
            room4.teacherArea = findViewById(R.id.teacherarea4);
            room4.studentArea = findViewById(R.id.studentarea4);
            room4.progressBar = findViewById(R.id.progressbar4);
            room4.space = findViewById(R.id.space4);
            room4.gradient = findViewById(R.id.room4gradient);
            room4.countdown = findViewById(R.id.countdown4);
            room4.wealth = findViewById(R.id.wealth4);
            room4.roomEarnings = findViewById(R.id.room4earnings);
            room4.normalProgressBar = findViewById(R.id.normalprogressbar4);
            room4.extensionTimerProgressBar = findViewById(R.id.extensionprogressbar4);
            room4.teacherHealth = findViewById(R.id.room4health);
            room4.button = findViewById(R.id.button4);
            room4.toSwap = findViewById(R.id.toswap4);
            room4.swap = findViewById(R.id.swap4);
            room5.teacherArea = findViewById(R.id.teacherarea5);
            room5.studentArea = findViewById(R.id.studentarea5);
            room5.progressBar = findViewById(R.id.progressbar5);
            room5.space = findViewById(R.id.space5);
            room5.gradient = findViewById(R.id.room5gradient);
            room5.countdown = findViewById(R.id.countdown5);
            room5.wealth = findViewById(R.id.wealth5);
            room5.roomEarnings = findViewById(R.id.room5earnings);
            room5.normalProgressBar = findViewById(R.id.normalprogressbar5);
            room5.extensionTimerProgressBar = findViewById(R.id.extensionprogressbar5);
            room5.teacherHealth = findViewById(R.id.room5health);
            room5.button = findViewById(R.id.button5);
            room5.toSwap = findViewById(R.id.toswap5);
            room5.swap = findViewById(R.id.swap5);
            room6.teacherArea = findViewById(R.id.teacherarea6);
            room6.studentArea = findViewById(R.id.studentarea6);
            room6.progressBar = findViewById(R.id.progressbar6);
            room6.space = findViewById(R.id.space6);
            room6.gradient = findViewById(R.id.room6gradient);
            room6.countdown = findViewById(R.id.countdown6);
            room6.wealth = findViewById(R.id.wealth6);
            room6.roomEarnings = findViewById(R.id.room6earnings);
            room6.normalProgressBar = findViewById(R.id.normalprogressbar6);
            room6.extensionTimerProgressBar = findViewById(R.id.extensionprogressbar6);
            room6.teacherHealth = findViewById(R.id.room6health);
            room6.button = findViewById(R.id.button6);
            room6.toSwap = findViewById(R.id.toswap6);
            room6.swap = findViewById(R.id.swap6);
            rooms = new Room[]{room1, room2, room3, room4, room5, room6};
        if(room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false
                && room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false){
            mainTimer();
        }
            for (Room room : rooms) {
                if (room.teacherAreaClicked) {
                    if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        teacherSection.setBackgroundResource(R.drawable.blankyellow);
                    } else {
                        teacherSection.setBackgroundResource(R.drawable.yellowwide);
                    }
                    if (room.previewed) {
                        for (int i = 0; i < returnAllOtherRooms(room).length; i++) {
                            if (returnAllOtherRooms(room)[i].previewed) {
                                room.setStudent(savedInstanceState.getParcelable("Student" + String.valueOf(room.roomNumber)));
                                room.student.setTeacher(savedInstanceState.getParcelable("Teacher" + String.valueOf(room.roomNumber)));
                                returnAllOtherRooms(room)[i].setStudent(savedInstanceState
                                        .getParcelable("Student" + String.valueOf(returnAllOtherRooms(room)[i].roomNumber)));
                                returnAllOtherRooms(room)[i].student.setTeacher(savedInstanceState
                                        .getParcelable("Teacher" + String.valueOf(returnAllOtherRooms(room)[i].roomNumber)));
                                room.studentArea.setImageResource(room.student.previewStudentImage(returnAllOtherRooms(room)[i].student.teacher));
                                returnAllOtherRooms(room)[i].studentArea.setImageResource(returnAllOtherRooms(room)[i].student
                                        .previewStudentImage(room.student.teacher));
                            }
                        }
                    }
                    room.space.setBackgroundResource(R.drawable.semitransparentyellowtable);
                    room.space.setScaleX(0.82f);
                    room.space.setScaleY(0.73f);
                    for (int i = 0; i < returnAllOtherRooms(room).length; i++) {
                        if (returnAllOtherRooms(room)[i].hasTeacher && returnAllOtherRooms(room)[i].checkDisappearing == false &&
                                returnAllOtherRooms(room)[i].previewed == false &&
                                (!(returnAllOtherRooms(room)[i].button.getVisibility() == View.VISIBLE &&
                                        returnAllOtherRooms(room)[i].button.getText().equals("TROUBLE")))) {
                            if (room.hasTeacher) {
                                returnAllOtherRooms(room)[i].toSwap.setVisibility(View.VISIBLE);
                                returnAllOtherRooms(room)[i].toSwap.bringToFront();
                                Room area = returnAllOtherRooms(room)[i];
                                area.setStudent(savedInstanceState.getParcelable("Student" + String.valueOf(returnAllOtherRooms(room)[i].roomNumber)));
                                area.student.setTeacher(savedInstanceState.getParcelable("Teacher" + String.valueOf(returnAllOtherRooms(room)[i].roomNumber)));
                                int otherRoom = i;
                                returnAllOtherRooms(area)[otherRoom].setStudent(savedInstanceState
                                        .getParcelable("Student" + String.valueOf(returnAllOtherRooms(area)[otherRoom].roomNumber)));
                                returnAllOtherRooms(area)[otherRoom].student.setTeacher(savedInstanceState
                                        .getParcelable("Teacher" + String.valueOf(returnAllOtherRooms(area)[otherRoom].roomNumber)));
                                returnAllOtherRooms(room)[i].toSwap.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        toSwapButtonAction(returnAllOtherRooms(area)[otherRoom], area);
                                    }
                                });
                            }
                        }
                    }
                } else {
                    if (room.previewed) {
                        room.swap.setVisibility(View.VISIBLE);
                        room.swap.bringToFront();
                        Room area = room;
                        area.setStudent(room.student);
                        area.student.setTeacher(room.student.teacher);
                        room.swap.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                swapButtonAction(area);
                            }
                        });
                    }
                }
                if (studentQuicklyIncoming) {
                    if (room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false
                            && room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false && activityDestroyed) {
                        nextQuicklyIncoming();
                    }
                } else if (room.studentIncoming) {
                    if (room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false
                            && room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false && activityDestroyed) {
                        randomNextStudent(room);
                    }
                } else if (room.studentAppearing) {
                    room.countdown.setVisibility(View.VISIBLE);
                    room.countdown.setText(String.valueOf(room.spawnCountdown));
                    if (room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false &&
                            room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false && activityDestroyed) {
                        studentSpawns(room);
                    }
                } else if (room.studentDespawning) {
                    room.setStudent(savedInstanceState.getParcelable("Student" + String.valueOf(room.roomNumber)));
                    room.studentArea.setImageResource(room.student.studentImage);
                    room.wealth.setVisibility(View.VISIBLE);
                    room.wealth.setText(room.student.wealth);
                    room.roomEarnings.setVisibility(View.VISIBLE);
                    room.student.displayPreferredStyle(room);
                    Room area = room;
                    room.teacherArea.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            teacherAreaAction(area);
                        }
                    });
                    room.countdown.setVisibility(View.VISIBLE);
                    room.countdown.setText(String.valueOf(room.student.leaveCountdown));
                    if (room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false &&
                            room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false && activityDestroyed) {
                        studentDespawns(room);
                    }
                } else if (room.timerRunning) {
                    room.setStudent(savedInstanceState.getParcelable("Student" + String.valueOf(room.roomNumber)));
                    room.student.setTeacher(savedInstanceState.getParcelable("Teacher" + String.valueOf(room.roomNumber)));
                    room.roomEarnings.setVisibility(View.VISIBLE);
                    room.roomEarnings.setText(String.valueOf(room.earnings));
                    room.wealth.setVisibility(View.VISIBLE);
                    room.wealth.setText(room.student.wealth);
                    room.studentArea.setImageResource(room.student.studentImage);
                    room.teacherArea.setImageResource(room.student.teacher.teacherImage);
                    room.teacherHealth.setVisibility(View.VISIBLE);
                    Room area = room;
                    area.setStudent(room.student);
                    area.student.setTeacher(room.student.teacher);
                    room.teacherArea.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            teacherAreaAction(area);
                        }
                    });
                    room.student.teacher.imageView.setImageResource(android.R.color.transparent);
                    if (room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false &&
                            room4.teacherAreaClicked == false && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false && activityDestroyed) {
                        startTimer(room);
                    }
                } else if (room.partyActivated) {
                    room.gradient.setVisibility(View.VISIBLE);
                    room.setStudent(savedInstanceState.getParcelable("Student" + String.valueOf(room.roomNumber)));
                    room.student.setTeacher(savedInstanceState.getParcelable("Teacher" + String.valueOf(room.roomNumber)));
                    room.roomEarnings.setVisibility(View.VISIBLE);
                    room.roomEarnings.setText(String.valueOf(room.earnings));
                    room.wealth.setVisibility(View.VISIBLE);
                    room.wealth.setText(room.student.wealth);
                    room.studentArea.setImageResource(room.student.studentImage);
                    room.teacherArea.setImageResource(room.student.teacher.teacherImage);
                    room.teacherHealth.setVisibility(View.VISIBLE);
                    Room area = room;
                    area.setStudent(room.student);
                    area.student.setTeacher(room.student.teacher);
                    room.teacherArea.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            teacherAreaAction(area);
                        }
                    });
                    room.student.teacher.imageView.setImageResource(android.R.color.transparent);
                    if (room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false && room4.teacherAreaClicked == false
                            && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false && activityDestroyed) {
                        partyMode(room);
                    }
                } else if (room.extensionRunning) {
                    room.gradient.setVisibility(View.VISIBLE);
                    room.setStudent(savedInstanceState.getParcelable("Student" + String.valueOf(room.roomNumber)));
                    room.student.setTeacher(savedInstanceState.getParcelable("Teacher" + String.valueOf(room.roomNumber)));
                    room.roomEarnings.setVisibility(View.VISIBLE);
                    room.roomEarnings.setText(String.valueOf(room.earnings));
                    room.wealth.setVisibility(View.VISIBLE);
                    room.wealth.setText(room.student.wealth);
                    room.studentArea.setImageResource(room.student.studentImage);
                    room.teacherArea.setImageResource(room.student.teacher.teacherImage);
                    room.teacherHealth.setVisibility(View.VISIBLE);
                    Room area = room;
                    area.setStudent(room.student);
                    area.student.setTeacher(room.student.teacher);
                    room.teacherArea.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            teacherAreaAction(area);
                        }
                    });
                    room.student.teacher.imageView.setImageResource(android.R.color.transparent);
                    if (room1.teacherAreaClicked == false && room2.teacherAreaClicked == false && room3.teacherAreaClicked == false && room4.teacherAreaClicked == false
                            && room5.teacherAreaClicked == false && room6.teacherAreaClicked == false && activityDestroyed) {
                        startExtensionTimer(room);
                    }
                }
            }
            activityDestroyed = false;
        if(room1.partyActivated || room2.partyActivated || room3.partyActivated || room4.partyActivated || room5.partyActivated || room6.partyActivated){
            SoundPlayer.playSong(this, R.raw.onfire, songPosition);
        } else{
            SoundPlayer.playSong(this, R.raw.straightfuse, songPosition);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        returningFromActivity = true;
        goingIntoActivity = false;
        background.start();
        background.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        resumeGame();
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra("room")) {
                    if (data.getIntExtra("room", 0) == 1) {
                        if (data.hasExtra("increasedtostoked")) {
                            if (data.getBooleanExtra("increasedtostoked", false)) {
                                room1.student.wasServed = true;
                            }
                        }
                        correctAnswerAction(room1, data.getIntExtra("healthincrease", 0),
                                data.getBooleanExtra("lateanswer", false));
                    } else if (data.getIntExtra("room", 0) == 2) {
                        if (data.hasExtra("increasedtostoked")) {
                            if (data.getBooleanExtra("increasedtostoked", false)) {
                                room2.student.wasServed = true;
                            }
                        }
                        correctAnswerAction(room2, data.getIntExtra("healthincrease", 0),
                                data.getBooleanExtra("lateanswer", false));
                    } else if (data.getIntExtra("room", 0) == 3) {
                        if (data.hasExtra("increasedtostoked")) {
                            if (data.getBooleanExtra("increasedtostoked", false)) {
                                room3.student.wasServed = true;
                            }
                        }
                        correctAnswerAction(room3, data.getIntExtra("healthincrease", 0),
                                data.getBooleanExtra("lateanswer", false));
                    } else if (data.getIntExtra("room", 0) == 4) {
                        if (data.hasExtra("increasedtostoked")) {
                            if (data.getBooleanExtra("increasedtostoked", false)) {
                                room4.student.wasServed = true;
                            }
                        }
                        correctAnswerAction(room4, data.getIntExtra("healthincrease", 0),
                                data.getBooleanExtra("lateanswer", false));
                    } else if (data.getIntExtra("room", 0) == 5) {
                        if (data.hasExtra("increasedtostoked")) {
                            if (data.getBooleanExtra("increasedtostoked", false)) {
                                room5.student.wasServed = true;
                            }
                        }
                        correctAnswerAction(room5, data.getIntExtra("healthincrease", 0),
                                data.getBooleanExtra("lateanswer", false));
                    } else{
                        if (data.hasExtra("increasedtostoked")) {
                            if (data.getBooleanExtra("increasedtostoked", false)) {
                                room6.student.wasServed = true;
                            }
                        }
                        correctAnswerAction(room6, data.getIntExtra("healthincrease", 0),
                                data.getBooleanExtra("lateanswer", false));
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                                                if (data.getIntExtra("room", 0) == 1) {
                                                    if (data.hasExtra("healthdecrease")) {
                                                        room1.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                    }
                                                    wrongAnswerAction(room1, data.getBooleanExtra("satisfactiondecrease", false));
                                                } else if (data.getIntExtra("room", 0) == 2) {
                                                    if (data.hasExtra("healthdecrease")) {
                                                        room2.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                    }
                                                    wrongAnswerAction(room2, data.getBooleanExtra("satisfactiondecrease", false));
                                                } else if (data.getIntExtra("room", 0) == 3) {
                                                    if (data.hasExtra("healthdecrease")) {
                                                        room3.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                    }
                                                    wrongAnswerAction(room3, data.getBooleanExtra("satisfactiondecrease", false));
                                                } else if (data.getIntExtra("room", 0) == 4) {
                                                    if (data.hasExtra("healthdecrease")) {
                                                        room4.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                    }
                                                    wrongAnswerAction(room4, data.getBooleanExtra("satisfactiondecrease", false));
                                                } else if (data.getIntExtra("room", 0) == 5) {
                                                    if (data.hasExtra("healthdecrease")) {
                                                        room5.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                    }
                                                    wrongAnswerAction(room5, data.getBooleanExtra("satisfactiondecrease", false));
                                                } else {
                                                    if (data.hasExtra("healthdecrease")) {
                                                        room6.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                    }
                                                    wrongAnswerAction(room6, data.getBooleanExtra("satisfactiondecrease", false));
                                                }
                                            }
                                        } else if (requestCode == 2) {
                                            if (resultCode == RESULT_OK) {
                                                if (data != null && data.hasExtra("room")) {
                                                    if (data.getIntExtra("room", 0) == 1) {
                                                        room1.progressBar.setProgress(30);
                                                        room1.student.teacher.increaseHealth(20);
                                                        startExtensionTimer(room1);
                                                        room1.teacherArea.setClickable(true);
                                                    } else if (data.getIntExtra("room", 0) == 2) {
                                                        room2.progressBar.setProgress(30);
                                                        room2.student.teacher.increaseHealth(20);
                                                        startExtensionTimer(room2);
                                                        room2.teacherArea.setClickable(true);
                                                    } else if (data.getIntExtra("room", 0) == 3) {
                                                        room3.progressBar.setProgress(30);
                                                        room3.student.teacher.increaseHealth(20);
                                                        startExtensionTimer(room3);
                                                        room3.teacherArea.setClickable(true);
                                                    } else if (data.getIntExtra("room", 0) == 4) {
                                                        room4.progressBar.setProgress(30);
                                                        room4.student.teacher.increaseHealth(20);
                                                        startExtensionTimer(room4);
                                                        room4.teacherArea.setClickable(true);
                                                    } else if (data.getIntExtra("room", 0) == 5) {
                                                        room5.progressBar.setProgress(30);
                                                        room5.student.teacher.increaseHealth(20);
                                                        startExtensionTimer(room5);
                                                        room5.teacherArea.setClickable(true);
                                                    } else {
                                                        room6.progressBar.setProgress(30);
                                                        room6.student.teacher.increaseHealth(20);
                                                        startExtensionTimer(room6);
                                                        room6.teacherArea.setClickable(true);
                                                    }
                                                }
                                            } else if (resultCode == RESULT_CANCELED) {
                                                if (data != null && data.hasExtra("room")) {
                                                    if (data.hasExtra("giftcosts")) {
                                                        giftCosts += data.getIntExtra("giftcosts", 0);
                                                    }
                                                    if (data.hasExtra("fans")) {
                                                        fans = data.getIntExtra("fans", 0);
                                                    }
                                                    if (data.getIntExtra("room", 0) == 1) {
                                                        clearRoom(room1);
                                                    } else if (data.getIntExtra("room", 0) == 2) {
                                                        clearRoom(room2);
                                                    } else if (data.getIntExtra("room", 0) == 3) {
                                                        clearRoom(room3);
                                                    } else if (data.getIntExtra("room", 0) == 4) {
                                                        clearRoom(room4);
                                                    } else if (data.getIntExtra("room", 0) == 5) {
                                                        clearRoom(room5);
                                                    } else {
                                                        clearRoom(room6);
                                                    }
                                                }
                                            }
                                        } else if (requestCode == 3) {
                                            if (resultCode == RESULT_OK) {
                                                if (data != null && data.hasExtra("room")) {
                                                    if(data.hasExtra("giftcosts")){
                                                        giftCosts += data.getIntExtra("giftcosts", 0);
                                                    }
                                                    if (data.getIntExtra("room", 0) == 1) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room1.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                            room1.teacherHealth.setProgress(room1.student.teacher.maxHealth - room1.student.teacher.health);
                                                        } else if(data.hasExtra("healthdecrease")){
                                                            room1.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            room1.teacherHealth.setProgress(room1.student.teacher.maxHealth - room1.student.teacher.health);
                                                        }
                                                        room1.student.increaseSatisfaction(room1);
                                                        if (room1.student.satisfaction.equals("Unsatisfied")) {
                                                            room1.student.improvedToUnsatisfied = true;
                                                            troubleChance(room1);
                                                        } else if (room1.student.satisfaction.equals("Mediocre")) {
                                                            room1.student.improvedToMediocre = true;
                                                            determineHelp(room1);
                                                        }
                                                        room1.teacherArea.setClickable(true);
                                                    } else if (data.getIntExtra("room", 0) == 2) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room2.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                            room2.teacherHealth.setProgress(room2.student.teacher.maxHealth - room2.student.teacher.health);
                                                        } else if(data.hasExtra("healthdecrease")){
                                                            room2.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            room2.teacherHealth.setProgress(room2.student.teacher.maxHealth - room2.student.teacher.health);
                                                        }
                                                        room2.student.increaseSatisfaction(room2);
                                                        if (room2.student.satisfaction.equals("Unsatisfied")) {
                                                            room2.student.improvedToUnsatisfied = true;
                                                            troubleChance(room2);
                                                        } else if (room2.student.satisfaction.equals("Mediocre")) {
                                                            room2.student.improvedToMediocre = true;
                                                            determineHelp(room2);
                                                        }
                                                        room2.teacherArea.setClickable(true);
                                                    } else if (data.getIntExtra("room", 0) == 3) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room3.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                            room3.teacherHealth.setProgress(room3.student.teacher.maxHealth - room3.student.teacher.health);
                                                        } else if(data.hasExtra("healthdecrease")){
                                                            room3.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            room3.teacherHealth.setProgress(room3.student.teacher.maxHealth - room3.student.teacher.health);
                                                        }
                                                        room3.student.increaseSatisfaction(room3);
                                                        if (room3.student.satisfaction.equals("Unsatisfied")) {
                                                            room3.student.improvedToUnsatisfied = true;
                                                            troubleChance(room3);
                                                        } else if(room3.student.satisfaction.equals("Mediocre")){
                                                            room3.student.improvedToMediocre = true;
                                                            determineHelp(room3);
                                                        }
                                                        room3.teacherArea.setClickable(true);
                                                    } else if (data.getIntExtra("room", 0) == 4) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room4.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                            room4.teacherHealth.setProgress(room4.student.teacher.maxHealth - room4.student.teacher.health);
                                                        } else if(data.hasExtra("healthdecrease")){
                                                            room4.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            room4.teacherHealth.setProgress(room4.student.teacher.maxHealth - room4.student.teacher.health);
                                                        }
                                                        room4.student.increaseSatisfaction(room4);
                                                        if (room4.student.satisfaction.equals("Unsatisfied")) {
                                                            room4.student.improvedToUnsatisfied = true;
                                                            troubleChance(room4);
                                                        } else if(room4.student.satisfaction.equals("Mediocre")){
                                                            room4.student.improvedToMediocre = true;
                                                            determineHelp(room4);
                                                        }
                                                        room4.teacherArea.setClickable(true);
                                                    } else if (data.getIntExtra("room", 0) == 5) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room5.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                            room5.teacherHealth.setProgress(room5.student.teacher.maxHealth - room5.student.teacher.health);
                                                        } else if(data.hasExtra("healthdecrease")){
                                                            room5.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            room5.teacherHealth.setProgress(room5.student.teacher.maxHealth - room5.student.teacher.health);
                                                        }
                                                        room5.student.increaseSatisfaction(room5);
                                                        if (room5.student.satisfaction.equals("Unsatisfied")) {
                                                            room5.student.improvedToUnsatisfied = true;
                                                            troubleChance(room5);
                                                        } else if(room5.student.satisfaction.equals("Mediocre")){
                                                            room5.student.improvedToMediocre = true;
                                                            determineHelp(room5);
                                                        }
                                                        room5.teacherArea.setClickable(true);
                                                    } else {
                                                        if(data.hasExtra("healthincrease")){
                                                            room6.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                            room6.teacherHealth.setProgress(room6.student.teacher.maxHealth - room6.student.teacher.health);
                                                        } else if(data.hasExtra("healthdecrease")){
                                                            room6.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            room6.teacherHealth.setProgress(room6.student.teacher.maxHealth - room6.student.teacher.health);
                                                        }
                                                        room6.student.increaseSatisfaction(room6);
                                                        if (room6.student.satisfaction.equals("Unsatisfied")) {
                                                            room6.student.improvedToUnsatisfied = true;
                                                            troubleChance(room6);
                                                        } else if(room6.student.satisfaction.equals("Mediocre")){
                                                            room6.student.improvedToMediocre = true;
                                                            determineHelp(room6);
                                                        }
                                                        room6.teacherArea.setClickable(true);
                                                    }
                                                }
                                            } else if (resultCode == RESULT_CANCELED) {
                                                if (data != null && data.hasExtra("room")) {
                                                    if (data.hasExtra("fans")) {
                                                        fans = data.getIntExtra("fans", 0);
                                                    }
                                                    if(data.hasExtra("giftcosts")){
                                                        giftCosts += data.getIntExtra("giftcosts", 0);
                                                    }
                                                    if (data.getIntExtra("room", 0) == 1) {
                                                             if(data.hasExtra("healthdecrease")){
                                                                room1.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            } else if (data.hasExtra("userDidntAnswer")) {
                                                                    room1.student.teacher.decreaseHealth(20);
                                                                }
                                                                earlyLeaveAction(room1);
                                                    } else if (data.getIntExtra("room", 0) == 2) {
                                                        if(data.hasExtra("healthdecrease")){
                                                                room2.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            } else if (data.hasExtra("userDidntAnswer")) {
                                                                    room2.student.teacher.decreaseHealth(20);
                                                                }
                                                                earlyLeaveAction(room2);
                                                    } else if (data.getIntExtra("room", 0) == 3) {
                                                            if(data.hasExtra("healthdecrease")){
                                                                room3.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            } else if (data.hasExtra("userDidntAnswer")) {
                                                                    room3.student.teacher.decreaseHealth(20);
                                                                }
                                                                earlyLeaveAction(room3);
                                                    } else if (data.getIntExtra("room", 0) == 4) {
                                                        if(data.hasExtra("healthdecrease")){
                                                                room4.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            } else if (data.hasExtra("userDidntAnswer")) {
                                                                    room4.student.teacher.decreaseHealth(20);
                                                                }
                                                                earlyLeaveAction(room4);
                                                    } else if (data.getIntExtra("room", 0) == 5) {
                                                            if(data.hasExtra("healthdecrease")){
                                                                room5.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            } else if (data.hasExtra("userDidntAnswer")) {
                                                                    room5.student.teacher.decreaseHealth(20);
                                                                }
                                                                earlyLeaveAction(room5);
                                                    } else {
                                                            if(data.hasExtra("healthdecrease")){
                                                                room6.student.teacher.decreaseHealth(data.getIntExtra("healthdecrease", 0));
                                                            } else if (data.hasExtra("userDidntAnswer")) {
                                                                    room6.student.teacher.decreaseHealth(20);
                                                                }
                                                                earlyLeaveAction(room6);
                                                    }
                                                }
                                            }
                                        } else if (requestCode == 4) {
                                            if (resultCode == RESULT_OK) {
                                                if (data != null && data.hasExtra("room")) {
                                                    if (data.hasExtra("fans")) {
                                                        fans = data.getIntExtra("fans", 0);
                                                    }
                                                    if(data.hasExtra("giftcosts")){
                                                        giftCosts += data.getIntExtra("giftcosts", 0);
                                                    }
                                                    if (data.getIntExtra("room", 0) == 1) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room1.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                        }
                                                        room1.gradient.setVisibility(View.INVISIBLE);
                                                        room1.progressBar.setProgressDrawable(room1.normalProgressBar.getProgressDrawable());
                                                        clearRoom(room1);
                                                    } else if (data.getIntExtra("room", 0) == 2) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room2.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                        }
                                                        room2.gradient.setVisibility(View.INVISIBLE);
                                                        room2.progressBar.setProgressDrawable(room2.normalProgressBar.getProgressDrawable());
                                                        clearRoom(room2);
                                                    } else if (data.getIntExtra("room", 0) == 3) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room3.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                        }
                                                        room3.gradient.setVisibility(View.INVISIBLE);
                                                        room3.progressBar.setProgressDrawable(room3.normalProgressBar.getProgressDrawable());
                                                        clearRoom(room3);
                                                    } else if (data.getIntExtra("room", 0) == 4) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room4.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                        }
                                                        room4.gradient.setVisibility(View.INVISIBLE);
                                                        room4.progressBar.setProgressDrawable(room4.normalProgressBar.getProgressDrawable());
                                                        clearRoom(room4);
                                                    } else if (data.getIntExtra("room", 0) == 5) {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room5.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                        }
                                                        room5.gradient.setVisibility(View.INVISIBLE);
                                                        room5.progressBar.setProgressDrawable(room5.normalProgressBar.getProgressDrawable());
                                                        clearRoom(room5);
                                                    } else {
                                                        if (data.hasExtra("healthincrease")) {
                                                            room6.student.teacher.increaseHealth(data.getIntExtra("healthincrease", 0));
                                                        }
                                                        room6.gradient.setVisibility(View.INVISIBLE);
                                                        room6.progressBar.setProgressDrawable(room6.normalProgressBar.getProgressDrawable());
                                                        clearRoom(room6);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }



