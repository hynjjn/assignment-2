package com.survey.model;

/**
 * DTO representing one fruit option in the survey.
 * {@code percentage} is not stored in the DB; it is computed per request
 * (votes / total) by the controller before the view renders it.
 */
public class Fruit {

    private int id;
    private String name;
    private int votes;
    private int percentage;

    public Fruit() {
    }

    public Fruit(int id, String name, int votes) {
        this.id = id;
        this.name = name;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
