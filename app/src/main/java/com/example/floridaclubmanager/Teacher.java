package com.example.floridaclubmanager;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.Toast;

public class Teacher implements Parcelable {
    char horns, percussions, strings, piano;
    int groove, rock, classical, jazz, teacherImage, salary, health;
    final int maxHealth, maxRock, maxJazz, maxClassical, maxGroove;
    boolean isAssigned;
    String name;
    ImageView imageView;
    public Teacher(String name, char horns, char strings, char piano, char percussions, int rock, int groove, int jazz, int classical, int health, int teacherImage, ImageView imageView){
        this.name = name;
        this.horns = horns;
        this.strings = strings;
        this.piano = piano;
        this.percussions = percussions;
        this.rock = rock;
        maxRock = rock;
        this.groove = groove;
        maxGroove = groove;
        this.jazz = jazz;
        maxJazz = jazz;
        this.classical = classical;
        maxClassical = classical;
        this.health = health;
        maxHealth = health;
        this.teacherImage = teacherImage;
        this.imageView = imageView;
    }

    protected Teacher(Parcel in) {
        horns = (char) in.readInt();
        percussions = (char) in.readInt();
        strings = (char) in.readInt();
        piano = (char) in.readInt();
        groove = in.readInt();
        rock = in.readInt();
        classical = in.readInt();
        jazz = in.readInt();
        teacherImage = in.readInt();
        salary = in.readInt();
        health = in.readInt();
        maxHealth = in.readInt();
        maxRock = in.readInt();
        maxJazz = in.readInt();
        maxClassical = in.readInt();
        maxGroove = in.readInt();
        isAssigned = in.readByte() != 0;
        name = in.readString();
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    public void setHealth(int health){
        this.health = health;
    }
    public void decreaseHealth(int healthdecrease){
            if (this.health <= healthdecrease) {
                this.health = 0;
            } else {
                this.health -= healthdecrease;
            }
            this.DecreaseStats();
    }
    public void increaseHealth(int healthincrease){
       int lowhealthincrement;
        if((maxHealth - health) <= healthincrease){
            lowhealthincrement = (maxHealth - health);
            this.increaseStats(lowhealthincrement);
            health = maxHealth;
        } else{
            this.increaseStats(healthincrease);
            health += healthincrease;
        }
    }
    public void DecreaseStats(){
        if(health <= (maxHealth * 0.6) && health > (maxHealth * 0.5)){
            rock = maxRock;
            rock -= (maxRock * 0.05);
            jazz = maxJazz;
            jazz -= (maxRock * 0.05);
            groove = maxGroove;
            groove -= (maxGroove * 0.05);
            classical = maxClassical;
            classical -= (maxClassical * 0.05);
        } else if(health <= (maxHealth * 0.5) && health > (maxHealth * 0.4)){
            rock = maxRock;
            jazz = maxJazz;
            groove = maxGroove;
            classical = maxClassical;
            for(int i = 0; i < 2; i++){
                rock -= (maxRock * 0.05);
                jazz -= (maxJazz * 0.05);
                groove -= (maxGroove * 0.05);
                classical -= (maxClassical * 0.05);
            }
        } else if(health <= (maxHealth * 0.4) && health > (maxHealth * 0.3)){
            rock = maxRock;
            jazz = maxJazz;
            groove = maxGroove;
            classical = maxClassical;
            for(int i = 0; i < 3; i++){
                rock -= (maxRock * 0.05);
                jazz -= (maxJazz * 0.05);
                groove -= (maxGroove * 0.05);
                classical -= (maxClassical * 0.05);
            }
        } else if(health <= (maxHealth * 0.3) && health > (maxHealth * 0.2)){
            rock = maxRock;
            jazz = maxJazz;
            groove = maxGroove;
            classical = maxClassical;
            for(int i = 0; i < 4; i++){
                rock -= (maxRock * 0.05);
                jazz -= (maxJazz * 0.05);
                groove -= (maxGroove * 0.05);
                classical -= (maxClassical * 0.05);
            }
        } else if(health <= (maxHealth * 0.2)){
            rock = maxRock;
            jazz = maxJazz;
            groove = maxGroove;
            classical = maxClassical;
            for(int i = 0; i < 5; i++){
                rock -= (maxRock * 0.05);
                jazz -= (maxJazz * 0.05);
                groove -= (maxGroove * 0.05);
                classical -= (maxClassical * 0.05);
            }
        }
    }
    public void increaseStats(int increase){
        if((health + increase) > (maxHealth * 0.2) && (health + increase) < (maxHealth * 0.3)){
            rock = maxRock;
            jazz = maxJazz;
            classical = maxClassical;
            groove = maxGroove;
            for(int i = 0; i < 4; i++){
                rock -= (maxRock * 0.05);
                jazz -= (maxJazz * 0.05);
                groove -= (maxGroove * 0.05);
                classical -= (maxClassical * 0.05);
            }
        } else if((health + increase) > (maxHealth * 0.3) && (health + increase) < (maxHealth * 0.4)){
            rock = maxRock;
            jazz = maxJazz;
            classical = maxClassical;
            groove = maxGroove;
            for(int i = 0; i < 3; i++){
                rock -= (maxRock * 0.05);
                jazz -= (maxJazz * 0.05);
                groove -= (maxGroove * 0.05);
                classical -= (maxClassical * 0.05);
            }
        } else if((health + increase) > (maxHealth * 0.4) && (health + increase) < (maxHealth * 0.5)){
            rock = maxRock;
            jazz = maxJazz;
            classical = maxClassical;
            groove = maxGroove;
            for(int i = 0; i < 2; i++){
                rock -= (maxRock * 0.05);
                jazz -= (maxJazz * 0.05);
                groove -= (maxGroove * 0.05);
                classical -= (maxClassical * 0.05);
            }
        } else if((health + increase) > (maxHealth * 0.5) && (health + increase) < (maxHealth * 0.6)){
            rock = maxRock;
            jazz = maxJazz;
            classical = maxClassical;
            groove = maxGroove;
            rock -= (maxRock * 0.05);
            jazz -= (maxJazz * 0.05);
            groove -= (maxGroove * 0.05);
            classical -= (maxClassical * 0.05);
        } else if((health + increase) > (maxHealth * 0.6)){
            rock = maxRock;
            jazz = maxJazz;
            classical = maxClassical;
            groove = maxGroove;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt((int) horns);
        parcel.writeInt((int) percussions);
        parcel.writeInt((int) strings);
        parcel.writeInt((int) piano);
        parcel.writeInt(groove);
        parcel.writeInt(rock);
        parcel.writeInt(classical);
        parcel.writeInt(jazz);
        parcel.writeInt(teacherImage);
        parcel.writeInt(salary);
        parcel.writeInt(health);
        parcel.writeInt(maxHealth);
        parcel.writeInt(maxRock);
        parcel.writeInt(maxJazz);
        parcel.writeInt(maxClassical);
        parcel.writeInt(maxGroove);
        parcel.writeByte((byte) (isAssigned ? 1 : 0));
        parcel.writeString(name);
    }
}
