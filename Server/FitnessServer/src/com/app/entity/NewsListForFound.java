package com.app.entity;

public class NewsListForFound {
    private int newsId;
    private String title;
    private String username;
    private int sex;
    private String name;
    public String getName() {
        return name;
    }
    private String image;
    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}
    public void setName(String name) {
        this.name = name;
    }
    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
