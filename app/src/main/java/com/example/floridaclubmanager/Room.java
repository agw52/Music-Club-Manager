package com.example.floridaclubmanager;

import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
public class Room implements Parcelable {
   Student student;
    int earnings, spawnCountdown, roomNumber;
    ProgressBar progressBar, teacherHealth, extensionTimerProgressBar, normalProgressBar;
    TextView roomEarnings, wealth, countdown;
    View space;
    ImageView teacherArea, studentArea, gradient;
    long SESSION_TIME_LEFT_IN_MILLIS, TIME_UNTIL_HELP, HELP_DISAPPEAR_TIME, TROUBLE_TIME_IN_MILLIS, PARTY_LEFT_IN_MILLIS, CHECK_DISAPPEAR_LEFT_IN_MILLIS, HEALTH_DECREASE_TIME_IN_MILLIS, SPAWN_TIME_LEFT_IN_MILLIS, DESPAWN_TIME_LEFT_IN_MILLIS;
    long EXTENSION_LEFT_IN_MILLIS = 30000;
    boolean teacherAreaClicked, timerRunning, hasTeacher, troubleRunning, helpRunning, helpDisappearing, partyActivated, extensionRunning, partyCancelled, extensionCancelled, checkDisappearing, studentDespawning, studentAppearing, studentIncoming, teacherHealthIsRapidlyDecreasing, previewed;
    CountDownTimer helpTimer, helpDisappear, checkDisappear, troubleTimer, sessionTimer, partyTimer, extensionTimer, rapidHealthDecrease, studentDespawnTimer, studentAppearTimer, incomingStudentTimer;
    Button button, toSwap, swap;
    public Room(int roomNumber, ImageView teacherArea, ImageView studentArea, View space, TextView wealth, TextView roomEarnings, ImageView gradient, TextView countdown, ProgressBar progressBar, ProgressBar teacherHealth, ProgressBar extensionTimerProgressBar, ProgressBar normalProgressBar, Button button, Button toSwap, Button swap){
        this.roomNumber = roomNumber;
        this.teacherArea = teacherArea;
        this.studentArea = studentArea;
        this.gradient = gradient;
        this.countdown = countdown;
        this.space = space;
        this.wealth = wealth;
        this.roomEarnings = roomEarnings;
        this.progressBar = progressBar;
        this.extensionTimerProgressBar = extensionTimerProgressBar;
        this.normalProgressBar = normalProgressBar;
        this.teacherHealth = teacherHealth;
        this.button = button;
        this.toSwap = toSwap;
        this.swap = swap;
    }
    protected Room(Parcel in) {
        earnings = in.readInt();
        spawnCountdown = in.readInt();
        roomNumber = in.readInt();
        SESSION_TIME_LEFT_IN_MILLIS = in.readLong();
        TIME_UNTIL_HELP = in.readLong();
        HELP_DISAPPEAR_TIME = in.readLong();
        TROUBLE_TIME_IN_MILLIS = in.readLong();
        PARTY_LEFT_IN_MILLIS = in.readLong();
        CHECK_DISAPPEAR_LEFT_IN_MILLIS = in.readLong();
        HEALTH_DECREASE_TIME_IN_MILLIS = in.readLong();
        SPAWN_TIME_LEFT_IN_MILLIS = in.readLong();
        DESPAWN_TIME_LEFT_IN_MILLIS = in.readLong();
        EXTENSION_LEFT_IN_MILLIS = in.readLong();
        timerRunning = in.readByte() != 0;
        hasTeacher = in.readByte() != 0;
        troubleRunning = in.readByte() != 0;
        helpRunning = in.readByte() != 0;
        helpDisappearing = in.readByte() != 0;
        partyActivated = in.readByte() != 0;
        extensionRunning = in.readByte() != 0;
        partyCancelled = in.readByte() != 0;
        extensionCancelled = in.readByte() != 0;
        checkDisappearing = in.readByte() != 0;
        studentDespawning = in.readByte() != 0;
        studentAppearing = in.readByte() != 0;
        studentIncoming = in.readByte() != 0;
        teacherHealthIsRapidlyDecreasing = in.readByte() != 0;
        previewed = in.readByte() != 0;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public void setEarnings(int earnings){
        this.earnings = earnings;
    }
    public void setStudent(Student student){

        this.student = student;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(earnings);
        parcel.writeInt(spawnCountdown);
        parcel.writeInt(roomNumber);
        parcel.writeLong(SESSION_TIME_LEFT_IN_MILLIS);
        parcel.writeLong(TIME_UNTIL_HELP);
        parcel.writeLong(HELP_DISAPPEAR_TIME);
        parcel.writeLong(TROUBLE_TIME_IN_MILLIS);
        parcel.writeLong(PARTY_LEFT_IN_MILLIS);
        parcel.writeLong(CHECK_DISAPPEAR_LEFT_IN_MILLIS);
        parcel.writeLong(HEALTH_DECREASE_TIME_IN_MILLIS);
        parcel.writeLong(SPAWN_TIME_LEFT_IN_MILLIS);
        parcel.writeLong(DESPAWN_TIME_LEFT_IN_MILLIS);
        parcel.writeLong(EXTENSION_LEFT_IN_MILLIS);
        parcel.writeByte((byte) (timerRunning ? 1 : 0));
        parcel.writeByte((byte) (hasTeacher ? 1 : 0));
        parcel.writeByte((byte) (troubleRunning ? 1 : 0));
        parcel.writeByte((byte) (helpRunning ? 1 : 0));
        parcel.writeByte((byte) (helpDisappearing ? 1 : 0));
        parcel.writeByte((byte) (partyActivated ? 1 : 0));
        parcel.writeByte((byte) (extensionRunning ? 1 : 0));
        parcel.writeByte((byte) (partyCancelled ? 1 : 0));
        parcel.writeByte((byte) (extensionCancelled ? 1 : 0));
        parcel.writeByte((byte) (checkDisappearing ? 1 : 0));
        parcel.writeByte((byte) (studentDespawning ? 1 : 0));
        parcel.writeByte((byte) (studentAppearing ? 1 : 0));
        parcel.writeByte((byte) (studentIncoming ? 1 : 0));
        parcel.writeByte((byte) (teacherHealthIsRapidlyDecreasing ? 1 : 0));
        parcel.writeByte((byte) (previewed ? 1 : 0));
    }
}
