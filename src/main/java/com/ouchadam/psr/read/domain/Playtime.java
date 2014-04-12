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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playtime playtime = (Playtime) o;
        if (hours != playtime.hours) return false;
        if (minutes != playtime.minutes) return false;
        if (seconds != playtime.seconds) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        result = 31 * result + seconds;
        return result;
    }

}
