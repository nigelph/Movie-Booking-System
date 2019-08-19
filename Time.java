/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nigel_Phan_17983161;

/**
 *
 * @author khj8681
 */
public class Time implements Comparable<Time> {

    private int hours;
    private int mins;
    private int secs;

    public Time() {
        this(0, 0, 0);
    }

    public Time(int hours) {
        this(hours, 0, 0);
    }

    public Time(int hours, int mins) {
        this(hours, mins, 0);
    }

    public Time(int hours, int mins, int secs) {
        setHours(hours);
        setMinutes(mins);
        setSeconds(secs);
    }

    public void setSeconds(int secs) {
        if (secs >= 0 && secs < 60) {
            this.secs = secs;
        } else {
            this.secs = 0;
        }
    }

    public void setMinutes(int mins) {
        if (mins >= 0 && mins < 60) {
            this.mins = mins;
        } else {
            this.mins = 0;
        }
    }

    public void setHours(int hours) {
        if (hours >= 0 && hours < 24) {
            this.hours = hours;
        } else {
            this.hours = 0;
        }
    }

    public int getSeconds() {
        return secs;
    }

    public int getMinutes() {
        return mins;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public boolean equals(Object otherTime) {

        if (otherTime instanceof Time) {
            Time t = new Time();
            Time other = (Time) otherTime;

            //Check if they are equal to each other 
            return t.equals(other);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%d:%d:%02d", hours, mins, secs);
    }

    @Override
    public int compareTo(Time e) {
        if (this.hours < e.hours) {
            return -1;
        } else if (this.hours > e.hours) {
            return 1;
        }
        if (this.mins < e.mins) {
            return -1;
        } else if (this.mins > e.mins) {
            return 1;
        }
        if (this.secs < e.secs) {
            return -1;
        } else if (this.secs > e.secs) {
            return 1;
        }
        return 0;
    }
}
