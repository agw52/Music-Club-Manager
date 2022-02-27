package com.example.floridaclubmanager;

public class WealthyStudent extends Student{
    public WealthyStudent(int studentimage, String wealth, String instrumentofchoice, String preferredmusicstyle){
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
                return teacher.groove;
            } else{
                return this.teacher.groove;
            }
        } else{
            if(teacher != this.teacher){
                return teacher.classical;
            } else{
                return this.teacher.classical;
            }
        }
    }
    public void setWealthyStudentSatisfaction(){
        if (((returnDesiredInstrumentGrade(teacher) == 'A' || returnDesiredInstrumentGrade(teacher) == 'B') && returnDesiredStyleStat(teacher) >= 90) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) >= 60 && returnDesiredStyleStat(teacher) < 90)) {
            this.setSatisfaction("Stoked");
        } else if ((returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) >= 60 && returnDesiredStyleStat(teacher) < 90) || (returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 80) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) >= 40 && returnDesiredStyleStat(teacher) < 60)) {
            this.setSatisfaction("Happy");
        } else if ((returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 60 && returnDesiredStyleStat(teacher) < 80) || (returnDesiredInstrumentGrade(teacher) == 'D' && returnDesiredStyleStat(teacher) >= 80) || (returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) >= 40 && returnDesiredStyleStat(teacher) < 60) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) < 40)) {
            this.setSatisfaction("Mediocre");
        } else if ((returnDesiredInstrumentGrade(teacher) == 'D' && returnDesiredStyleStat(teacher) >= 60 && returnDesiredStyleStat(teacher) < 80) || (returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 40 && returnDesiredStyleStat(teacher) < 60) || (returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) < 40)) {
            this.setSatisfaction("Unsatisfied");
        } else {
            this.setSatisfaction("Pissed");
        }
    }
    public String getWealthyStudentSatisfaction(Teacher teacher){
        if (((returnDesiredInstrumentGrade(teacher) == 'A' || returnDesiredInstrumentGrade(teacher) == 'B') && returnDesiredStyleStat(teacher) >= 90) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) >= 60 && returnDesiredStyleStat(teacher) < 90)) {
            return "Stoked";
        } else if ((returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) >= 60 && returnDesiredStyleStat(teacher) < 90) || (returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 80) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) >= 40 && returnDesiredStyleStat(teacher) < 60)) {
            return "Happy";
        } else if ((returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 60 && returnDesiredStyleStat(teacher) < 80) || (returnDesiredInstrumentGrade(teacher) == 'D' && returnDesiredStyleStat(teacher) >= 80) || (returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) >= 40 && returnDesiredStyleStat(teacher) < 60) || (returnDesiredInstrumentGrade(teacher) == 'A' && returnDesiredStyleStat(teacher) < 40)) {
            return "Mediocre";
        } else if ((returnDesiredInstrumentGrade(teacher) == 'D' && returnDesiredStyleStat(teacher) >= 60 && returnDesiredStyleStat(teacher) < 80) || (returnDesiredInstrumentGrade(teacher) == 'C' && returnDesiredStyleStat(teacher) >= 40 && returnDesiredStyleStat(teacher) < 60) || (returnDesiredInstrumentGrade(teacher) == 'B' && returnDesiredStyleStat(teacher) < 40)) {
            return "Unsatisfied";
        } else {
            return "Pissed";
        }
    }
}
