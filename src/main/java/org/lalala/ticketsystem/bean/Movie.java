package org.lalala.ticketsystem.bean;

import java.util.Date;

public class Movie {
    private String name;
    private String starring;
    private double score;
    private double time;
    private double moviePrice;
    private int remainingTickets;
    private Date startTime;

    public Movie() {
    }

    public Movie(String name, String starring, double score, double time, double moviePrice, int remainingTickets, Date startTime) {
        this.name = name;
        this.starring = starring;
        this.score = score;
        this.time = time;
        this.moviePrice = moviePrice;
        this.remainingTickets = (int) remainingTickets;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getMoviePrice() {
        return moviePrice;
    }

    public void setMoviePrice(double moviePrice) {
        this.moviePrice = moviePrice;
    }

    public double getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(double remainingTickets) {
        this.remainingTickets = (int) remainingTickets;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
