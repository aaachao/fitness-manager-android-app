package com.lilei.entity;

/**
 * Created by djzhao on 17/04/30.
 */

public class User {
    private int userId;

    private String oldusername;

    private String username;

    private String password;



    
    private String registerTime;
    
    private int status;

    private String role;

    private String name;

    private String phone;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole(){return role;}

    public void setRole(String role){this.role=role;}

    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getoldUsername() {
        return oldusername;
    }

    public void setoldUsername(String oldusername) {
        this.oldusername = oldusername;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }





}
