package com.example.myapplication.example.entity;

import android.graphics.Bitmap;

/**
 * Created by djzhao on 17/05/02.
 */

public class Comment {

    private int commentId;

    private String username;

    private String replyUser;

    private String comment;

    private String commentTime;
    private String name;
    private Bitmap bitmap;
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
