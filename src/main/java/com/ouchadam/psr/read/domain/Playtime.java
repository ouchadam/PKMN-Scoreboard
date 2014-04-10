package com.ouchadam.psr.read.domain;

public class Playtime {

    private final int hours;
    private final int minutes;
    private final int seconds;

    public Playtime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return hours + ":" + minutes + ":" + seconds;
    }

    public String formatted() {
        return padTimeIfNeeded(hours) + ":" + padTimeIfNeeded(minutes) + ":" + padTimeIfNeeded(seconds);
    }

    private String padTimeIfNeeded(int time) {
        String stringTime = Integer.toString(time);
        if (time < 10) {
            stringTime = "0" + stringTime;
        }
        return stringTime;
    }
}
