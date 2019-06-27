package com.app.servlet;

import com.google.gson.Gson;
import com.app.entity.Course;
import com.app.entity.Calories;
import com.app.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CaloriesServlet extends BaseMobileServlet {

	private static final long serialVersionUID= -318394635533549355L;

	public String add(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {

		Calories a = packageEntity(request);
		Gson gson = new Gson();
		String json = "";
		if (caloriesDao.add(a)) {
          //  System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇a="+a);
			json = gson.toJson(a);
		} else {
			json = "添加失败，请稍后重试！";
		}
		return json;
	}
    public String add3(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        Calories a = packageEntity4(request);
        Gson gson = new Gson();
        String json = "";
        if (caloriesDao.isExist(a.getUserId(),a.getCourseId())) {
            json = "您已经选过该课程了";
        } else if (	caloriesDao.add2(a)) {
            //  System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇a="+a);
            json = gson.toJson(a);
        } else {
            json = "添加失败，请稍后重试！";
        }
        return json;
    }
    public String add2(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        Calories a = packageEntity6(request);
        Gson gson = new Gson();
        String json = "";
        if (caloriesDao.add2(a)) {
            json = gson.toJson(a);
        } else {
            json = "添加失败，请稍后重试！";
        }
        return json;
    }

    public String delete(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        Calories a = packageEntity(request);
        Gson gson = new Gson();
        String json = "";
        if (caloriesDao.isExist(a.getUserId(),a.getCourseId())) {
            caloriesDao.delete(a.getUserId(),a.getCourseId());
            json = gson.toJson(a);
        } else {
            json = "未找到该课程名称";
        }
        return json;
    }



    public String mycalories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Calories a = packageEntity2(request);
        Gson gson = new Gson();
        List<Course> newsList = courseDao.getmyList(a.getUserId());
        for(int i=0;i<newsList.size();i++)
        {
            Calories c=caloriesDao.found(a.getUserId(),newsList.get(i).getCourseId());
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
    public String coursecalories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Calories a = packageEntity3(request);
        Gson gson = new Gson();
        System.out.println("111111111111111)="+a.getCourseId());
        List<User> newsList = userDao.getmyList(a.getCourseId());
        String json = gson.toJson(newsList);
        return json;
    }
    public String coursecalories2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Calories a = packageEntity5(request);
        Gson gson = new Gson();
        System.out.println("111111111111111)="+a.getCourseId());
        System.out.println("222222222222222)="+a.getCoachId());
        List<User> newsList = userDao.getmyList3(a.getCourseId(),a.getCoachId());
        System.out.println("333333333333333)="+newsList.size());
        String json = gson.toJson(newsList);
        return json;
    }
    public String calorieslist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Calories a = packageEntity2(request);
        List<Calories> newsList = caloriesDao.getmyList(a.getUserId());
        for(int i=0;i<newsList.size();i++)
        {
            Course b=courseDao.found3(newsList.get(i).getCourseId());
            if(b!=null)
            {
                newsList.get(i).setCoursename(b.getCoursename()+"");
                //System.out.println("newsList.get(i).getCoursename()="+newsList.get(i).getCoursename());
            }

        }
        Gson gson = new Gson();
        String json = gson.toJson(newsList);
        return json;
    }
    public String calorieslist2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Calories a = packageEntity4(request);
        List<Calories> newsList = caloriesDao.getmyList2(a.getUserId(),a.getCourseId(),a.getCoachId());
        for(int i=0;i<newsList.size();i++)
        {
            Course b=courseDao.found3(newsList.get(i).getCourseId());
            if(b!=null)
            {
                newsList.get(i).setCoursename(b.getCoursename()+"");
                //System.out.println("newsList.get(i).getCoursename()="+newsList.get(i).getCoursename());
            }

        }
        Gson gson = new Gson();
        String json = gson.toJson(newsList);
        return json;
    }
	private Calories packageEntity(HttpServletRequest request) {
		String userid2 = request.getParameter("userId");
		String courseid2=request.getParameter("fitnessId");
        String coachid2 = request.getParameter("coachId");
        String caloires2=request.getParameter("calories");
		int userid;
		int courseid;
        int coachid;
        int calories;
		userid=Integer.parseInt(userid2);
		courseid=Integer.parseInt(courseid2);
        coachid=Integer.parseInt(coachid2);
        calories=Integer.parseInt(caloires2);
		Calories a = new Calories();
		a.setCourseId(courseid);
		a.setUserId(userid);
		a.setCoachId(coachid);
		a.setCalories(calories);
		//System.out.println("a.getCourseId()="+a.getCourseId());
        //System.out.println("a.getUserId()="+a.getUserId());
		return a;
	}
    private Calories packageEntity6(HttpServletRequest request) {
        String userid2 = request.getParameter("userId");
        String courseid2=request.getParameter("fitnessId");
        String caloires2=request.getParameter("calories");
        int userid;
        int courseid;
        int coachid;
        int calories;
        userid=Integer.parseInt(userid2);
        courseid=Integer.parseInt(courseid2);
        calories=Integer.parseInt(caloires2);
        Calories a = new Calories();
        a.setCourseId(courseid);
        a.setUserId(userid);
        a.setCalories(calories);
        //System.out.println("a.getCourseId()="+a.getCourseId());
        //System.out.println("a.getUserId()="+a.getUserId());
        return a;
    }
    private Calories packageEntity5(HttpServletRequest request) {
        String userid2 = request.getParameter("coachId");
        String courseid2=request.getParameter("fitnessId");
        int userid;
        int courseid;
        userid=Integer.parseInt(userid2);
        courseid=Integer.parseInt(courseid2);
        Calories a = new Calories();
        a.setCourseId(courseid);
        a.setCoachId(userid);
        //System.out.println("a.getCourseId()="+a.getCourseId());
       // System.out.println("a.getUserId()="+a.getUserId());
        return a;
    }
    private Calories packageEntity4(HttpServletRequest request) {
        String userid2 = request.getParameter("userId");
        String courseid2=request.getParameter("fitnessId");
        String coachid2=request.getParameter("coachId");
        int userid;
        int courseid;
        int coachid;
        userid=Integer.parseInt(userid2);
        courseid=Integer.parseInt(courseid2);
        coachid=Integer.parseInt(coachid2);
        Calories a = new Calories();
        a.setCourseId(courseid);
        a.setUserId(userid);
        a.setCoachId(coachid);
       // System.out.println("a.getCourseId()="+a.getCourseId());
       // System.out.println("a.getUserId()="+a.getUserId());
        //System.out.println("a.getCoachId()="+a.getCoachId());
        return a;
    }
    private Calories packageEntity2(HttpServletRequest request) {
        String userid2 = request.getParameter("userId");
        int userid;
        userid=Integer.parseInt(userid2);
        Calories a = new Calories();
        a.setUserId(userid);
        return a;
    }
    private Calories packageEntity3(HttpServletRequest request) {
        String courseid2 = request.getParameter("fitnessId");
        int courseid;
        courseid=Integer.parseInt(courseid2);
        Calories a = new Calories();
        a.setCourseId(courseid);
        return a;
    }

}
