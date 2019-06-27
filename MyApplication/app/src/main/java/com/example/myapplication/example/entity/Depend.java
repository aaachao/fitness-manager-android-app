package com.example.myapplication.example.entity;

public class Depend {
    private int dependId;
    private int courseId;
    private int userId;
    private int coachId;
    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId= coachId;
    }

    public int getDependId() {
        return dependId;
    }

    public void setDepenId(int dependId) {
        this.dependId = dependId;
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
