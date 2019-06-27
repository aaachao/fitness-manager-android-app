package com.example.myapplication.example.entity;

import java.sql.Timestamp;

public class Calories {
    private int Id;
    private int courseId;
    private int userId;
    private int coachId;
    private int calories;
    private Timestamp time;
    private String coursename;
    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
    public Timestamp getTime() {
        return time;
    }
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId= coachId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

	public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
