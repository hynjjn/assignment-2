// Fruit.java: DTO object (JavaBean)

package com.survey.model;

import java.io.Serializable;

/**
 * DTO representing one fruit option in the survey.
 * {@code percentage} is not stored in the DB; it is computed per request
 * (votes / total) by the controller before the view renders it.
 */

// 3. Serializable
public class Fruit implements Serializable {

    private static final long serialVersionUID = 1L;

    // private fields, exposed with getters and setters
    private int id;
    private String name;
    private int votes;
    private int percentage;

    // 1. no-arg constructor
    public Fruit() {
    }

    public Fruit(int id, String name, int votes) {
        this.id = id;
        this.name = name;
        this.votes = votes;
    }

    // 2. getters and setters -> naming conventions
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
