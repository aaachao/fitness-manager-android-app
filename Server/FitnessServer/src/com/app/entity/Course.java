package com.app.entity;

import java.io.File;

public class Course {
    private int courseId;
    private int coachId;
    private int calories;
    //private File imageFile;

    private String teachername;
    private String coursename;
    private String oldcoursename;

    private String courseteach;

    private String coursedata;
    private String image;
    private int status;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
   // public File getImageFile() {
   //     return imageFile;
   // }

   // public void setImageFile(File imageFile) {
   //     this.imageFile = imageFile;
   // }
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}
    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

	public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }
    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
    public String getoldCoursename() {
        return oldcoursename;
    }

    public void setoldCoursename(String oldcoursename) {
        this.oldcoursename = oldcoursename;
    }

    public String getCourseteach() {
        return courseteach;
    }

    public void setCourseteach(String courseteach) {
        this.courseteach = courseteach;
    }

    public String getCoursedata() {
        return coursedata;
    }

    public void setCoursedata(String coursedata) {
        this.coursedata = coursedata;
    }
}
