package com.example.floridaclubmanager;

import android.view.View;

import pl.droidsonroids.gif.GifImageView;

public class PoorStudent extends Student{
    public PoorStudent(int studentimage, String wealth, String instrumentofchoice, String preferredmusicstyle){
        this.studentImage = studentimage;
        this.instrumentOfChoice = instrumentofchoice;
        this.preferredMusicStyle = preferredmusicstyle;
        this.wealth = wealth;
    }
    private char returnDesiredInstrumentGrade(Teacher teacher){
        if(this.instrumentOfChoice.equals("Horns")){
            if(teacher != this.teacher){
                return teacher.horns;
            } else{
                return this.teacher.horns;
            }
        } else if(this.instrumentOfChoice.equals("Piano")){
            if(teacher != this.teacher){
                return teacher.piano;
            } else{
                return this.teacher.piano;
            }
        } else if(this.instrumentOfChoice.equals("Percussions")){
            if(teacher != this.teacher){
                return teacher.percussions;
            } else{
                return this.teacher.percussions;
            }
        } else{
            if(teacher != this.teacher){
                return teacher.strings;
            } else{
                return this.teacher.strings;
            }
        }
    }
    private int returnDesiredStyleStat(Teacher teacher){
        if(this.preferredMusicStyle.equals("Jazz")){
            if(teacher != this.teacher){
                return teacher.jazz;
            } else{
                return this.teacher.jazz;
            }
        } else if(this.preferredMusicStyle.equals("Rock")){
            if(teacher != this.teacher){
                return teacher.rock;
            } else{
                return this.teacher.rock;
            }
        } else if(this.preferredMusicStyle.equals("Groove")){
            if(teacher != this.teacher){
                return teacher.rock;
            } else{
                return this.teacher.rock;
            }
        } else{
            if(teacher != this.teacher){
                return teacher.classical;
            } else{
                return this.teacher.classical;
            }
        }
    }
    public void setPoorStudentSatisfaction(){
        if(((returnDesiredInstrumentGrade(teacher) == 'A' || returnDesiredInstrumentGrade(teacher) == 'B') && returnDesiredStyleStat(teacher) >= 40) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) >= 25 && returnDesiredStyleStat(teacher) < 40)){
            this.setSatisfaction("Stoked");
        } else if((returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) >= 25 && returnDesiredStyleStat(teacher) < 40) || (returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 40) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) >= 15 && returnDesiredStyleStat(teacher) < 25)){
            this.setSatisfaction("Happy");
        } else if((returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 25 && returnDesiredStyleStat(teacher) < 40) || (returnDesiredInstrumentGrade(teacher) == 'D' && returnDesiredStyleStat(teacher) >= 40) || (returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) >= 15 && returnDesiredStyleStat(teacher) < 25) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) < 15)){
            this.setSatisfaction("Mediocre");
        } else if((returnDesiredInstrumentGrade(teacher) == 'D' && returnDesiredStyleStat(teacher) >= 25 && returnDesiredStyleStat(teacher) < 40) || (returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 15 && returnDesiredStyleStat(teacher) < 25) || (returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) < 15)){
            this.setSatisfaction("Unsatisfied");
        } else{
            this.setSatisfaction("Pissed");
        }
}
public String getPoorStudentSatisfaction(Teacher teacher){
    if(((returnDesiredInstrumentGrade(teacher) == 'A' || returnDesiredInstrumentGrade(teacher) == 'B') && returnDesiredStyleStat(teacher) >= 40) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) >= 25 && returnDesiredStyleStat(teacher) < 40)){
        return "Stoked";
    } else if((returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) >= 25 && returnDesiredStyleStat(teacher) < 40) || (returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 40) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) >= 15 && returnDesiredStyleStat(teacher) < 25)){
        return "Happy";
    } else if((returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 25 && returnDesiredStyleStat(teacher) < 40) || (returnDesiredInstrumentGrade(teacher) == 'D' && returnDesiredStyleStat(teacher) >= 40) || (returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) >= 15 && returnDesiredStyleStat(teacher) < 25) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) < 15)){
        return "Mediocre";
    } else if((returnDesiredInstrumentGrade(teacher) == 'D' && returnDesiredStyleStat(teacher) >= 25 && returnDesiredStyleStat(teacher) < 40) || (returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 15 && returnDesiredStyleStat(teacher) < 25) || (returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) < 15)){
        return "Unsatisfied";
    } else{
        return "Pissed";
    }
}
}
