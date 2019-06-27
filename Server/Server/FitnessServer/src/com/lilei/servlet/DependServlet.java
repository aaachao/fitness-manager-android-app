package com.lilei.servlet;

import com.google.gson.Gson;
import com.lilei.dao.DependDao;
import com.lilei.entity.Course;
import com.lilei.entity.Depend;
import com.lilei.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DependServlet extends BaseMobileServlet {

	private static final long serialVersionUID= -318394635533549355L;

	public String add(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {

		Depend a = packageEntity(request);
		Gson gson = new Gson();
		String json = "";
		if (dependDao.isExist(a.getUserId(),a.getCourseId())) {
			json = "您已经选过该课程了";
		} else if (	dependDao.add(a)) {
          //  System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇a="+a);
			json = gson.toJson(a);
		} else {
			json = "添加失败，请稍后重试！";
		}
		return json;
	}
    public String add2(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        Depend a = packageEntity(request);
        Gson gson = new Gson();
        String json = "";
        if (dependDao.isExist(a.getUserId(),a.getCourseId())) {
            json = "该课程的学习者已有该用户";
        } else if (	dependDao.add(a)) {
            //  System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇a="+a);
            json = gson.toJson(a);
        } else {
            json = "添加失败，请稍后重试！";
        }
        return json;
    }

    public String delete(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        Depend a = packageEntity(request);
        Gson gson = new Gson();
        String json = "";
        if (dependDao.isExist(a.getUserId(),a.getCourseId())) {
            dependDao.delete(a.getUserId(),a.getCourseId());
            json = gson.toJson(a);
        } else {
            json = "未找到该课程名称";
        }
        return json;
    }



    public String mydepend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Depend a = packageEntity2(request);
        Gson gson = new Gson();
        List<Course> newsList = courseDao.getmyList(a.getUserId());
        String json = gson.toJson(newsList);
        return json;
    }
    public String coursedepend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Depend a = packageEntity3(request);
        Gson gson = new Gson();
        List<User> newsList = userDao.getmyList(a.getCourseId());
        String json = gson.toJson(newsList);
        return json;
    }
	private Depend packageEntity(HttpServletRequest request) {
		String userid2 = request.getParameter("userId");
		String courseid2=request.getParameter("courseId");
		int userid;
		int courseid;
       //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇userid2="+userid2);
        //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇courseid2="+courseid2);
		userid=Integer.parseInt(userid2);
		courseid=Integer.parseInt(courseid2);
       //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇userid="+userid);
       //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇courseid="+courseid);
		Depend a = new Depend();
		a.setCourseId(courseid);
		a.setUserId(userid);
		return a;
	}
    private Depend packageEntity2(HttpServletRequest request) {
        String userid2 = request.getParameter("userId");
        int userid;
        userid=Integer.parseInt(userid2);
        Depend a = new Depend();
        a.setUserId(userid);
        return a;
    }
    private Depend packageEntity3(HttpServletRequest request) {
        String courseid2 = request.getParameter("courseId");
        int courseid;
        courseid=Integer.parseInt(courseid2);
        Depend a = new Depend();
        a.setCourseId(courseid);
        return a;
    }

}
