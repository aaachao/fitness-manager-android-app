package com.app.servlet;

import com.google.gson.Gson;
import com.app.entity.Course;
import com.app.entity.Depend;
import com.app.entity.User;

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
			json = gson.toJson(a);
		} else {
			json = "添加失败，请稍后重试！";
		}
		return json;
	}
    public String add3(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        Depend a = packageEntity4(request);
        Gson gson = new Gson();
        String json = "";
        if (dependDao.isExist(a.getUserId(),a.getCourseId())) {
            json = "您已经选过该课程了";
        } else if (	dependDao.add2(a)) {
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
        for(int i=0;i<newsList.size();i++)
        {
            Depend c=dependDao.found(a.getUserId(),newsList.get(i).getCourseId());
            User b=userDao.found2(c.getCoachId());
            if(b!=null)
            {
                newsList.get(i).setTeachername(b.getName());
                newsList.get(i).setCourseteach(b.getUserId()+"");
            }

        }
        String json = gson.toJson(newsList);
        return json;
    }
    public String mydepend2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Depend a = packageEntity2(request);
        Gson gson = new Gson();
        List<Course> newsList = courseDao.getmyList22(a.getUserId());
        for(int i=0;i<newsList.size();i++)
        {
            Depend c=dependDao.found(a.getUserId(),newsList.get(i).getCourseId());
            User b=userDao.found2(c.getCoachId());
            if(b!=null)
            {
                newsList.get(i).setTeachername(b.getName());
                newsList.get(i).setCourseteach(b.getUserId()+"");
            }

        }
        String json = gson.toJson(newsList);
        return json;
    }
    public String coursedepend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Depend a = packageEntity3(request);
        Gson gson = new Gson();
        System.out.println("111111111111111)="+a.getCourseId());
        List<User> newsList = userDao.getmyList(a.getCourseId());
        String json = gson.toJson(newsList);
        return json;
    }
    public String coursedepend2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Depend a = packageEntity5(request);
        Gson gson = new Gson();
        List<User> newsList = userDao.getmyList3(a.getCourseId(),a.getCoachId());

        String json = gson.toJson(newsList);
        return json;
    }
	private Depend packageEntity(HttpServletRequest request) {
		String userid2 = request.getParameter("userId");
		String courseid2=request.getParameter("fitnessId");
		int userid;
		int courseid;
		userid=Integer.parseInt(userid2);
		courseid=Integer.parseInt(courseid2);
		Depend a = new Depend();
		a.setCourseId(courseid);
		a.setUserId(userid);
		System.out.println("a.getCourseId()="+a.getCourseId());
        System.out.println("a.getUserId()="+a.getUserId());
		return a;
	}
    private Depend packageEntity5(HttpServletRequest request) {
        String userid2 = request.getParameter("coachId");
        String courseid2=request.getParameter("fitnessId");
        int userid;
        int courseid;
        userid=Integer.parseInt(userid2);
        courseid=Integer.parseInt(courseid2);
        Depend a = new Depend();
        a.setCourseId(courseid);
        a.setCoachId(userid);
        //System.out.println("a.getCourseId()="+a.getCourseId());
       // System.out.println("a.getUserId()="+a.getUserId());
        return a;
    }
    private Depend packageEntity4(HttpServletRequest request) {
        String userid2 = request.getParameter("userId");
        String courseid2=request.getParameter("fitnessId");
        String coachid2=request.getParameter("coachId");
        int userid;
        int courseid;
        int coachid;
        userid=Integer.parseInt(userid2);
        courseid=Integer.parseInt(courseid2);
        coachid=Integer.parseInt(coachid2);
        Depend a = new Depend();
        a.setCourseId(courseid);
        a.setUserId(userid);
        a.setCoachId(coachid);
       // System.out.println("a.getCourseId()="+a.getCourseId());
       // System.out.println("a.getUserId()="+a.getUserId());
        //System.out.println("a.getCoachId()="+a.getCoachId());
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
        String courseid2 = request.getParameter("fitnessId");
        int courseid;
        courseid=Integer.parseInt(courseid2);
        Depend a = new Depend();
        a.setCourseId(courseid);
        return a;
    }

}
