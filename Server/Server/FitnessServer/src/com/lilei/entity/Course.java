package com.lilei.entity;

public class Course {
    private int courseId;

    private String teachername;
    private String coursename;
    private String oldcoursename;

    private String courseteach;

    private String coursedata;
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
