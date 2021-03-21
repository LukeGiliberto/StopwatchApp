package au.edu.jcu.cp3406.stopwatchapp;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

class Stopwatch {
    private int hours, minutes, seconds;

    Stopwatch(){
        hours = minutes = seconds = 0;
    }

    Stopwatch(String time){
        String[] savedTime = time.split(":");
        hours = Integer.parseInt(savedTime[0]);
        minutes = Integer.parseInt(savedTime[1]);
        seconds = Integer.parseInt(savedTime[2]);
    }

    void tick (){
        if (seconds == 59){ //60 seconds reached
            seconds = 0;
            if (minutes == 59){ //60 minutes reached
                minutes = 0;
                hours++;
            }
            else {
                minutes++;
            }
        }
        else {
            seconds++;
        }
    }

    @SuppressLint("DefaultLocale")
    @NonNull @Override
    public String toString(){
        return String.format("%02d:%02d:%02d",hours,minutes,seconds);
    }
}
