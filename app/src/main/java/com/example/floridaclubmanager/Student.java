package com.example.floridaclubmanager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import pl.droidsonroids.gif.GifImageView;

public class Student implements Parcelable {
    int studentImage, leaveCountdown, sessionLength, helpRequests;
    String instrumentOfChoice, preferredMusicStyle, satisfaction, wealth;
    Teacher teacher;
    boolean improvedToUnsatisfied, improvedToMediocre, wasServed, satisfactionDecreaseFromWrongAnswer;

    protected Student(Parcel in) {
        studentImage = in.readInt();
        leaveCountdown = in.readInt();
        sessionLength = in.readInt();
        helpRequests = in.readInt();
        instrumentOfChoice = in.readString();
        preferredMusicStyle = in.readString();
        satisfaction = in.readString();
        wealth = in.readString();
        improvedToUnsatisfied = in.readByte() != 0;
        improvedToMediocre = in.readByte() != 0;
        wasServed = in.readByte() != 0;
        satisfactionDecreaseFromWrongAnswer = in.readByte() != 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public void setSatisfaction(String string){
        this.satisfaction = string;
    }
    public int previewStudentImage(Teacher teacher){
        if(this.getStudentSatisfaction(teacher, this).equals("Stoked")){
            return R.drawable.stokedface;
        } else if(this.getStudentSatisfaction(teacher, this).equals("Happy")){
            return R.drawable.happyface;
        } else if(this.getStudentSatisfaction(teacher, this).equals("Mediocre")){
            return R.drawable.straightface;
        } else if(this.getStudentSatisfaction(teacher, this).equals("Unsatisfied")){
            return R.drawable.unhappyface;
        } else{
            return R.drawable.pissedface;
        }
    }
    public void setStudentImage(int studentimage){
        this.studentImage = studentimage;
    }
    public void setTeacher(Teacher teacher){

        this.teacher = teacher;
    }
    public Student(){

    }
    public void increaseSatisfaction(Room room){
        if(this.satisfaction.equals("Pissed")){
            this.setSatisfaction("Unsatisfied");
            this.setStudentImage(R.drawable.unhappyface);
            room.studentArea.setImageResource(R.drawable.unhappyface);
        } else if(this.satisfaction.equals("Unsatisfied")){
            this.setSatisfaction("Mediocre");
            this.setStudentImage(R.drawable.straightface);
            room.studentArea.setImageResource(R.drawable.straightface);
        } else if(this.satisfaction.equals("Mediocre")){
            this.setSatisfaction("Happy");
            this.setStudentImage(R.drawable.happyface);
            room.studentArea.setImageResource(R.drawable.happyface);
        } else if(this.satisfaction.equals("Happy")){
            this.setSatisfaction("Stoked");
            this.setStudentImage(R.drawable.stokedface);
            room.studentArea.setImageResource(R.drawable.stokedface);
        }
    }
    public void decreaseSatisfaction(Room room){
        if(this.satisfaction.equals("Stoked")){
            this.setSatisfaction("Happy");
            this.setStudentImage(R.drawable.happyface);
            room.studentArea.setImageResource(R.drawable.happyface);
        } else if(this.satisfaction.equals("Happy")){
            this.setSatisfaction("Mediocre");
            this.setStudentImage(R.drawable.straightface);
            room.studentArea.setImageResource(R.drawable.straightface);
        } else if(this.satisfaction.equals("Mediocre")){
            this.setSatisfaction("Unsatisfied");
            this.setStudentImage(R.drawable.unhappyface);
            room.studentArea.setImageResource(R.drawable.unhappyface);
        }
    }
    public int pay(Student student){
        if(student instanceof PoorStudent){
            if(student.satisfaction.equals("Pissed")){
                return 1;
            } else if(student.satisfaction.equals("Unsatisfied")){
                return 35;
            } else if(student.satisfaction.equals("Mediocre")){
                return 46;
            } else if(student.satisfaction.equals("Happy")){
                return 58;
            } else{
               return 87;
            }
        } else if(student instanceof AverageStudent){
            if(student.satisfaction.equals("Pissed")){
                return 1;
            } else if(student.satisfaction.equals("Unsatisfied")){
                return 140;
            } else if(student.satisfaction.equals("Mediocre")){
                return 185;
            } else if(student.satisfaction.equals("Happy")){
                return 232;
            } else{
                return 348;
            }
        } else{
            if(student.satisfaction.equals("Pissed")){
                return 1;
            } else if(student.satisfaction.equals("Unsatisfied")){
                return 244;
            } else if(student.satisfaction.equals("Mediocre")){
                return 325;
            } else if(student.satisfaction.equals("Happy")){
                return 407;
            } else{
                return 610;
            }
        }
    }
    public int partyModePay(Student student){
            if(student instanceof PoorStudent){
                return 174;
            } else if(student instanceof AverageStudent){
                return 696;
            } else{
                return 1220;
            }
    }
    public void displayPreferredStyle(Room room){
        room.roomEarnings.setText(room.student.preferredMusicStyle);
        if(room.student.preferredMusicStyle.equals("Rock")){
            room.space.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#03DAC5")));
        } else if(room.student.preferredMusicStyle.equals("Jazz")){
            room.space.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#336633")));
        } else if(room.student.preferredMusicStyle.equals("Classical")){
            room.space.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6F660C")));
        } else{
            room.space.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#BB86FC")));
        }
    }
    public String getStudentSatisfaction(Teacher teacher, Student student){
        if(student instanceof PoorStudent){
           return ((PoorStudent) student).getPoorStudentSatisfaction(teacher);
        } else if(student instanceof AverageStudent){
            return ((AverageStudent) student).getAverageStudentSatisfaction(teacher);
        } else{
            return ((WealthyStudent) student).getWealthyStudentSatisfaction(teacher);
        }
    }
    public void setStudentSatisfaction(Student student){
        if(student instanceof PoorStudent){
            ((PoorStudent) student).setPoorStudentSatisfaction();
        } else if(student instanceof AverageStudent){
            ((AverageStudent) student).setAverageStudentSatisfaction();
        } else{
            ((WealthyStudent) student).setWealthyStudentSatisfaction();
        }
    }
    public void customerServedAction(Room froom){
        if(this.satisfaction.equals("Stoked")){
            if(this.getStudentSatisfaction(this.teacher, this).equals("Mediocre")){
                this.decreaseSatisfaction(froom);
            }
        } else if(this.satisfaction.equals("Happy")){
            if(this.getStudentSatisfaction(this.teacher, this).equals("Unsatisfied")){
                this.decreaseSatisfaction(froom);
            }
        } else{
            if(this.getStudentSatisfaction(this.teacher, this).equals("Pissed")){
                this.decreaseSatisfaction(froom);
            }
        }
    }
    public void satisfactionDecreaseAction(Room froom){
        if(this.satisfaction.equals("Happy")){
            if(this.getStudentSatisfaction(this.teacher, this).equals("Mediocre")){
                this.decreaseSatisfaction(froom);
            }
        } else if(this.satisfaction.equals("Mediocre")){
            if(this.getStudentSatisfaction(this.teacher, this).equals("Unsatisfied")){
                this.decreaseSatisfaction(froom);
            }
        } else if(this.satisfaction.equals("Unsatisfied")){
            if(this.getStudentSatisfaction(this.teacher, this).equals("Pissed")){
                this.decreaseSatisfaction(froom);
            }
        }
    }
    public void increaseToMediocreAction(Room froom){
        if(this.getStudentSatisfaction(this.teacher, this).equals("Pissed") && this.satisfactionDecreaseFromWrongAnswer == false){
            this.decreaseSatisfaction(froom);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(studentImage);
        parcel.writeInt(leaveCountdown);
        parcel.writeInt(sessionLength);
        parcel.writeInt(helpRequests);
        parcel.writeString(instrumentOfChoice);
        parcel.writeString(preferredMusicStyle);
        parcel.writeString(satisfaction);
        parcel.writeString(wealth);
        parcel.writeByte((byte) (improvedToUnsatisfied ? 1 : 0));
        parcel.writeByte((byte) (improvedToMediocre ? 1 : 0));
        parcel.writeByte((byte) (wasServed ? 1 : 0));
        parcel.writeByte((byte) (satisfactionDecreaseFromWrongAnswer ? 1 : 0));
    }
}
