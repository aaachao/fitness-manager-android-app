package com.example.myapplication.example.entity;


import android.graphics.Bitmap;

import java.io.File;

public class Course {
        private int courseId;
        private int calories;
        private String teachername;
        private String coursename;
        private File imageFile;
        private Bitmap bitmap;

        private String courseteach;

        private String coursedata;
        private String image;
        private int coachId;
        private int status;
        public int getStatus() {
        return status;
    }

        public void setStatus(int status) {
        this.status = status;
    }
        public int getCoachId() {
        return coachId;
    }

        public void setCoachId(int coachId) {
        this.coachId = coachId;
    }
        public Bitmap getBitmap() {
        return bitmap;
    }

        public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
        public File getImageFile() {
        return imageFile;
    }

        public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
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

        public String getCoursename() {
            return coursename;
        }

        public void setCoursename(String coursename) {
            this.coursename = coursename;
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
